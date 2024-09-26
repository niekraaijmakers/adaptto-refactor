package com.adobe.raaijmakers.core.models;


import com.adobe.raaijmakers.core.services.I18nMap;
import com.adobe.raaijmakers.core.services.I18nProvider;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.constants.NameConstants;
import com.day.cq.wcm.api.policies.ContentPolicy;
import com.day.cq.wcm.api.policies.ContentPolicyManager;
import com.day.crx.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Model(adaptables = { Resource.class })
public class HeroComponent {

    @Self
    private Resource resource;

    @ChildResource
    private HeroSlide slide1;

    @ChildResource
    private HeroSlide slide2;

    @ChildResource
    private HeroSlide slide3;
    private final List<HeroSlide> slides = new ArrayList<>();;

    private String language;
    private String template;

    private Integer containerHeight;

    private AdaptToPage adaptToPage;

    private I18nMap i18nMap;


    @OSGiService
    private I18nProvider i18nProvider;

    @PostConstruct
    public void init(){

        // Do something
        i18nMap = i18nProvider.getMap(resource);

        ResourceResolver resolver = resource.getResourceResolver();
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        Page resourcePage = pageManager.getContainingPage(resource);
        Resource pageContentResource = resourcePage.getContentResource();
        adaptToPage = pageContentResource.adaptTo(AdaptToPage.class);

        ValueMap componentContentPolicy = getContentPolicyValues(resource);
        containerHeight = componentContentPolicy.get("containerHeight", Integer.class);
        ValueMap pageContentPolicy = getContentPolicyValues(pageContentResource);
        boolean rtl = pageContentPolicy.get("rtl", false);

        if(rtl){
            slides.add(slide3);
            slides.add(slide2);
            slides.add(slide1);
        }else{
            slides.add(slide1);
            slides.add(slide2);
            slides.add(slide3);
        }

        language = getHierarchicalPageProperty(resourcePage, JcrConstants.JCR_LANGUAGE);
        template = getHierarchicalPageProperty(resourcePage, NameConstants.PN_TEMPLATE);
    }


    private ValueMap getContentPolicyValues(Resource resource) {
        ResourceResolver resourceResolver = resource.getResourceResolver();
        ContentPolicyManager manager = resourceResolver.adaptTo(ContentPolicyManager.class);

        if(manager == null){
            return null;
        }
        ContentPolicy policy = manager.getPolicy(resource);
        ValueMap valueMap = policy.getProperties();
        return valueMap;
    }

    private String getHierarchicalPageProperty(Page page, String key) {


        ValueMap props = page.getProperties();
        InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(props);
        return inheritanceValueMap.get(key, String.class);
    }


    public List<HeroSlide> getSlides() {
        return slides;
    }

    public String getContainerHeight() {
        return "height:"+ containerHeight + "px";
    }

    public String getAriaLabel(){
        return i18nMap.getHeroArialLabel();
    }
}
