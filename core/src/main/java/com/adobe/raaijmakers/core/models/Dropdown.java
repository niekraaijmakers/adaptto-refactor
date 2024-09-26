package com.adobe.raaijmakers.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = { Resource.class })
public class Dropdown {

    @ChildResource
    private Select dynamicSelect1;

    @ChildResource
    private Select dynamicSelect2;

    public Select getDynamicSelect1() {
        return dynamicSelect1;
    }

    public Select getDynamicSelect2() {
        return dynamicSelect2;
    }
}
