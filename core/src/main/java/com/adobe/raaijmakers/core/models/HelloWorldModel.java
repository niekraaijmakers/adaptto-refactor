/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.raaijmakers.core.models;

import com.adobe.acs.commons.models.injectors.annotation.ChildResourceFromRequest;
import com.adobe.cq.wcm.core.components.models.Image;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;

import javax.annotation.PostConstruct;

@Model(adaptables = { SlingHttpServletRequest.class })
public class HelloWorldModel {

    @ChildResourceFromRequest
    private Image loggedInImage;

    @ChildResourceFromRequest
    private Image loggedOutImage;

    @RequestAttribute(name = "isLoggedIn", injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean isLoggedIn;

    @PostConstruct
    public void init(){
        //compute isLoggedIn somehow
    }

    public Image getImage() {
        return isLoggedIn ? loggedInImage : loggedOutImage;
    }
}
