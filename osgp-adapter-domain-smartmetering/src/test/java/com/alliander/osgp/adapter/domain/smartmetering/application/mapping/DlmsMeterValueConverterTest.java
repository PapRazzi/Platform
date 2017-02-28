/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.domain.smartmetering.application.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import com.alliander.osgp.domain.core.valueobjects.smartmetering.OsgpMeterValue;
import com.alliander.osgp.domain.core.valueobjects.smartmetering.OsgpUnit;
import com.alliander.osgp.dto.valueobjects.smartmetering.DlmsMeterValueDto;
import com.alliander.osgp.dto.valueobjects.smartmetering.DlmsUnitTypeDto;

public class DlmsMeterValueConverterTest {

    @Test
    public void testCalculate() {
        final MonitoringMapper calculator = new MonitoringMapper();
        DlmsMeterValueDto response = new DlmsMeterValueDto(BigDecimal.valueOf(123456), DlmsUnitTypeDto.KWH);
        assertEquals(BigDecimal.valueOf(123.456d), calculator.map(response, OsgpMeterValue.class).getValue());
        assertEquals(OsgpUnit.KWH, calculator.map(response, OsgpMeterValue.class).getOsgpUnit());

        response = new DlmsMeterValueDto(BigDecimal.valueOf(123456), DlmsUnitTypeDto.M3);
        assertEquals(BigDecimal.valueOf(123456d), calculator.map(response, OsgpMeterValue.class).getValue());
        assertEquals(OsgpUnit.M3, calculator.map(response, OsgpMeterValue.class).getOsgpUnit());

        response = new DlmsMeterValueDto(BigDecimal.valueOf(123456), DlmsUnitTypeDto.M3_CORR);
        assertEquals(BigDecimal.valueOf(123456d), calculator.map(response, OsgpMeterValue.class).getValue());
        assertEquals(OsgpUnit.M3, calculator.map(response, OsgpMeterValue.class).getOsgpUnit());

        response = new DlmsMeterValueDto(BigDecimal.valueOf(123456), DlmsUnitTypeDto.MONTH);
        try {
            calculator.map(response, OsgpMeterValue.class);
            fail("dlms unit A not supported, expected IllegalArgumentException");
        } catch (final IllegalArgumentException ex) {

        }
    }

}
