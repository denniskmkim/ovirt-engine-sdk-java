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
import static org.ovirt.engine.sdk4.builders.Builders.nic;
import static org.ovirt.engine.sdk4.builders.Builders.vnicProfile;

import java.util.List;
import java.util.Objects;

import org.ovirt.engine.sdk4.Connection;
import org.ovirt.engine.sdk4.services.VmNicsService;
import org.ovirt.engine.sdk4.services.VmsService;
import org.ovirt.engine.sdk4.services.VnicProfilesService;
import org.ovirt.engine.sdk4.types.Vm;
import org.ovirt.engine.sdk4.types.VnicProfile;

// This example will connect to the server and add a network interface card to an existing virtual machine:
public class AddVmNic {
    public static void main(String[] args) throws Exception {
        // Create the connection to the server:
        Connection connection = connection()
            .url("https://engine40.example.com/ovirt-engine/api")
            .user("admin@internal")
            .password("redhat123")
            .trustStoreFile("truststore.jks")
            .build();

        // Locate the virtual machines service and use it to find the virtual machine:
        VmsService vmsService = connection.systemService().vmsService();
        Vm vm = vmsService.list().search("name=myvm").send().vms().get(0);

        // In order to specify the network that the new interface will be connected to, we need to specify the
        // identifier of the virtual network interface profile, so we need to find it:
        VnicProfilesService profilesService = connection.systemService().vnicProfilesService();
        String profileId = null;
        List<VnicProfile> profiles = profilesService.list().send().profiles();
        for (VnicProfile profile : profiles) {
            if (Objects.equals(profile.name(), "mynetwork")) {
                profileId = profile.id();
                break;
            }
        }

        // Locate the service that manages the NICs of the virtual machine:
        VmNicsService nicsService = vmsService.vmService(vm.id()).nicsService();

        // Use the "add" method of the disks service to add the disk:
        nicsService.add()
            .nic(
                nic()
                .name("mynic")
                .description("My network interface card")
                .vnicProfile(
                    vnicProfile()
                    .id(profileId)
                )
            )
            .send();

        // Close the connection to the server:
        connection.close();
    }
}
