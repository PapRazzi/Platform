/**
 * Copyright 2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.smartmetering.application.mapping;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alliander.osgp.adapter.ws.schema.smartmetering.monitoring.ActualMeterReadsResponseData;
import com.alliander.osgp.adapter.ws.schema.smartmetering.monitoring.MeterValue;
import com.alliander.osgp.adapter.ws.schema.smartmetering.monitoring.ObjectFactory;
import com.alliander.osgp.domain.core.valueobjects.smartmetering.MeterReads;
import com.alliander.osgp.domain.core.valueobjects.smartmetering.OsgpMeterValue;

public class ActualMeterReadsResponseConverter extends CustomConverter<MeterReads, ActualMeterReadsResponseData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActualMeterReadsResponseConverter.class);

    @Override
    public ActualMeterReadsResponseData convert(final MeterReads source,
            final Type<? extends ActualMeterReadsResponseData> destinationType) {

        final com.alliander.osgp.adapter.ws.schema.smartmetering.monitoring.ActualMeterReadsResponseData destination = new ObjectFactory()
                .createActualMeterReadsResponseData();

        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(source.getLogTime());
        XMLGregorianCalendar convertedDate;
        try {
            convertedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (final DatatypeConfigurationException e) {
            LOGGER.error("JAXB mapping: An error occured while converting calendar types.", e);
            convertedDate = null;
        }

        destination.setLogTime(convertedDate);
        destination.setActiveEnergyImport(this.getMeterValue(source.getActiveEnergyImport()));
        destination.setActiveEnergyExport(this.getMeterValue(source.getActiveEnergyExport()));
        destination.setActiveEnergyExportTariffOne(this.getMeterValue(source.getActiveEnergyExportTariffOne()));
        destination.setActiveEnergyExportTariffTwo(this.getMeterValue(source.getActiveEnergyExportTariffTwo()));
        destination.setActiveEnergyImportTariffOne(this.getMeterValue(source.getActiveEnergyImportTariffOne()));
        destination.setActiveEnergyImportTariffTwo(this.getMeterValue(source.getActiveEnergyImportTariffTwo()));

        destination.setException(source.getException());
        destination.setResultString(source.getResultString());

        return destination;
    }

    private MeterValue getMeterValue(final OsgpMeterValue source) {
        return this.mapperFacade.map(source, MeterValue.class);
    }

}
