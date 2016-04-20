/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.domain.controllableload.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alliander.osgp.adapter.domain.controllableload.application.mapping.DomainControllableLoadMapper;
import com.alliander.osgp.adapter.domain.controllableload.infra.jms.core.OsgpCoreRequestMessageSender;
import com.alliander.osgp.adapter.domain.controllableload.infra.jms.ws.WebServiceResponseMessageSender;
import com.alliander.osgp.domain.core.entities.Device;
import com.alliander.osgp.domain.core.entities.Organisation;
import com.alliander.osgp.domain.core.entities.Ssld;
import com.alliander.osgp.domain.core.exceptions.UnknownEntityException;
import com.alliander.osgp.domain.core.exceptions.UnregisteredDeviceException;
import com.alliander.osgp.domain.core.repositories.SsldRepository;
import com.alliander.osgp.domain.core.services.DeviceDomainService;
import com.alliander.osgp.domain.core.services.OrganisationDomainService;
import com.alliander.osgp.shared.exceptionhandling.ComponentType;
import com.alliander.osgp.shared.exceptionhandling.FunctionalException;
import com.alliander.osgp.shared.exceptionhandling.FunctionalExceptionType;

public class AbstractService {

    @Autowired
    protected DeviceDomainService deviceDomainService;

    @Autowired
    protected OrganisationDomainService organisationDomainService;

    @Autowired
    protected SsldRepository ssldRepository;

    @Autowired
    @Qualifier(value = "domainControllableLoadOutgoingOsgpCoreRequestMessageSender")
    protected OsgpCoreRequestMessageSender osgpCoreRequestMessageSender;

    @Autowired
    protected DomainControllableLoadMapper domainCoreMapper;

    @Autowired
    @Qualifier(value = "domainControllableLoadOutgoingWebServiceResponseMessageSender")
    protected WebServiceResponseMessageSender webServiceResponseMessageSender;

    protected Device findActiveDevice(final String deviceIdentification) throws FunctionalException {
        Device device;
        try {
            device = this.deviceDomainService.searchActiveDevice(deviceIdentification);
        } catch (final UnregisteredDeviceException e) {
            throw new FunctionalException(FunctionalExceptionType.UNREGISTERED_DEVICE,
                    ComponentType.DOMAIN_CONTROLLABLE_LOAD, e);
        } catch (final UnknownEntityException e) {
            throw new FunctionalException(FunctionalExceptionType.UNKNOWN_DEVICE, ComponentType.DOMAIN_CONTROLLABLE_LOAD,
                    e);
        }
        return device;
    }

    protected Organisation findOrganisation(final String organisationIdentification) throws FunctionalException {
        Organisation organisation;
        try {
            organisation = this.organisationDomainService.searchOrganisation(organisationIdentification);
        } catch (final UnknownEntityException e) {
            throw new FunctionalException(FunctionalExceptionType.UNKNOWN_ORGANISATION,
                    ComponentType.DOMAIN_CONTROLLABLE_LOAD, e);
        }
        return organisation;
    }

    protected Ssld findSsldForDevice(final Device device) {
        return this.ssldRepository.findOne(device.getId());
    }
}
