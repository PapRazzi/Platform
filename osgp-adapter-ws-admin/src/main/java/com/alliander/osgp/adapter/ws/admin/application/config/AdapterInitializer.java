/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.admin.application.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

import com.alliander.osgp.shared.application.config.AbstractWsAdapterInitializer;

/**
 * Web application Java configuration class.
 */
public class AdapterInitializer extends AbstractWsAdapterInitializer implements WebApplicationInitializer {

    public AdapterInitializer() {
        super(ApplicationContext.class, "java:comp/env/osgp/AdapterWsAdmin/log-config");
    }
    
    /**
     * Handles startup of Spring context.
     */
    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        startUp(servletContext);
    }
}