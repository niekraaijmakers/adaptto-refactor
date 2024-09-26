package com.adobe.raaijmakers.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = { Resource.class })
public class NestingDemo {

    @ChildResource
    private Dropdown dropdown1;

    @ChildResource
    private Dropdown dropdown2;

    @ChildResource
    private Dropdown dropdown3;

    public Dropdown getDropdown1() {
        return dropdown1;
    }

    public Dropdown getDropdown2() {
        return dropdown2;
    }

    public Dropdown getDropdown3() {
        return dropdown3;
    }
}
