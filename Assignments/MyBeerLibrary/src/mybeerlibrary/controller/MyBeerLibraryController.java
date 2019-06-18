/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary.controller;

import java.util.List;
import mybeerlibrary.dao.MyBeerLibraryDao;
import mybeerlibrary.dao.MyBeerLibraryDaoException;
import mybeerlibrary.dao.MyBeerLibraryDaoFileImpl;
import mybeerlibrary.dto.Beer;
import mybeerlibrary.ui.MyBeerLibraryView;
import mybeerlibrary.ui.UserIO;
import mybeerlibrary.ui.UserIOConsoleImpl;

/**
 *
 * @author ctrop
 */
public class MyBeerLibraryController {

    //Brains of my application. Will display my method, get the user's menu choice, and then calls a method to perform the user's menu choice.
    MyBeerLibraryView view;
    MyBeerLibraryDao dao;

    //constructor that initializes the fields dao and view
    public MyBeerLibraryController(MyBeerLibraryDao dao, MyBeerLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing) {

                menuSelection = view.printMenuAndGetSelection();

                switch (menuSelection) { //using a switch statement to navigate through the user's choice
                    case 1:
                        listBeers();
                        break;
                    case 2:
                        addBeer();
                        break;
                    case 3:
                        viewBeer();
                        break;
                    case 4:
                        removeBeer();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (MyBeerLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    //a method that will add a beer to our library
    private void addBeer() throws MyBeerLibraryDaoException {
        view.displayAddBeerBanner();
        Beer newBeer = view.getBeerInfo();
        dao.addBeer(newBeer.getBeerSerialNumber(), newBeer);
        view.displayCreateSuccessBanner();
    }

    private void listBeers() throws MyBeerLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Beer> beerList = dao.getAllBeers();
        view.displayBeerList(beerList);
    }

    //a  method to view beers
    private void viewBeer() throws MyBeerLibraryDaoException {
        view.displayDisplayBeerBanner();
        String beerSerialNumber = view.getBeerSerialNumberChoice();
        Beer beer = dao.getBeer(beerSerialNumber);
        view.displayBeer(beer);
    }

    //creating remove beer method
    private void removeBeer() throws MyBeerLibraryDaoException {
        view.displayRemoveBeerBanner();
        String beerSerialNumber = view.getBeerSerialNumberChoice();
        dao.removeBeer(beerSerialNumber);
        view.displayRemoveSuccessBanner();

    }

    // two methods to display an exit message or unknown command
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
