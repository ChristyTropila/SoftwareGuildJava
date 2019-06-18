/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import mybeerlibrary.dto.Beer;

/**
 *
 * @author ctrop
 */
public class MyBeerLibraryDaoFileImpl implements MyBeerLibraryDao {

    public static final String LIBRARY_FILE = "library.txt";
    public static final String DELIMITER = "::";

    private Map<String, Beer> beers = new HashMap(); //using a map that contains a key value to look up beers

    @Override
    public Beer addBeer(String beerSerialNumber, Beer beer)
            throws MyBeerLibraryDaoException {
        Beer newBeer = beers.put(beerSerialNumber, beer);
        writeLibrary();
        return newBeer;
    }

    @Override
    public List<Beer> getAllBeers()
            throws MyBeerLibraryDaoException {
        loadLibrary();
        return new ArrayList<Beer>(beers.values());
    }

    @Override
    public Beer getBeer(String beerSerialNumber)
            throws MyBeerLibraryDaoException {
        loadLibrary();
        return beers.get(beerSerialNumber);
    }

    @Override
    public Beer removeBeer(String beerSerialNumber)
            throws MyBeerLibraryDaoException {
        Beer removedBeer = beers.remove(beerSerialNumber);
        writeLibrary();
        return removedBeer;
    }

    private void loadLibrary() throws MyBeerLibraryDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new MyBeerLibraryDaoException(
                    "-_- Could not load Beer data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;

        String[] currentTokens;

        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // break up the line into tokens
            currentTokens = currentLine.split(DELIMITER);

            Beer currentBeer = new Beer();
            currentBeer.setBeerSerialNumber(currentTokens[0]);
            currentBeer.setBeerName(currentTokens[1]);
            currentBeer.setBeerBrand(currentTokens[2]);
            currentBeer.setBeerType(currentTokens[3]);

            // Put current beer into the map using beer serial number as the key
            beers.put(currentBeer.getBeerSerialNumber(), currentBeer);
        }
        // close scanner
        scanner.close();
    }

    private void writeLibrary() throws MyBeerLibraryDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new MyBeerLibraryDaoException("Could not save the beer data ", e);
        }

        List<Beer> beerList = this.getAllBeers();
        for (Beer currentBeer : beerList) {
            out.println(currentBeer.getBeerSerialNumber() + DELIMITER + currentBeer.getBeerName() + DELIMITER + currentBeer.getBeerBrand()
                    + DELIMITER + currentBeer.getBeerType());
            out.flush(); //forcing printwriter to write line to file
        }

        out.close(); //clean up
    }
}
