/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.loadmanagement.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.method.MethodConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.alliander.osgp.adapter.ws.endpointinterceptors.OrganisationIdentification;
import com.alliander.osgp.adapter.ws.loadmanagement.application.mapping.AdHocManagementMapper;
import com.alliander.osgp.adapter.ws.loadmanagement.application.services.AdHocManagementService;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.DevicePage;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.FindAllDevicesRequest;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.FindAllDevicesResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.GetStatusAsyncRequest;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.GetStatusAsyncResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.GetStatusRequest;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.GetStatusResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.ResumeScheduleAsyncRequest;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.ResumeScheduleAsyncResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.ResumeScheduleRequest;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.ResumeScheduleResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.SetSwitchAsyncRequest;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.SetSwitchAsyncResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.SetSwitchRequest;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.SetSwitchResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.common.AsyncResponse;
import com.alliander.osgp.adapter.ws.schema.loadmanagement.common.OsgpResultType;
import com.alliander.osgp.domain.core.entities.Device;
import com.alliander.osgp.domain.core.valueobjects.LightValue;
import com.alliander.osgp.domain.core.exceptions.ValidationException;
import com.alliander.osgp.domain.core.valueobjects.DeviceStatus;
import com.alliander.osgp.domain.core.valueobjects.ResumeScheduleData;
import com.alliander.osgp.shared.exceptionhandling.ComponentType;
import com.alliander.osgp.shared.exceptionhandling.FunctionalException;
import com.alliander.osgp.shared.exceptionhandling.FunctionalExceptionType;
import com.alliander.osgp.shared.exceptionhandling.OsgpException;
import com.alliander.osgp.shared.exceptionhandling.TechnicalException;
import com.alliander.osgp.shared.infra.jms.ResponseMessage;

//MethodConstraintViolationException is deprecated.
//Will by replaced by equivalent functionality defined
//by the Bean Validation 1.1 API as of Hibernate Validator 5.
@SuppressWarnings("deprecation")
@Endpoint
public class LoadManagementAdHocManagementEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadManagementAdHocManagementEndpoint.class);
    private static final String NAMESPACE = "http://www.alliander.com/schemas/osgp/loadmanagement/adhocmanagement/2014/10";
    private static final ComponentType COMPONENT_WS_LOAD_MANAGEMENT = ComponentType.WS_LOAD_MANAGEMENT;

    private static final String EXCEPTION_OCCURRED = "Exception Occurred";

    private final AdHocManagementService adHocManagementService;
    private final AdHocManagementMapper adHocManagementMapper;

    @Autowired
    public LoadManagementAdHocManagementEndpoint(
            @Qualifier("wsLoadManagementAdHocManagementService") final AdHocManagementService adHocManagementService,
            @Qualifier("loadManagementAdhocManagementMapper") final AdHocManagementMapper adHocManagementMapper) {
        this.adHocManagementService = adHocManagementService;
        this.adHocManagementMapper = adHocManagementMapper;
    }

    @PayloadRoot(localPart = "FindAllDevicesRequest", namespace = NAMESPACE)
    @ResponsePayload
    public FindAllDevicesResponse findAllDevices(@OrganisationIdentification final String organisationIdentification,
            @RequestPayload final FindAllDevicesRequest request) throws OsgpException {

        LOGGER.info("Finding All Devices Request received from organisation: {}.", organisationIdentification);

        final FindAllDevicesResponse response = new FindAllDevicesResponse();

        try {
            final Page<Device> page = this.adHocManagementService.findAllDevices(organisationIdentification,
                    request.getPage());

            final DevicePage devicePage = new DevicePage();
            devicePage.setTotalPages(page.getTotalPages());
            devicePage.getDevices().addAll(
                    this.adHocManagementMapper.mapAsList(page.getContent(),
                            com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.Device.class));
            response.setDevicePage(devicePage);
        } catch (final MethodConstraintViolationException e) {
            LOGGER.error(EXCEPTION_OCCURRED, e);
            throw new FunctionalException(FunctionalExceptionType.VALIDATION_ERROR, COMPONENT_WS_LOAD_MANAGEMENT,
                    new ValidationException(e.getConstraintViolations()));
        } catch (final Exception e) {
            this.handleException(e);
        }

        return response;
    }

    // === SET LIGHT ===

    @PayloadRoot(localPart = "SetSwitchRequest", namespace = NAMESPACE)
    @ResponsePayload
    public SetSwitchAsyncResponse setSwitch(@OrganisationIdentification final String organisationIdentification,
            @RequestPayload final SetSwitchRequest request) throws OsgpException {

        LOGGER.info("Set Switch Request received from organisation: {} for device: {}.", organisationIdentification,
                request.getDeviceIdentification());

        final SetSwitchAsyncResponse response = new SetSwitchAsyncResponse();

        try {
            final List<LightValue> lightValues = new ArrayList<>();
            lightValues.addAll(this.adHocManagementMapper.mapAsList(request.getSwitchValue(), LightValue.class));

            final String correlationUid = this.adHocManagementService.enqueueSetSwitchRequest(
                    organisationIdentification, request.getDeviceIdentification(), lightValues);

            final AsyncResponse asyncResponse = new AsyncResponse();

            asyncResponse.setCorrelationUid(correlationUid);
            asyncResponse.setDeviceId(request.getDeviceIdentification());

            response.setAsyncResponse(asyncResponse);
        } catch (final MethodConstraintViolationException e) {
            LOGGER.error(EXCEPTION_OCCURRED, e);
            throw new FunctionalException(FunctionalExceptionType.VALIDATION_ERROR, COMPONENT_WS_LOAD_MANAGEMENT,
                    new ValidationException(e.getConstraintViolations()));
        } catch (final Exception e) {
            this.handleException(e);
        }

        return response;
    }

    @PayloadRoot(localPart = "SetSwitchAsyncRequest", namespace = NAMESPACE)
    @ResponsePayload
    public SetSwitchResponse getSetSwitchResponse(@OrganisationIdentification final String organisationIdentification,
            @RequestPayload final SetSwitchAsyncRequest request) throws OsgpException {

        LOGGER.info("Get Set Switch Response received from organisation: {} with correlationUid: {}.",
                organisationIdentification, request.getAsyncRequest().getCorrelationUid());

        final SetSwitchResponse response = new SetSwitchResponse();

        try {
            final ResponseMessage message = this.adHocManagementService.dequeueSetSwitchResponse(request
                    .getAsyncRequest().getCorrelationUid());
            if (message != null) {
                response.setResult(OsgpResultType.fromValue(message.getResult().getValue()));
            }
        } catch (final Exception e) {
            this.handleException(e);
        }

        return response;
    }

    // === GET STATUS ===

    @PayloadRoot(localPart = "GetStatusRequest", namespace = NAMESPACE)
    @ResponsePayload
    public GetStatusAsyncResponse getStatus(@OrganisationIdentification final String organisationIdentification,
            @RequestPayload final GetStatusRequest request) throws OsgpException {

        LOGGER.info("Get Status received from organisation: {} for device: {}.", organisationIdentification,
                request.getDeviceIdentification());

        final GetStatusAsyncResponse response = new GetStatusAsyncResponse();

        try {
            final String correlationUid = this.adHocManagementService.enqueueGetStatusRequest(
                    organisationIdentification, request.getDeviceIdentification());

            final AsyncResponse asyncResponse = new AsyncResponse();
            asyncResponse.setCorrelationUid(correlationUid);
            asyncResponse.setDeviceId(request.getDeviceIdentification());
            response.setAsyncResponse(asyncResponse);
        } catch (final Exception e) {
            this.handleException(e);
        }

        return response;
    }

    @PayloadRoot(localPart = "GetStatusAsyncRequest", namespace = NAMESPACE)
    @ResponsePayload
    public GetStatusResponse getGetStatusResponse(@OrganisationIdentification final String organisationIdentification,
            @RequestPayload final GetStatusAsyncRequest request) throws OsgpException {

        LOGGER.info("Get Status Response received from organisation: {} for correlationUid: {}.",
                organisationIdentification, request.getAsyncRequest().getCorrelationUid());

        final GetStatusResponse response = new GetStatusResponse();

        try {
            final ResponseMessage message = this.adHocManagementService.dequeueGetStatusResponse(request
                    .getAsyncRequest().getCorrelationUid());
            if (message != null) {
                response.setResult(OsgpResultType.fromValue(message.getResult().getValue()));

                if (message.getDataObject() != null) {
                    final DeviceStatus deviceStatus = (DeviceStatus) message.getDataObject();
                    if (deviceStatus != null) {
                        response.setDeviceStatus(this.adHocManagementMapper.map(deviceStatus,
                                com.alliander.osgp.adapter.ws.schema.loadmanagement.adhocmanagement.DeviceStatus.class));
                    }
                }
            }
        } catch (final Exception e) {
            this.handleException(e);
        }

        return response;
    }

    // === RESUME SCHEDULE ===

    @PayloadRoot(localPart = "ResumeScheduleRequest", namespace = NAMESPACE)
    @ResponsePayload
    public ResumeScheduleAsyncResponse resumeSchedule(
            @OrganisationIdentification final String organisationIdentification,
            @RequestPayload final ResumeScheduleRequest request) throws OsgpException {

        LOGGER.info("Resume Schedule Request received from organisation: {} for device: {}.",
                organisationIdentification, request.getDeviceIdentification());

        final ResumeScheduleAsyncResponse response = new ResumeScheduleAsyncResponse();

        try {
            final ResumeScheduleData resumeScheduleData = new ResumeScheduleData();
            if (request.getIndex() != null) {
                resumeScheduleData.setIndex(request.getIndex());
            }
            resumeScheduleData.setIsImmediate(request.isIsImmediate());

            final String correlationUid = this.adHocManagementService.enqueueResumeScheduleRequest(
                    organisationIdentification, request.getDeviceIdentification(), resumeScheduleData);

            final AsyncResponse asyncResponse = new AsyncResponse();
            asyncResponse.setCorrelationUid(correlationUid);
            asyncResponse.setDeviceId(request.getDeviceIdentification());
            response.setAsyncResponse(asyncResponse);
        } catch (final MethodConstraintViolationException e) {
            LOGGER.error(EXCEPTION_OCCURRED, e);
            throw new FunctionalException(FunctionalExceptionType.VALIDATION_ERROR, COMPONENT_WS_LOAD_MANAGEMENT,
                    new ValidationException(e.getConstraintViolations()));
        } catch (final Exception e) {
            this.handleException(e);
        }

        return response;
    }

    @PayloadRoot(localPart = "ResumeScheduleAsyncRequest", namespace = NAMESPACE)
    @ResponsePayload
    public ResumeScheduleResponse getResumeScheduleResponse(
            @OrganisationIdentification final String organisationIdentification,
            @RequestPayload final ResumeScheduleAsyncRequest request) throws OsgpException {

        LOGGER.info("Resume Schedule Async Request received from organisation: {} for device: {}.",
                organisationIdentification, request.getAsyncRequest().getDeviceId());

        final ResumeScheduleResponse response = new ResumeScheduleResponse();

        try {
            final ResponseMessage message = this.adHocManagementService.dequeueResumeScheduleResponse(request
                    .getAsyncRequest().getCorrelationUid());
            if (message != null) {
                response.setResult(OsgpResultType.fromValue(message.getResult().getValue()));
            }
        } catch (final Exception e) {
            this.handleException(e);
        }

        return response;
    }

    private void handleException(final Exception e) throws OsgpException {
        // Rethrow exception if it already is a functional or technical
        // exception,
        // otherwise throw new technical exception.
        LOGGER.error("Exception occurred: ", e);
        if (e instanceof OsgpException) {
            throw (OsgpException) e;
        } else {
            throw new TechnicalException(COMPONENT_WS_LOAD_MANAGEMENT, e);
        }
    }
}