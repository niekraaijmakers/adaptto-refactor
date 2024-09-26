/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.raaijmakers.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.raaijmakers.core.services.DataSourceService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(
        service = { Servlet.class },
        property = {
                "sling.servlet.resourceTypes=" + DataSourceDemo.RESOURCE_TYPE_V1,
                "sling.servlet.methods=GET",
                "sling.servlet.extensions=html"
        }
)
public class DataSourceDemo extends SlingSafeMethodsServlet {

    public final static String RESOURCE_TYPE_V1 = "adaptdialog/dialogsnippet/dynamicselect/source";

    private static final long serialVersionUID = 1L;

    @Reference
    private DataSourceService service;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        DataSource source = service.get(req);
        req.setAttribute(DataSource.class.getName(), source);
    }

}

