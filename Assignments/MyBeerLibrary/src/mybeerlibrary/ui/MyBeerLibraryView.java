/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary.ui;

import java.util.List;
import mybeerlibrary.dto.Beer;

/**
 *
 * @author ctrop
 */
public class MyBeerLibraryView {

    //functionality for printing menu and getting user's selection from the controller
    private UserIO io;  //composition

    //constructor that initializes the io
    public MyBeerLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. List All Your Beers");
        io.print("2. Add Beer To Your List");
        io.print("3. View An Individual Beer");
        io.print("4. Remove Beer From Your List");
        io.print("5. Exit");

        return io.readInt("Please select from the above choices ", 1, 5);
    }

    public Beer getBeerInfo() {
        String beerSerialNumber = io.readString("Please enter Beer Serial Number");
        String beerName = io.readString("Please enter the name of the Beer");
        String beerBrand = io.readString("Please enter the brand of the Beer");
        String beerType = io.readString("Please enter the beer type. e.g IPA, Lager, Stout, Pilsner..ect");
        Float alcoholPercentage = io.readFloat("Please enter the alcohol percentage");
        Beer currentBeer = new Beer();
        currentBeer.setBeerSerialNumber(beerSerialNumber);
        currentBeer.setBeerName(beerName);
        currentBeer.setBeerBrand(beerBrand);
        currentBeer.setBeerType(beerType);
        currentBeer.setAlcoholPercentage(alcoholPercentage);
        return currentBeer;
    }

    public void displayAddBeerBanner() {
        io.print("==Add Beer==");
    }

    public void displayCreateSuccessBanner() {
        io.readString("Beer was successfully added. Please hit enter to continute");
    }

    //takes a list of Beer objects as parameters and displays the information for each beer to the screen
    public void displayBeerList(List<Beer> beerList) {
        for (Beer currentBeer : beerList) {
            io.print(currentBeer.getBeerSerialNumber() + ":" + currentBeer.getBeerName() + " " + currentBeer.getBeerBrand() + " " + currentBeer.getBeerType());
        }
        io.readString("Please hit enter to continute");
    }

    public void displayDisplayAllBanner() {
        io.print("==Display All Beers");
    }

    public void displayDisplayBeerBanner() {
        io.print("==Display Beer==");
    }

    public String getBeerSerialNumberChoice() {
        return io.readString("Please enter the beer serial number");

    }

    public void displayBeer(Beer beer) {
        if (beer != null) {
            io.print(beer.getBeerSerialNumber());
            io.print(beer.getBeerBrand() + " " + beer.getBeerName());
            io.print(beer.getBeerType());
            io.print(Float.toString(beer.getAlcoholPercentage()));
            
            io.print(" ");
        } else {
            io.print("No such Beer");
        }
        io.readString("Pleaase hit enter to continue");

    }

    //removed beer banners
    public void displayRemoveBeerBanner() {
        io.print("== Remove Beer==");
    }

    public void displayRemoveSuccessBanner() {
        io.readString("The Beer was successfullly removed. Please hit enter to continute");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("==ERROR==");
        io.print(errorMsg);
    }

}
