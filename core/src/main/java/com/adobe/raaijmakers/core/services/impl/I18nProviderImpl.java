/*
 * ACS AEM Commons
 *
 * Copyright (C) 2013 - 2023 Adobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adobe.raaijmakers.core.services.impl;


import com.adobe.raaijmakers.core.services.I18nMap;
import com.adobe.raaijmakers.core.services.I18nProvider;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.osgi.Order;
import org.apache.sling.commons.osgi.RankedServices;
import org.apache.sling.i18n.ResourceBundleProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Component(
        service = I18nProvider.class,
        immediate = true,
        reference = {
                @Reference(
                        name = "resourceBundleProviders",
                        service = ResourceBundleProvider.class,
                        cardinality = ReferenceCardinality.AT_LEAST_ONE,
                        policy = ReferencePolicy.DYNAMIC,
                        bind = "bindResourceBundleProvider",
                        unbind = "unbindResourceBundleProvider"
                )
        }
)
public class I18nProviderImpl implements I18nProvider {

    private final RankedServices<ResourceBundleProvider> resourceBundleProviders = new RankedServices<>(Order.ASCENDING);

    protected void bindResourceBundleProvider(final ResourceBundleProvider resourceBundleProvider, final Map<String, Object> props) {
        resourceBundleProviders.bind(resourceBundleProvider, props);
    }

    protected void unbindResourceBundleProvider(final ResourceBundleProvider resourceBundleProvider, final Map<String, Object> props) {
        resourceBundleProviders.unbind(resourceBundleProvider, props);
    }


    @Override
    public I18nMap getMap(Resource resource) {
        I18n i18n = i18n(resource);
        return new I18nMap(i18n);
    }

    @Override
    public String translate(final String key, final Resource resource) {
        return translate(key, resource, false);
    }

    @Override
    public String translate(String key, Resource resource, boolean localeIgnoreContent) {
        final I18n i18n = i18n(resource, localeIgnoreContent);
        if (i18n != null) {
            return i18n.get(key);
        }

        return null;
    }

    @Override
    public String translate(final String key, final Locale locale) {
        return I18n.get(getResourceBundle(locale), key);
    }

    @Override
    public String translate(final String key, final HttpServletRequest request) {
        return I18n.get(request, key);
    }

    @Override
    public I18n i18n(final Resource resource) {
        return i18n(resource, false);
    }

    @Override
    public I18n i18n(Resource resource, boolean localeIgnoreContent) {
        return i18n(getResourceBundleFromPageLocale(resource, localeIgnoreContent));
    }

    @Override
    public I18n i18n(final Locale locale) {
        return i18n(getResourceBundle(locale));
    }

    @Override
    public I18n i18n(final HttpServletRequest request) {
        return new I18n(request);
    }

    protected I18n i18n(final ResourceBundle resourceBundle) {
        return new I18n(resourceBundle);
    }

    private ResourceBundle getResourceBundleFromPageLocale(final Resource resource, boolean localeIgnoreContent) {
        return getResourceBundle(getLocaleFromResource(resource, localeIgnoreContent));
    }

    private Locale getLocaleFromResource(final Resource resource, boolean localeIgnoreContent) {
        final Page page = getResourcePage(resource);
        if (page != null) {
            return page.getLanguage(localeIgnoreContent);
        }

        return null;
    }

    protected Page getResourcePage(final Resource resource) {
        return InjectorUtils.getResourcePage(resource);
    }

    private ResourceBundle getResourceBundle(final Locale locale) {
        for (ResourceBundleProvider provider : resourceBundleProviders) {
            final ResourceBundle resourceBundle = provider.getResourceBundle(locale);
            if (resourceBundle != null) {
                return resourceBundle;
            }
        }

        return null;
    }


}
