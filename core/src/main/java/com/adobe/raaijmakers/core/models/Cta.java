package com.adobe.raaijmakers.core.models;

import com.adobe.raaijmakers.core.services.I18nMap;
import com.adobe.raaijmakers.core.services.I18nProvider;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = { Resource.class })
public class Cta {

    @Self
    private Resource resource;

    @ValueMapValue
    private String buttonType;

    @ValueMapValue
    private String link;

    @ValueMapValue
    private String extraCssClass;

    @ValueMapValue
    private String target;

    @ValueMapValue
    private String text;

    private I18nMap i18nMap;

    @OSGiService
    private I18nProvider i18nProvider;

    @PostConstruct
    public void postConstruct() {
        // Do something
        i18nMap = i18nProvider.getMap(resource);
    }

    public String getButtonType() {
        return buttonType;
    }

    public String getLink() {
        return link;
    }

    public String getExtraCssClass() {
        return extraCssClass;
    }

    public String getTarget() {
        return target;
    }

    public String getText() {
        return text;
    }

    public String getAriaLabel() {
        return i18nMap.getAriaLabel() + " - " + text;
    }

}
