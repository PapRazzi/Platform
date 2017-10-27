/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.loadmanagement.application.config;

import javax.annotation.Resource;

import org.apache.activemq.RedeliveryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import com.alliander.osgp.adapter.ws.infra.jms.LoggingMessageSender;
import com.alliander.osgp.adapter.ws.loadmanagement.infra.jms.LoadManagementRequestMessageSender;
import com.alliander.osgp.adapter.ws.loadmanagement.infra.jms.LoadManagementResponseMessageFinder;
import com.alliander.osgp.shared.application.config.AbstractMessagingConfig;
import com.alliander.osgp.shared.application.config.jms.JmsConfiguration;
import com.alliander.osgp.shared.application.config.jms.JmsConfigurationFactory;
import com.alliander.osgp.shared.application.config.jms.JmsConfigurationNames;
import com.alliander.osgp.shared.application.config.jms.JmsPropertyNames;

@Configuration
@PropertySources({ @PropertySource("classpath:osgp-adapter-ws-loadmanagement.properties"),
        @PropertySource(value = "file:${osgp/Global/config}", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${osgp/AdapterWsLoadManagement/config}", ignoreResourceNotFound = true), })
public class MessagingConfig extends AbstractMessagingConfig {

    public static final String PROPERTY_NAME_JMS_RECEIVE_TIMEOUT = "jms.loadmanagement.responses.receive.timeout";

    @Resource
    private Environment environment;

    // === JMS SETTINGS ===

    @Override
    @Bean
    public RedeliveryPolicy defaultRedeliveryPolicy() {
        final RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setInitialRedeliveryDelay(Long.parseLong(this.environment
                .getRequiredProperty(JmsPropertyNames.PROPERTY_NAME_JMS_DEFAULT_INITIAL_REDELIVERY_DELAY)));
        redeliveryPolicy.setMaximumRedeliveries(Integer.parseInt(this.environment
                .getRequiredProperty(JmsPropertyNames.PROPERTY_NAME_JMS_DEFAULT_MAXIMUM_REDELIVERIES)));
        redeliveryPolicy.setMaximumRedeliveryDelay(Long.parseLong(this.environment
                .getRequiredProperty(JmsPropertyNames.PROPERTY_NAME_JMS_DEFAULT_MAXIMUM_REDELIVERY_DELAY)));
        redeliveryPolicy.setRedeliveryDelay(Long.parseLong(this.environment
                .getRequiredProperty(JmsPropertyNames.PROPERTY_NAME_JMS_DEFAULT_REDELIVERY_DELAY)));

        return redeliveryPolicy;
    }

    // === JMS SETTINGS: LOAD MANAGEMENT REQUESTS ===

    @Bean
    public JmsConfiguration loadManagementRequestsJmsConfiguration(final JmsConfigurationFactory jmsConfigurationFactory) {
        return jmsConfigurationFactory.initializeConfiguration(JmsConfigurationNames.JMS_LOADMANAGEMENT_REQUESTS);
    }

    @Bean(name = "wsLoadManagementOutgoingRequestsJmsTemplate")
    public JmsTemplate loadManagementRequestsJmsTemplate(final JmsConfiguration loadManagementRequestsJmsConfiguration) {
        return loadManagementRequestsJmsConfiguration.getJmsTemplate();
    }

    @Bean(name = "wsLoadManagementOutgoingRequestsMessageSender")
    public LoadManagementRequestMessageSender loadManagementRequestMessageSender() {
        return new LoadManagementRequestMessageSender();
    }

    // === JMS SETTINGS: LOAD MANAGEMENT RESPONSES ===

    @Bean
    public JmsConfiguration loadManagementResponsesJmsConfiguration(
            final JmsConfigurationFactory jmsConfigurationFactory) {
        return jmsConfigurationFactory.initializeConfiguration(JmsConfigurationNames.JMS_LOADMANAGEMENT_RESPONSES);
    }

    @Bean(name = "wsLoadManagementIncomingResponsesJmsTemplate")
    public JmsTemplate loadManagementResponsesJmsTemplate(final JmsConfiguration loadManagementResponsesJmsConfiguration) {
        final Long receiveTimeout = Long.parseLong(this.environment
                .getRequiredProperty(PROPERTY_NAME_JMS_RECEIVE_TIMEOUT));

        final JmsTemplate jmsTemplate = loadManagementResponsesJmsConfiguration.getJmsTemplate();
        jmsTemplate.setReceiveTimeout(receiveTimeout);
        return jmsTemplate;
    }

    @Bean(name = "wsLoadManagementIncomingResponsesMessageFinder")
    public LoadManagementResponseMessageFinder loadManagementResponseMessageFinder() {
        return new LoadManagementResponseMessageFinder();
    }

    // === JMS SETTINGS: LOAD MANAGEMENT LOGGING ===

    @Bean
    public JmsConfiguration loggingJmsConfiguration(final JmsConfigurationFactory jmsConfigurationFactory) {
        return jmsConfigurationFactory.initializeConfiguration(JmsConfigurationNames.JMS_LOADMANAGEMENT_LOGGING);
    }

    @Bean
    public JmsTemplate loggingJmsTemplate(final JmsConfiguration loggingJmsConfiguration) {
        return loggingJmsConfiguration.getJmsTemplate();
    }

    @Bean
    public LoggingMessageSender loggingMessageSender() {
        return new LoggingMessageSender();
    }
}
