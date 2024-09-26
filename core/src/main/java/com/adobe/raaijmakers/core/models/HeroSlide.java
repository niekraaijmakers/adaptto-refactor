package com.adobe.raaijmakers.core.models;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Model(adaptables = { Resource.class })
public class HeroSlide {

    @Self
    private Resource resource;

    @ChildResource
    private Cta ctaLeft;

    @ChildResource
    private Cta ctaRight;

    @ValueMapValue
    private String imageUrl;

    @ValueMapValue
    private String[] tags;

    private List<Tag> tagList;

    @PostConstruct
    public void init(){
        ResourceResolver resourceResolver = resource.getResourceResolver();
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        tagList = Arrays.stream(tags).map(tagManager::resolve).collect(Collectors.toUnmodifiableList());
    }

    public Cta getCtaLeft() {
        return ctaLeft;
    }

    public Cta getCtaRight() {
        return ctaRight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTags() {
        return String.join(", ", tagList.stream().map(Tag::getTitle).toArray(String[]::new));
    }

}
