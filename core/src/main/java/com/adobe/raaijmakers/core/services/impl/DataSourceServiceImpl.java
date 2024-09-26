package com.adobe.raaijmakers.core.services.impl;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.adobe.raaijmakers.core.models.DataSourceModel;
import com.adobe.raaijmakers.core.models.Type;
import com.adobe.raaijmakers.core.services.DataSourceService;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component(service = DataSourceService.class)
public class DataSourceServiceImpl implements DataSourceService {
    @Override
    public DataSource get(SlingHttpServletRequest req) {

        DataSourceModel source = req.adaptTo(DataSourceModel.class);
        Type type = source.getSource();
        String param = source.getPassedThroughParam();
        return type == Type.TYPE_A ? getSourceA(param, req.getResourceResolver()) : getSourceB(param, req.getResourceResolver());

    }


    private DataSource getSourceA(String suffix, ResourceResolver resourceResolver){
        java.util.List<Resource> stubs = new ArrayList<>();
        stubs.add(getStub(resourceResolver, "Option 1 - " + suffix, "option1"));
        stubs.add(getStub(resourceResolver, "Option 2 - " + suffix, "option2"));
        return new SimpleDataSource(stubs.iterator());
    }

    private DataSource getSourceB(String suffix, ResourceResolver resourceResolver){
        java.util.List<Resource> stubs = new ArrayList<>();
        stubs.add(getStub(resourceResolver, "Option 3 - " + suffix, "option3"));
        stubs.add(getStub(resourceResolver, "Option 4 - " + suffix, "option4"));
        return new SimpleDataSource(stubs.iterator());
    }

    private Resource getStub(ResourceResolver resourceResolver, String text, String value){

        ValueMapDecorator vm = new ValueMapDecorator(new HashMap<>());
        vm.put("text", text);
        vm.put("value", value);
        vm.put("hadsize", true);

        return new ValueMapResource(resourceResolver,  new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, vm);

    }
}
