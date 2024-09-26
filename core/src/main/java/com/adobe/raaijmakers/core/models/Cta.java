package com.adobe.raaijmakers.core.models;

import com.adobe.acs.commons.models.injectors.annotation.I18N;
import com.day.cq.i18n.I18n;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class })
public class Cta {

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

    @I18N(value = "My Aria Label")
    private String ariaLabel;

    @I18N
    private I18n i18N;

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
        //String ariaLabel = i18N.get("My Aria Label");
        return ariaLabel + " - " + text;
    }
}
