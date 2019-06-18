/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary.dto;

/**
 *
 * @author ctrop
 */
public class Beer {
    private String beerName;
    private String beerBrand;
    private String beerSerialNumber;
    private String beerType;
    private float alcoholPercentage;

    
    //constructor with no prameters
    public Beer(){
        
    }
   

    
    //getters and setters for my fields
    
    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerBrand() {
        return beerBrand;
    }

    public void setBeerBrand(String beerBrand) {
        this.beerBrand = beerBrand;
    }
    
    

    public String getBeerSerialNumber() {
        return beerSerialNumber;
    }

    public void setBeerSerialNumber(String beerSerialNumber){
        this.beerSerialNumber=beerSerialNumber;
    }

    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = beerType;
    }

    public float getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(float alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }
    
    
    
    
    
}
