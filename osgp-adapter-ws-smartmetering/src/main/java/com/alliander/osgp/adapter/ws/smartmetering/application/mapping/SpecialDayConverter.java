/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.smartmetering.application.mapping;

import java.nio.ByteBuffer;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

import com.alliander.osgp.domain.core.valueobjects.smartmetering.CosemDate;
import com.alliander.osgp.domain.core.valueobjects.smartmetering.SpecialDay;

public class SpecialDayConverter extends
        CustomConverter<com.alliander.osgp.adapter.ws.schema.smartmetering.configuration.SpecialDay, SpecialDay> {

    @Override
    public SpecialDay convert(final com.alliander.osgp.adapter.ws.schema.smartmetering.configuration.SpecialDay source,
            final Type<? extends SpecialDay> destinationType) {
        final ByteBuffer bb = ByteBuffer.wrap(source.getSpecialDayDate());
        final int year = bb.getShort() & 0xFFFF;
        final int month = bb.get() & 0xFF;
        final int day = bb.get() & 0xFF;
        final int weekDay = bb.get() & 0xFF;
        final SpecialDay specialDay = new SpecialDay(new CosemDate(year, month, day, weekDay), source.getDayId());

        return specialDay;
    }

}
