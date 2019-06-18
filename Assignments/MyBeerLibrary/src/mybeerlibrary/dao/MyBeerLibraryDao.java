/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary.dao;

import java.util.List;
import mybeerlibrary.dto.Beer;

/**
 *
 * @author ctrop
 */
public interface MyBeerLibraryDao {

    /*Adds a beer to the libary and associates it with given name and brand. If there isn't a beer associated with the user input, the result is null */
    Beer addBeer(String beerSerialNumber, Beer beer)
            throws MyBeerLibraryDaoException;

    /* A string array containing all the beers in the libary */
    List<Beer> getAllBeers()
            throws MyBeerLibraryDaoException;

    /* Returns a beer associated with the given name and brand */
    Beer getBeer(String beerSerialNumber)
            throws MyBeerLibraryDaoException;

    /*Removes a beer from the library that the user no longer wants in their library */
    Beer removeBeer(String beerSerialNumber)
            throws MyBeerLibraryDaoException;

}
