package com.adobe.raaijmakers.core.services;

import com.day.cq.i18n.I18n;

public class I18nMap {

    private final I18n i18n;

    public I18nMap(I18n i18n) {
        // Do something
        this.i18n = i18n;
    }

    public String getAriaLabel(){
        return i18n.get("My Aria Label");
    }

    public String getHeroArialLabel(){
        return i18n.get("This is a hero component");
    }
}
