/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.smartmetering.application.mapping;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

import org.springframework.stereotype.Component;

import com.alliander.osgp.adapter.ws.schema.smartmetering.monitoring.PeriodicMeterReadsRequest;
import com.alliander.osgp.adapter.ws.smartmetering.domain.entities.PeriodicMeterReads;

@Component(value = "monitoringMapper")
public class MonitoringMapper extends ConfigurableMapper {
    @Override
    public void configure(final MapperFactory mapperFactory) {

        // entity PeriodicMeterReads -> WS PeriodicMeterReads
        mapperFactory
                .classMap(PeriodicMeterReads.class,
                        com.alliander.osgp.adapter.ws.schema.smartmetering.monitoring.PeriodicMeterReads.class)
                .byDefault().register();

        mapperFactory
                .classMap(PeriodicMeterReadsRequest.class,
                        com.alliander.osgp.domain.core.valueobjects.smartmetering.PeriodicMeterReadsRequest.class)
                .byDefault().register();
    }
}