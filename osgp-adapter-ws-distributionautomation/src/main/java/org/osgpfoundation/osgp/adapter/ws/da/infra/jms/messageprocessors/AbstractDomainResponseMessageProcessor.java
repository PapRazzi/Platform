/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.osgpfoundation.osgp.adapter.ws.da.infra.jms.messageprocessors;

import com.alliander.osgp.domain.core.valueobjects.DeviceFunction;
import com.alliander.osgp.shared.infra.jms.Constants;
import com.alliander.osgp.shared.infra.jms.MessageProcessor;
import com.alliander.osgp.shared.infra.jms.ResponseMessageResultType;
import org.osgpfoundation.osgp.adapter.ws.da.application.services.NotificationService;
import org.osgpfoundation.osgp.adapter.ws.da.application.services.RtuResponseDataService;
import org.osgpfoundation.osgp.adapter.ws.da.domain.entities.RtuResponseData;
import org.osgpfoundation.osgp.adapter.ws.schema.distributionautomation.notification.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * Base class for MessageProcessor implementations. Each MessageProcessor
 * implementation should be annotated with @Component. Further the MessageType
 * the MessageProcessor implementation can process should be passed in at
 * construction. The Singleton instance is added to the HashMap of
 * MessageProcessors after dependency injection has completed.
 *
 */
public abstract class AbstractDomainResponseMessageProcessor implements MessageProcessor {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDomainResponseMessageProcessor.class);

    /**
     * The hash map of message processor instances.
     */
    @Autowired
    protected DomainResponseMessageProcessorMap domainResponseMessageProcessorMap;
    /**
     * The message type that a message processor implementation can handle.
     */
    protected DeviceFunction deviceFunction;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RtuResponseDataService rtuResponseDataService;

    /**
     * Construct a message processor instance by passing in the message type.
     *
     * @param deviceFunction
     *            The message type a message processor can handle.
     */
    protected AbstractDomainResponseMessageProcessor(final DeviceFunction deviceFunction) {
        this.deviceFunction = deviceFunction;
    }

    /**
     * Initialization function executed after dependency injection has finished.
     * The MessageProcessor Singleton is added to the HashMap of
     * MessageProcessors. The key for the HashMap is the integer value of the
     * enumeration member.
     */
    @PostConstruct
    public void init() {
        this.domainResponseMessageProcessorMap.addMessageProcessor(this.deviceFunction.ordinal(), this.deviceFunction.name(), this);
    }

    @Override
    public void processMessage(final ObjectMessage message) throws JMSException {
        LOGGER.debug("Processing distribution automation response message");

        String correlationUid = null;
        String messageType = null;
        String organisationIdentification = null;
        String deviceIdentification = null;

        String notificationMessage = null;
        NotificationType notificationType = null;
        ResponseMessageResultType resultType = null;
        String resultDescription = null;
        Serializable dataObject = null;

        try {
            correlationUid = message.getJMSCorrelationID();
            messageType = message.getJMSType();
            organisationIdentification = message.getStringProperty(Constants.ORGANISATION_IDENTIFICATION);
            deviceIdentification = message.getStringProperty(Constants.DEVICE_IDENTIFICATION);
            resultType = ResponseMessageResultType.valueOf(message.getStringProperty(Constants.RESULT));
            resultDescription = message.getStringProperty(Constants.DESCRIPTION);

            notificationMessage = message.getStringProperty(Constants.DESCRIPTION);
            notificationType = NotificationType.valueOf(messageType);

            dataObject = message.getObject();
        } catch (final JMSException e) {
            LOGGER.error("UNRECOVERABLE ERROR, unable to read ObjectMessage instance, giving up.", e);
            LOGGER.debug("correlationUid: {}", correlationUid);
            LOGGER.debug("messageType: {}", messageType);
            LOGGER.debug("organisationIdentification: {}", organisationIdentification);
            LOGGER.debug("deviceIdentification: {}", deviceIdentification);
            return;
        }

        try {
            LOGGER.info("Calling application service function to handle response: {}", messageType);

            this.handleMessage(organisationIdentification, messageType, deviceIdentification, correlationUid, resultType, resultDescription,
                    dataObject);

            // Send notification indicating data is available.
            this.notificationService
                    .sendNotification(organisationIdentification, deviceIdentification, resultType.name(), correlationUid, notificationMessage,
                            notificationType);

        } catch (final Exception e) {
            this.handleError(e, correlationUid, organisationIdentification, deviceIdentification, notificationType);
        }
    }

    protected void handleMessage(final String organisationIdentification, final String messageType, final String deviceIdentification,
                                 final String correlationUid, final ResponseMessageResultType resultType, final String resultDescription, final Serializable dataObject) {

        Serializable meterResponseObject;
        if (dataObject == null) {
            meterResponseObject = resultDescription;
        } else {
            meterResponseObject = dataObject;
        }

        final RtuResponseData responseData = new RtuResponseData(organisationIdentification, messageType, deviceIdentification, correlationUid,
                resultType, meterResponseObject);
        this.rtuResponseDataService.enqueue(responseData);
    }

    /**
     * In case of an error, this function can be used to send a response
     * containing the exception to the web-service-adapter.
     *
     * @param e The exception.
     * @param correlationUid The correlation UID.
     * @param organisationIdentification The organisation identification.
     * @param deviceIdentification The device identification.
     * @param notificationType The message type.
     */
    protected void handleError(final Exception e, final String correlationUid, final String organisationIdentification,
                               final String deviceIdentification, final NotificationType notificationType) {

        LOGGER.info("handeling error: {} for notification type: {}", e.getMessage(), notificationType);
        this.notificationService
                .sendNotification(organisationIdentification, deviceIdentification, "NOT_OK", correlationUid, e.getMessage(), notificationType);
    }
}
