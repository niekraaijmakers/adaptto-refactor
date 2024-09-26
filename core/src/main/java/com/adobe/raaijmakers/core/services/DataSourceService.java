package com.adobe.raaijmakers.core.services;

import com.adobe.granite.ui.components.ds.DataSource;
import org.apache.sling.api.SlingHttpServletRequest;

public interface DataSourceService {
    DataSource get(SlingHttpServletRequest req);
}
