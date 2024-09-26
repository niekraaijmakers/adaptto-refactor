package com.adobe.raaijmakers.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class })
public class Select {

    @ValueMapValue
    private String selectValue;

    public String getSelectValue() {
        return selectValue;
    }
}
