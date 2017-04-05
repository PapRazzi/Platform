package com.smartsocietyservices.osgp.domain.da.valueobjects;

import java.io.Serializable;

public class GetDeviceModelRequest implements Serializable {
    private final String deviceIdentifier;

    public GetDeviceModelRequest( final String deviceIdentifier ) {

        this.deviceIdentifier = deviceIdentifier;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }
}
