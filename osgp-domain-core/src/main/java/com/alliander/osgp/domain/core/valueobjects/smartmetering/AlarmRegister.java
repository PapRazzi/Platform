/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.valueobjects.smartmetering;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class AlarmRegister implements Serializable, ActionValueResponseObject {

    private static final long serialVersionUID = 2319359505656305783L;

    private final Set<AlarmType> alarmTypes;

    public AlarmRegister(final Set<AlarmType> alarmTypes) {
        this.alarmTypes = new TreeSet<AlarmType>(alarmTypes);
    }

    @Override
    public String toString() {
        return "AlarmTypes[" + this.alarmTypes + "]";
    }

    public Set<AlarmType> getAlarmTypes() {
        return Collections.unmodifiableSet(this.alarmTypes);
    }
}
