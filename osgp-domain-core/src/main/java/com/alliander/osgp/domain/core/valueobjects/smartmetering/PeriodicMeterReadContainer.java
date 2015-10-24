/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.valueobjects.smartmetering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeriodicMeterReadContainer implements Serializable {

    private static final long serialVersionUID = -156966569210717657L;

    private String deviceIdentification;
    private List<PeriodicMeterReads> periodicMeterReads;

    public PeriodicMeterReadContainer() {
        this.periodicMeterReads = new ArrayList<>();
    }

    public String getDeviceIdentification() {
        return this.deviceIdentification;
    }

    public void setDeviceIdentification(final String deviceIdentification) {
        this.deviceIdentification = deviceIdentification;
    }

    public List<PeriodicMeterReads> getPeriodicMeterReads() {
        return this.periodicMeterReads;
    }

    public void setPeriodicMeterReads(final List<PeriodicMeterReads> periodicMeterReads) {
        this.periodicMeterReads = periodicMeterReads;
    }

    public void addPeriodicMeterReads(final PeriodicMeterReads periodicMeterReads) {
        this.periodicMeterReads.add(periodicMeterReads);
    }

}