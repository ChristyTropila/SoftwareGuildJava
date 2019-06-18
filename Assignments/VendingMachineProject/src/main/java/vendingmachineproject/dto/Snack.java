/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author ctrop
 */
public class Snack {
      //fields for my snack class
    private String snackId;
    private String name;
    private BigDecimal price;
    private int quantity;

    //Constructor with snackId for parameter
    public Snack(String snackId) {
        this.snackId = snackId;
    }
    
    //getters and setters

    public String getSnackId() {
        return snackId;
    }

    public void setSnackId(String snackId) {
        this.snackId = snackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
   @Override
    public String toString() {
        return "Snack ID: " + snackId + 
                " | Name: " + name + 
                " | Price: " + price + 
                " | Qty: " + quantity;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.snackId);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.price);
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
        final Snack other = (Snack) obj;
        if (!Objects.equals(this.snackId, other.snackId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }
    
    
}
