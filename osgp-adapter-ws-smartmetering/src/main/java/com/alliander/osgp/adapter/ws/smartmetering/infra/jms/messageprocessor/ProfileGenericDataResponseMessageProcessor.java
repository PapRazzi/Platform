/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.smartmetering.infra.jms.messageprocessor;

import org.springframework.stereotype.Component;

import com.alliander.osgp.domain.core.valueobjects.DeviceFunction;

@Component
public class ProfileGenericDataResponseMessageProcessor extends DomainResponseMessageProcessor {

    protected ProfileGenericDataResponseMessageProcessor() {
        super(DeviceFunction.GET_PROFILE_GENERIC_DATA);
    }

}
