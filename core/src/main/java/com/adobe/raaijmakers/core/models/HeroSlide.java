package com.adobe.raaijmakers.core.models;

import com.adobe.acs.commons.models.injectors.annotation.TagProperty;
import com.day.cq.tagging.Tag;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = { Resource.class })
public class HeroSlide {

    @ChildResource
    private Cta ctaLeft;

    @ChildResource
    private Cta ctaRight;

    @ValueMapValue
    private String imageUrl;

    @TagProperty @Optional
    private List<Tag> tags;


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
        return String.join(", ", tags.stream().map(Tag::getTitle).toArray(String[]::new));
    }

}
