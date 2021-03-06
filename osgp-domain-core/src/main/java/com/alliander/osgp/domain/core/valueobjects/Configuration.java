/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.valueobjects;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.joda.time.DateTime;

import com.alliander.osgp.domain.core.validation.LightTypeAndConfiguration;
import com.alliander.osgp.domain.core.validation.LongTermIntervalAndLongTermIntervalType;
import com.alliander.osgp.domain.core.validation.ShortTermHistoryIntervalMinutes;
import com.alliander.osgp.domain.core.validation.TlsConfiguration;

@LightTypeAndConfiguration
@LongTermIntervalAndLongTermIntervalType
@ShortTermHistoryIntervalMinutes
@TlsConfiguration
public class Configuration implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 8359276160483972289L;

    private final LightType lightType;

    @Valid
    private final DaliConfiguration daliConfiguration;

    @Valid
    private final RelayConfiguration relayConfiguration;

    @Min(0)
    private final Integer shortTermHistoryIntervalMinutes;

    @Min(0)
    private final Integer longTermHistoryInterval;

    private final LongTermIntervalType longTermHistoryIntervalType;

    private final LinkType preferredLinkType;

    private final MeterType meterType;

    private Integer timeSyncFrequency;

    private DeviceFixedIp deviceFixedIp;

    private Boolean dhcpEnabled;

    private Boolean tlsEnabled;

    private Integer tlsPortNumber;

    private String commonNameString;

    private Integer communicationTimeout;

    private Integer communicationNumberOfRetries;

    private Integer communicationPauseTimeBetweenConnectionTrials;

    private String osgpIpAddress;

    private Integer osgpPortNumber;

    private Boolean testButtonEnabled;

    private Boolean automaticSummerTimingEnabled;

    private Integer astroGateSunRiseOffset;

    private Integer astroGateSunSetOffset;

    private List<Integer> switchingDelays;

    private List<RelayMatrix> relayLinking;

    private Boolean relayRefreshing;

    private DateTime summerTimeDetails;

    private DateTime winterTimeDetails;

    public Configuration(final LightType lightType, final DaliConfiguration daliConfiguration,
            final RelayConfiguration relayConfiguration, final Integer shortTermHistoryIntervalMinutes,
            final LinkType preferredLinkType, final MeterType meterType, final Integer longTermHistoryInterval,
            final LongTermIntervalType longTermHistoryIntervalType) {
        this.lightType = lightType;
        this.daliConfiguration = daliConfiguration;
        this.relayConfiguration = relayConfiguration;
        this.shortTermHistoryIntervalMinutes = shortTermHistoryIntervalMinutes;
        this.preferredLinkType = preferredLinkType;
        this.meterType = meterType;
        this.longTermHistoryInterval = longTermHistoryInterval;
        this.longTermHistoryIntervalType = longTermHistoryIntervalType;
    }

    public MeterType getMeterType() {
        return this.meterType;
    }

    public LightType getLightType() {
        return this.lightType;
    }

    public DaliConfiguration getDaliConfiguration() {
        return this.daliConfiguration;
    }

    public RelayConfiguration getRelayConfiguration() {
        return this.relayConfiguration;
    }

    public Integer getShortTermHistoryIntervalMinutes() {
        return this.shortTermHistoryIntervalMinutes;
    }

    public LinkType getPreferredLinkType() {
        return this.preferredLinkType;
    }

    public Integer getLongTermHistoryInterval() {
        return this.longTermHistoryInterval;
    }

    public LongTermIntervalType getLongTermHistoryIntervalType() {
        return this.longTermHistoryIntervalType;
    }

    public Integer getTimeSyncFrequency() {
        return this.timeSyncFrequency;
    }

    public void setTimeSyncFrequency(final Integer timeSyncFrequency) {
        this.timeSyncFrequency = timeSyncFrequency;
    }

    public DeviceFixedIp getDeviceFixedIp() {
        return this.deviceFixedIp;
    }

    public void setDeviceFixedIp(final DeviceFixedIp deviceFixedIp) {
        this.deviceFixedIp = deviceFixedIp;
    }

    public Boolean isDhcpEnabled() {
        return this.dhcpEnabled;
    }

    public void setDhcpEnabled(final Boolean dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
    }

    public Boolean isTlsEnabled() {
        return this.tlsEnabled;
    }

    public void setTlsEnabled(final Boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
    }

    public Integer getTlsPortNumber() {
        return this.tlsPortNumber;
    }

    public void setTlsPortNumber(final Integer tlsPortNumber) {
        this.tlsPortNumber = tlsPortNumber;
    }

    public String getCommonNameString() {
        return this.commonNameString;
    }

    public void setCommonNameString(final String commonNameString) {
        this.commonNameString = commonNameString;
    }

    public Integer getCommunicationTimeout() {
        return this.communicationTimeout;
    }

    public void setCommunicationTimeout(final Integer communicationTimeout) {
        this.communicationTimeout = communicationTimeout;
    }

    public Integer getCommunicationNumberOfRetries() {
        return this.communicationNumberOfRetries;
    }

    public void setCommunicationNumberOfRetries(final Integer communicationNumberOfRetries) {
        this.communicationNumberOfRetries = communicationNumberOfRetries;
    }

    public Integer getCommunicationPauseTimeBetweenConnectionTrials() {
        return this.communicationPauseTimeBetweenConnectionTrials;
    }

    public void setCommunicationPauseTimeBetweenConnectionTrials(
            final Integer communicationPauseTimeBetweenConnectionTrials) {
        this.communicationPauseTimeBetweenConnectionTrials = communicationPauseTimeBetweenConnectionTrials;
    }

    public String getOsgpIpAddress() {
        return this.osgpIpAddress;
    }

    public void setOsgpIpAddress(final String osgpIpAddress) {
        this.osgpIpAddress = osgpIpAddress;
    }

    public Integer getOsgpPortNumber() {
        return this.osgpPortNumber;
    }

    public void setOsgpPortNumber(final Integer osgpPortNumber) {
        this.osgpPortNumber = osgpPortNumber;
    }

    public Boolean isTestButtonEnabled() {
        return this.testButtonEnabled;
    }

    public void setTestButtonEnabled(final Boolean testButtonEnabled) {
        this.testButtonEnabled = testButtonEnabled;
    }

    public Boolean isAutomaticSummerTimingEnabled() {
        return this.automaticSummerTimingEnabled;
    }

    public void setAutomaticSummerTimingEnabled(final Boolean automaticSummerTimingEnabled) {
        this.automaticSummerTimingEnabled = automaticSummerTimingEnabled;
    }

    public void setRelayLinking(final List<RelayMatrix> relayLinking) {
        this.relayLinking = relayLinking;
    }

    public Integer getAstroGateSunRiseOffset() {
        return this.astroGateSunRiseOffset;
    }

    public void setAstroGateSunRiseOffset(final Integer astroGateSunRiseOffset) {
        this.astroGateSunRiseOffset = astroGateSunRiseOffset;
    }

    public Integer getAstroGateSunSetOffset() {
        return this.astroGateSunSetOffset;
    }

    public void setAstroGateSunSetOffset(final Integer astroGateSunSetOffset) {
        this.astroGateSunSetOffset = astroGateSunSetOffset;
    }

    public Boolean isRelayRefreshing() {
        return this.relayRefreshing;
    }

    public void setRelayRefreshing(final Boolean relayRefreshing) {
        this.relayRefreshing = relayRefreshing;
    }

    public List<Integer> getSwitchingDelays() {
        return this.switchingDelays;
    }

    public void setSwitchingDelays(final List<Integer> switchingDelays) {
        this.switchingDelays = switchingDelays;
    }

    public List<RelayMatrix> getRelayLinking() {
        return this.relayLinking;
    }

    public DateTime getSummerTimeDetails() {
        return this.summerTimeDetails;
    }

    public DateTime getWinterTimeDetails() {
        return this.winterTimeDetails;
    }

    public void setSummerTimeDetails(final DateTime summerTimeDetails) {
        this.summerTimeDetails = summerTimeDetails;
    }

    public void setWinterTimeDetails(final DateTime winterTimeDetails) {
        this.winterTimeDetails = winterTimeDetails;
    }
}
