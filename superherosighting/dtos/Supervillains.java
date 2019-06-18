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
public class Supervillains {

    private int villainID;

    @NotBlank(message = "First name must not be empty.")
    @Size(max = 30, message = "First name must be less than 30 characters.")
    private String villainName;

    @NotBlank(message = "Must not be empty.")
    @Size(max = 45, message = "Must be less than 45 characters.")
    private String descrip;
   
    private Superpowers superPower;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.villainID;
        hash = 17 * hash + Objects.hashCode(this.villainName);
        hash = 17 * hash + Objects.hashCode(this.descrip);
        hash = 17 * hash + Objects.hashCode(this.superPower);

        return hash;
    }

    public String getVillainName() {
        return villainName;
    }

    public void setVillainName(String villainName) {
        this.villainName = villainName;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public int getVillainID() {
        return villainID;
    }

    public void setVillainID(int villainID) {
        this.villainID = villainID;
    }

    public String getDescription() {
        return descrip;
    }

    public void setDescription(String description) {
        this.descrip = description;
    }

    public Superpowers getSuperPower() {
        return superPower;
    }

    public void setSuperPower(Superpowers superPower) {
        this.superPower = superPower;
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
        final Supervillains other = (Supervillains) obj;
        if (this.villainID != other.villainID) {
            return false;
        }
        if (!Objects.equals(this.villainName, other.villainName)) {
            return false;
        }
        if (!Objects.equals(this.descrip, other.descrip)) {
            return false;
        }
        if (!Objects.equals(this.superPower, other.superPower)) {
            return false;
        }

        return true;
    }

}
