package com.adobe.raaijmakers.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class})
public class AdaptToPage {

    @ValueMapValue @Optional
    private String trumpable;

    @ValueMapValue  @Optional
    private String pageTitle;

    @ValueMapValue  @Optional
    private String adaptToPageProp;

    public String getTrumpable() {
        return trumpable;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getAdaptToPageProp() {
        return adaptToPageProp;
    }
}
