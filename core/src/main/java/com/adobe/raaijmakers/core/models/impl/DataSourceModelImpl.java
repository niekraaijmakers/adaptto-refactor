package com.adobe.raaijmakers.core.models.impl;

import com.adobe.raaijmakers.core.models.DataSourceModel;
import com.adobe.raaijmakers.core.models.Type;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = DataSourceModel.class,
        resourceType = "adaptdialog/dialogsnippet/dynamicselect/source"
)
public class DataSourceModelImpl implements DataSourceModel {

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    String dataSource;

    @ValueMapValue
    String passedThroughParam;

    @Override
    public Type getSource() {
        return Type.valueOf(dataSource.toUpperCase());
    }

    @Override
    public String getPassedThroughParam() {
        return passedThroughParam;
    }
}
