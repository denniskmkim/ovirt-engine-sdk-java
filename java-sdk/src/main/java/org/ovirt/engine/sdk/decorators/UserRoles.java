//
// Copyright (c) 2012 Red Hat, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//           http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

// *********************************************************************
// ********************* GENERATED CODE - DO NOT MODIFY ****************
// *********************************************************************

package org.ovirt.engine.sdk.decorators;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.ovirt.engine.sdk.common.CollectionDecorator;
import org.ovirt.engine.sdk.exceptions.ServerException;
import org.ovirt.engine.sdk.utils.CollectionUtils;
import org.ovirt.engine.sdk.utils.HttpHeaderUtils;
import org.ovirt.engine.sdk.utils.UrlHelper;
import org.ovirt.engine.sdk.web.HttpProxyBroker;
import org.ovirt.engine.sdk.web.UrlParameterType;
import org.ovirt.engine.sdk.entities.Action;

/**
 * <p>UserRoles providing relation and functional services
 * <p>to {@link org.ovirt.engine.sdk.entities.Roles }.
 */
@SuppressWarnings("unused")
public class UserRoles extends
        CollectionDecorator<org.ovirt.engine.sdk.entities.Role,
                            org.ovirt.engine.sdk.entities.Roles,
                            UserRole> {

    private User parent;

    /**
     * @param proxy HttpProxyBroker
     * @param parent User
     */
    public UserRoles(HttpProxyBroker proxy, User parent) {
        super(proxy, "roles");
        this.parent = parent;
    }

    /**
     * Lists UserRole objects.
     * 
     * @return
     *     List of {@link UserRole }
     *
     * @throws ClientProtocolException
     * @throws ServerException
     * @throws IOException
     */
    @Override
    public List<UserRole> list() throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName();
        return list(url, org.ovirt.engine.sdk.entities.Roles.class, UserRole.class);
    }

    /**
     * Fetches UserRole object by id.
     * 
     * @return 
     *     {@link UserRole }
     *
     * @throws ClientProtocolException
     * @throws ServerException
     * @throws IOException
     */
    @Override
    public UserRole get(UUID id) throws ClientProtocolException,
            ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName() + SLASH + id.toString();
        return getProxy().get(url, org.ovirt.engine.sdk.entities.Role.class, UserRole.class);
    }

    /**
     * Adds Role object.
     *
     * @param role
     *
     * <pre>
     * role.id
     * </pre>
     *
     * @return
     *     {@link UserRole }
     *
     * @throws ClientProtocolException
     * @throws ServerException
     * @throws IOException
     */
    public UserRole add(org.ovirt.engine.sdk.entities.Role role) throws 
            ClientProtocolException, ServerException, IOException {
        String url = this.parent.getHref() + SLASH + getName();
        return getProxy().add(url, role, org.ovirt.engine.sdk.entities.Role.class, UserRole.class);
    }

}

