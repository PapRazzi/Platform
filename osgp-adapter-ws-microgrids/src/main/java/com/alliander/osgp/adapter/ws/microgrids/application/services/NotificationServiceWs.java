/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.microgrids.application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.ws.client.WebServiceIOException;

import com.alliander.osgp.adapter.ws.microgrids.presentation.ws.SendNotificationServiceClient;
import com.alliander.osgp.adapter.ws.schema.microgrids.notification.Notification;
import com.alliander.osgp.adapter.ws.schema.microgrids.notification.NotificationType;
import com.alliander.osgp.shared.exceptionhandling.WebServiceSecurityException;

@Transactional(value = "transactionManager")
@Validated
public class NotificationServiceWs implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceWs.class);

    private final SendNotificationServiceClient sendNotificationServiceClient;

    private final String notificationUrl;

    private final String notificationUsername;

    private final String notificationOrganisation;

    public NotificationServiceWs(final SendNotificationServiceClient client, final String notificationUrl,
            final String notificationUsername, final String notificationOrganisation) {
        this.sendNotificationServiceClient = client;
        this.notificationUrl = notificationUrl;
        this.notificationUsername = notificationUsername;
        this.notificationOrganisation = notificationOrganisation;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.alliander.osgp.adapter.ws.smartmetering.application.services.
     * INotificationService#sendNotification(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String,
     * com.alliander.osgp.adapter
     * .ws.schema.smartmetering.notification.NotificationType)
     */
    @Override
    public void sendNotification(final String organisationIdentification, final String deviceIdentification,
            final String result, final String correlationUid, final String message,
            final NotificationType notificationType) {

        LOGGER.info("sendNotification called with organisation: {}, correlationUid: {}, type: {}, to organisation: {}",
                this.notificationOrganisation, correlationUid, notificationType, organisationIdentification);

        final Notification notification = new Notification();
        // message is null, unless an error occurred
        notification.setMessage(message);
        notification.setResult(result);
        notification.setDeviceIdentification(deviceIdentification);
        notification.setCorrelationUid(correlationUid);
        notification.setNotificationType(notificationType);

        try {
            /*
             * Get a template for the organisation representing the OSGP
             * platform, on behalf of which the notification is sent to the
             * organisation identified by the organisationIdentification.
             */
            this.sendNotificationServiceClient.sendNotification(this.notificationOrganisation, notification,
                    this.notificationUrl, this.notificationUsername);
        } catch (final WebServiceSecurityException | WebServiceIOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
