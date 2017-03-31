/*
Copyright (c) 2017 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.ovirt.engine.sdk4.examples;

import static org.ovirt.engine.sdk4.ConnectionBuilder.connection;
import static org.ovirt.engine.sdk4.builders.Builders.cluster;
import static org.ovirt.engine.sdk4.builders.Builders.operatingSystem;
import static org.ovirt.engine.sdk4.builders.Builders.template;
import static org.ovirt.engine.sdk4.builders.Builders.vm;

import org.ovirt.engine.sdk4.Connection;
import org.ovirt.engine.sdk4.services.VmService;
import org.ovirt.engine.sdk4.services.VmsService;
import org.ovirt.engine.sdk4.types.Vm;
import org.ovirt.engine.sdk4.types.VmStatus;

// This example will create a new virtual machine and start it using Sysprep:
public class AddVmWithSysprep {
    public static void main(String[] args) throws Exception {
        // Create the connection to the server:
        Connection connection = connection()
            .url("https://engine40.example.com/ovirt-engine/api")
            .user("admin@internal")
            .password("redhat123")
            .trustStoreFile("truststore.jks")
            .build();

        // Find the service that manages the collection of virtual machines:
        VmsService vmsService = connection.systemService().vmsService();

        // Create the virtual machine. Note that no Sysprep stuff is needed here, when creating it, it will be used
        // later, when starting it:
        Vm vm = vmsService.add()
            .vm(
                vm()
                .name("myvm")
                .cluster(
                    cluster()
                    .name("mycluster")
                )
                .template(
                    template()
                    .name("mytemplate")
                )
            )
            .send()
            .vm();

        // Find the service that manages the virtual machine:
        VmService vmService = vmsService.vmService(vm.id());

        // Wait till the virtual machine is down, which indicates that all the disks have been created:
        while (vm.status() != VmStatus.DOWN) {
            Thread.sleep(5 * 1000);
            vm = vmService.get().send().vm();
        }

        // The content of the Unattend.xml file. Note that this is an incomplete file, make sure to use a complete one,
        // maybe reading it from an external file:
        String unattendXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<unattend xmlns=\"urn:schemas-microsoft-com:unattend\">\n" +
            "  ...\n" +
            "</unattend>\n";

        // Start the virtual machine enabling Sysprep. Make sure to use a Windows operating system, either in the
        // template, or overriding it explicitly here. Without that the Sysprep logic won't be triggered.
        vmService.start()
            .useSysprep(true)
            .vm(
                vm()
                .os(
                    operatingSystem()
                    .type("windows_7x64")
                )
            )
            .send();

        // Close the connection to the server:
        connection.close();
    }
}
