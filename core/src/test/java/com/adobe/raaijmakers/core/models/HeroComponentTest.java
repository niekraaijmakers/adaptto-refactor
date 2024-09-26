package com.adobe.raaijmakers.core.models;

import com.adobe.acs.commons.i18n.impl.I18nProviderImpl;
import com.adobe.acs.commons.models.injectors.annotation.impl.HierarchicalPagePropertyAnnotationProcessorFactory;
import com.adobe.acs.commons.models.injectors.impl.ContentPolicyValueInjector;
import com.adobe.acs.commons.models.injectors.impl.HierarchicalPagePropertyInjector;
import com.adobe.acs.commons.models.injectors.impl.I18nInjector;
import com.adobe.acs.commons.models.injectors.impl.TagPropertyInjector;
import com.adobe.acs.commons.models.via.impl.PageContentResourceViaProvider;
import com.adobe.raaijmakers.core.services.I18nMap;
import com.adobe.raaijmakers.core.services.I18nProvider;
import com.adobe.raaijmakers.core.testcontext.AppAemContext;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.i18n.ResourceBundleProvider;
import org.apache.sling.testing.mock.sling.MockResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
class HeroComponentTest {

    private final AemContext context = AppAemContext.newAemContext();

    private HeroComponent systemUnderTest;


    @BeforeEach
    public void setup() throws Exception {

        InputStream pageResourceStream = HeroComponentTest.class.getClassLoader().getResourceAsStream("en.json");
        InputStream tagsResourceStream = HeroComponentTest.class.getClassLoader().getResourceAsStream("tags.json");

        context.load().json(pageResourceStream, "/content/adaptdialog/us/en");
        context.load().json(tagsResourceStream, "/content/cq:tags");
        // prepare a page with a test resource
        context.currentPage("/content/adaptdialog/us/en");
        context.currentResource("/content/adaptdialog/us/en/jcr:content/root/container/container/hero");

        context.contentPolicyMapping("adaptdialog/components/hero", "containerHeight", 250);
        context.contentPolicyMapping("adaptdialog/components/page", "rtl", true);

        context.addModelsForClasses(HeroComponent.class, HeroSlide.class, Cta.class);


        context.registerInjectActivateService(new I18nProviderImpl());
        context.registerService(new HierarchicalPagePropertyInjector());
        context.registerService(new HierarchicalPagePropertyAnnotationProcessorFactory());
        context.registerService(new TagPropertyInjector());
        context.registerService(new ContentPolicyValueInjector());
        context.registerService(new PageContentResourceViaProvider());

        ResourceBundle bundle = context.request().getResourceBundle(Locale.FRENCH);
        ((MockResourceBundle)bundle).put("My Aria Label", "getAriaLabelTest");
        ((MockResourceBundle)bundle).put("My Hero Aria Label", "getHeroAriaLabelTest");
        I18nInjector component = new I18nInjector();
        context.registerInjectActivateService(component);

        ResourceBundleProvider bundleProvider = mock(ResourceBundleProvider.class);
        when(bundleProvider.getResourceBundle(any(Locale.class))).thenReturn(bundle);
        when(bundleProvider.getResourceBundle(anyString(), any(Locale.class))).thenReturn(bundle);
        context.registerService(ResourceBundleProvider.class, bundleProvider);


        I18nProvider service = mock(I18nProvider.class);
        I18nMap mockedMap = mock(I18nMap.class);

        when(mockedMap.getHeroArialLabel()).thenReturn("getHeroAriaLabelTest");
        when(mockedMap.getAriaLabel()).thenReturn("getAriaLabelTest");

        when(service.getMap(any())).thenReturn(mockedMap);

        context.registerService(I18nProvider.class, service);
        // create sling model
        systemUnderTest = context.currentResource().adaptTo(HeroComponent.class);
    }

    @Test
    void testModel() throws Exception {

        assertNotNull(systemUnderTest);

        assertEquals(3, systemUnderTest.getSlides().size());

        assertEquals("height:250px", systemUnderTest.getContainerHeight());

        assertEquals("getHeroAriaLabelTest", systemUnderTest.getAriaLabel());

        HeroSlide slide1 = systemUnderTest.getSlides().get(0);

        assertEquals("/content/dam/adaptdialog/trump.png", slide1.getImageUrl());
        assertEquals("Asset Properties", slide1.getTags());

        assertEquals("primary", slide1.getCtaLeft().getButtonType());

        Cta ctaLeft = slide1.getCtaLeft();
        assertEquals("primary", ctaLeft.getButtonType());
        assertEquals("_self", ctaLeft.getTarget());
        assertEquals("getAriaLabelTest - Terminate", ctaLeft.getAriaLabel());

    }

}
