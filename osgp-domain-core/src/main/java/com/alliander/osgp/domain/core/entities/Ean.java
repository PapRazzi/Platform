/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.alliander.osgp.shared.domain.entities.AbstractEntity;

@Entity
public class Ean extends AbstractEntity {

    private static final long serialVersionUID = 2569469187462546946L;

    @ManyToOne()
    @JoinColumn()
    private Device device;

    @Column(nullable = false)
    private Long code;

    @Column()
    private String description;

    public Ean() {
        // Default constructor
    }

    public Ean(final Device device, final Long code, final String description) {
        this.device = device;
        this.code = code;
        this.description = description;
    }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(final Device device) {
        this.device = device;
    }

    public Long getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        boolean result = false;
        if (o != null && o instanceof Ean) {
            final Ean that = (Ean) o;

            // "code" is a unique identifier for an EAN, so check
            // equality for just that property:
            result = Objects.equals(this.code, that.code);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.device, this.code, this.description);
    }
}
