/*
Copyright (c) 2016 Red Hat, Inc.

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
import static org.ovirt.engine.sdk4.builders.Builders.cdrom;
import static org.ovirt.engine.sdk4.builders.Builders.file;

import org.ovirt.engine.sdk4.Connection;
import org.ovirt.engine.sdk4.services.VmCdromService;
import org.ovirt.engine.sdk4.services.VmCdromsService;
import org.ovirt.engine.sdk4.services.VmService;
import org.ovirt.engine.sdk4.services.VmsService;
import org.ovirt.engine.sdk4.types.Cdrom;
import org.ovirt.engine.sdk4.types.Vm;

// This example will connect to the server and change the CDROM attached to a virtual machine:
public class ChangeVmCd {
    public static void main(String[] args) throws Exception {
        // Create the connection to the server:
        Connection connection = connection()
            .url("https://engine40.example.com/ovirt-engine/api")
            .user("admin@internal")
            .password("redhat123")
            .trustStoreFile("truststore.jks")
            .build();

        // Locate the service that manages the virtual machines:
        VmsService vmsService = connection.systemService().vmsService();

        // Find the virtual machine:
        Vm vm = vmsService.list()
            .search("name=myvm")
            .send()
            .vms()
            .get(0);

        // Locate the service that manages the virtual machine:
        VmService vmService = vmsService.vmService(vm.id());

        // Locate the service that manages the CDROM devices of the virtual machine:
        VmCdromsService cdromsService = vmService.cdromsService();

        // Get the first CDROM:
        Cdrom cdrom = cdromsService.list()
            .send()
            .cdroms()
            .get(0);

        // Locate the service that manages the CDROM device found in the previous step:
        VmCdromService cdromService = cdromsService.cdromService(cdrom.id());

        // Change the CDROM disk of the virtual machine to 'my_iso_file.iso'. By default the below operation changes
        // permanently the disk that will be visible to the virtual machine after the next boot, but it doesn't have
        // any effect on the currently running virtual machine. If you want to change the disk that is visible to the
        // current running virtual machine, change the value of the 'current' parameter to 'true'.
        cdromService.update()
            .cdrom(
                cdrom()
                .file(
                    file()
                    .id("my_iso_file.iso")
                )
            )
            .current(false)
            .send();

        // Close the connection to the server:
        connection.close();
    }
}
