/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dtos;

import java.util.Objects;
import static javafx.scene.input.KeyCode.X;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author ctrop
 */
public class Locations {

    private int locationId;
    
    
    @NotBlank(message = "Must not be empty.")
    @Size(max = 30, message = "Must be less than 30 characters.")
    private String name;

    @NotBlank(message = "Must not be empty.")
    @Size(max = 45, message = "Must be less than 45 characters.")
    private String description;
    @NotBlank(message = "Must not be empty.")
    @Size(max = 30, message = "Must be less than 30 characters.")
    private String streetName;

    @NotBlank(message = "Must not be empty.")
    @Size(max = 20, message = "Must be less than 20 characters.")
    private String city;
    @NotBlank(message = "Must not be empty.")
    @Size(max = 20, message = "Must be less than 20 characters.")
    private String state;
    
    @NotBlank(message = "Must not be empty.")
    @Size(max = 10, message = "Must be less than 10 characters.")
    private String zipCode;

      
   @NotBlank(message = "Must not be empty.")
   @Digits(integer=2, fraction=2)
    private double longitude;
    
    
    @NotBlank(message = "Must not be empty.")
    @Digits(integer=2, fraction=2)
    private double latitude;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.locationId;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.streetName);
        hash = 59 * hash + Objects.hashCode(this.city);
        hash = 59 * hash + Objects.hashCode(this.state);
        hash = 59 * hash + Objects.hashCode(this.zipCode);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Locations other = (Locations) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.streetName, other.streetName)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        return true;
    }

}
