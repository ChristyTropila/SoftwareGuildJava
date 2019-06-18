/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dtos;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author ctrop
 */
public class Superpowers {

    private int superPowerId;

    @NotBlank(message = "Must not be empty.")
    @Size(max = 30, message = "Must be less than 30 characters.")
    private String powerName;

    @NotBlank(message = "Must not be empty.")
    @Size(max = 45, message = "Must be less than 45 characters.")
    private String descrip;

    public int getSuperPowerId() {
        return superPowerId;
    }

    public void setSuperPowerId(int superPowerId) {
        this.superPowerId = superPowerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String name) {
        this.powerName = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String desciption) {
        this.descrip = desciption;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.superPowerId;
        hash = 29 * hash + Objects.hashCode(this.powerName);
        hash = 29 * hash + Objects.hashCode(this.descrip);
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
        final Superpowers other = (Superpowers) obj;
        if (this.superPowerId != other.superPowerId) {
            return false;
        }
        if (!Objects.equals(this.powerName, other.powerName)) {
            return false;
        }
        if (!Objects.equals(this.descrip, other.descrip)) {
            return false;
        }
        return true;
    }

}
