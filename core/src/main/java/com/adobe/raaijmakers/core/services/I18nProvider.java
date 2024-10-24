package com.adobe.raaijmakers.core.services;

import com.day.cq.i18n.I18n;
import org.apache.sling.api.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Provides I18n translations translations / maps from a resource or locale
 */
public interface I18nProvider {


    I18nMap getMap(Resource resource);

    /**
     * Provides the translated value based on resource
     *
     * @param key      i18n key
     * @param resource underlying resource
     * @return translated string
     */
    String translate(String key, Resource resource);

    /**
     * Provides the translated value based on resource
     *
     * @param key      i18n key
     * @param resource underlying resource
     * @param localeIgnoreContent if true only the path is used to determine the language.
     * @see com.day.cq.wcm.api.Page#getLanguage(boolean)
     * @return translated string
     */
    default String translate(String key, Resource resource, boolean localeIgnoreContent){
        return translate(key, resource);
    }

    /**
     * Provides the translated value based on resource
     *
     * @param key    i18n key
     * @param locale locale
     * @return translated string
     */
    String translate(String key, Locale locale);

    /**
     * Provides the translated value based on request
     *
     * @param key     i18n key
     * @param request locale
     * @return translated string
     */
    String translate(String key, HttpServletRequest request);

    /**
     * Provides the i18n map based on the underlying resource
     *
     * @param resource underlying resource
     * @return i18n map
     */
    I18n i18n(Resource resource);

    /**
     * Provides the i18n map based on the underlying resource
     *
     * @param resource underlying resource
     * @param localeIgnoreContent if true only the path is used to determine the language.
     * @see com.day.cq.wcm.api.Page#getLanguage(boolean)
     * @return i18n map
     */
    default I18n i18n(Resource resource, boolean localeIgnoreContent){
        return i18n(resource);
    }

    /**
     * Provides the i18n map based on the underlying resource
     *
     * @param locale locale
     * @return i18n map
     */
    I18n i18n(Locale locale);

    /**
     * Provides the i18n map based on the request
     *
     * @param request request
     * @return i18n map
     */
    I18n i18n(HttpServletRequest request);
}
