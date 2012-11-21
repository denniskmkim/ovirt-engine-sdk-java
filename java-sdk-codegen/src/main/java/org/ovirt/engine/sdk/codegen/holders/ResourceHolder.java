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

package org.ovirt.engine.sdk.codegen.holders;

import org.ovirt.engine.sdk.codegen.templates.GetterTemplate;
import org.ovirt.engine.sdk.codegen.templates.ResourceTemplate;
import org.ovirt.engine.sdk.codegen.templates.SubResourceTemplate;
import org.ovirt.engine.sdk.codegen.templates.VariableTemplate;
import org.ovirt.engine.sdk.utils.StringUtils;

/**
 * Holds sub-resources
 */
public class ResourceHolder extends AbstractResourceHolder {

    private VariableTemplate variableTemplate;
    private GetterTemplate getterTemplate;

    /**
     * @param decoratorResourceName
     * @param publicEntityName
     * @param subResourceTemplate
     * @param variableTemplate
     * @param getterTemplate
     */
    public ResourceHolder(
            String decoratorResourceName,
            String publicEntityName,
            SubResourceTemplate subResourceTemplate,
            VariableTemplate variableTemplate,
            GetterTemplate getterTemplate) {
        super(decoratorResourceName, publicEntityName, subResourceTemplate);

        this.variableTemplate = variableTemplate;
        this.getterTemplate = getterTemplate;
    }

    /**
     * @param decoratorResourceName
     * @param publicEntityName
     * @param resourceTemplate
     * @param variableTemplate
     * @param getterTemplate
     */
    public ResourceHolder(
            String decoratorResourceName,
            String publicEntityName,
            ResourceTemplate resourceTemplate,
            VariableTemplate variableTemplate,
            GetterTemplate getterTemplate) {
        super(decoratorResourceName, publicEntityName, resourceTemplate);

        this.variableTemplate = variableTemplate;
        this.getterTemplate = getterTemplate;
    }

    /**
     * Returns sub-resource getters
     */
    @Override
    String getSubCollectionsGetters() {
        String subCollectionGetters = "";

        for (CollectionHolder ch : this.getSubcollections().values()) {
            subCollectionGetters +=
                    this.getterTemplate.getTemplate(ch.getName(),
                            StringUtils.toLowerCase(ch.getName()));
        }

        return subCollectionGetters;
    }

    /**
     * Returns sub-resource variables
     */
    @Override
    String getSubCollectionsVariables() {
        String subCollectionVariables = "";

        for (CollectionHolder ch : this.getSubcollections().values()) {
            subCollectionVariables +=
                    this.variableTemplate.getTemplate(ch.getName(),
                            StringUtils.toLowerCase(ch.getName()));
        }

        return subCollectionVariables;
    }
}