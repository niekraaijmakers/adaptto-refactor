package com.adobe.raaijmakers.core.models;


import com.adobe.acs.commons.models.injectors.annotation.ContentPolicyValue;
import com.adobe.acs.commons.models.injectors.annotation.HierarchicalPageProperty;
import com.adobe.acs.commons.models.injectors.annotation.I18N;
import com.adobe.acs.commons.models.via.annotations.ContentPolicyViaType;
import com.adobe.acs.commons.models.via.annotations.PageContentResourceViaType;
import com.adobe.acs.commons.models.via.impl.ContentPolicyPropertiesViaProvider;
import com.day.cq.wcm.api.constants.NameConstants;
import com.day.crx.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Model(adaptables = { Resource.class })
public class HeroComponent {

    @ChildResource
    private HeroSlide slide1;

    @ChildResource
    private HeroSlide slide2;

    @ChildResource
    private HeroSlide slide3;
    private final List<HeroSlide> slides = new ArrayList<>();;

    @HierarchicalPageProperty(JcrConstants.JCR_LANGUAGE)
    private String language;

    @HierarchicalPageProperty(NameConstants.PN_TEMPLATE)
    private String template;

    @ContentPolicyValue
    private Integer containerHeight;

    @Self @Via( type = PageContentResourceViaType.class)
    private AdaptToPage adaptToPage;

    @I18N(value = "My Aria Label")
    private String ariaLabel;

    @ContentPolicyValue @Via(type = PageContentResourceViaType.class)
    private boolean rtl;

    @PostConstruct
    public void init(){
        if(rtl){
            slides.add(slide3);
            slides.add(slide2);
            slides.add(slide1);
        }else{
            slides.add(slide1);
            slides.add(slide2);
            slides.add(slide3);
        }
    }

    public List<HeroSlide> getSlides() {
        return slides;
    }

    public String getContainerHeight() {
        return "height:"+ containerHeight + "px";
    }

    public String getAriaLabel(){
        return ariaLabel;
    }
}
