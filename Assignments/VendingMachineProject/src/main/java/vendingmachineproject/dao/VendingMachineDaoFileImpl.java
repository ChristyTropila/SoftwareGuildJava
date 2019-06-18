/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    
    
    //use a Map to store the snack inventory
    
    Map<String, Snack> snackInventory = new HashMap<>();
    
    //list the file to read the inventory from and the delimitter
    
    public static final String SNACKS_FILE = "snackInventory.txt";
    public static final String DELIMITER = "::";
    
    
    //
    @Override
    public List<Snack> getAllSnacks() throws SnackInventoryPersistenceException {
      readSnackInventory();
      return new ArrayList<Snack>(snackInventory.values());
    }

    @Override
    public Snack getOneSnack(String snackId) throws SnackInventoryPersistenceException {
 readSnackInventory();
 return snackInventory.get(snackId);
    }

    @Override
    public void updateSnack(String snackId) throws SnackInventoryPersistenceException {
 Snack snackToUpdate = snackInventory.get(snackId);
 int currentQty = snackToUpdate.getQuantity();
 snackToUpdate.setQuantity(currentQty -1 );
 snackInventory.put(snackId, snackToUpdate);
 writeSnackInventory();

    }
    
    
    //file persistence
     // Reads the snack inventory from the snackInventory.txt file
   
    public void readSnackInventory() throws SnackInventoryPersistenceException {
        Scanner myScanner;

        try {
            myScanner = new Scanner(new BufferedReader(new FileReader(SNACKS_FILE)));

        } catch (FileNotFoundException e) {
            throw new SnackInventoryPersistenceException(
                    "Could not load Snack Inventory data.", e);
        }

        //currentSnack will read all the snack information on one line
        String currentLine;

        //currentToken will hold an array of all the Snack information on each line
        String[] currentToken;

        //while loop will iterate as long as there are lines in the snackInventory.txt file
        while (myScanner.hasNext()) {

            //reading a line and assigning it to currentLine
            currentLine = myScanner.nextLine();

            //splitting up the line using DELIMITER assigned above ("::") and 
            //  assigning each item currentToken array
            currentToken = currentLine.split(DELIMITER);

            //Creating a new Snack object.  CurrentToken[0] is the title passed
            //  in as a parameter to the constructor
            Snack currentSnack = new Snack(currentToken[0]);

            //setting the rest of the properties
            currentSnack.setName(currentToken[1]);

            currentSnack.setPrice(new BigDecimal(currentToken[2]));
            currentSnack.setQuantity(Integer.parseInt(currentToken[3]));

            //putting the currentSnack into the map
            snackInventory.put(currentSnack.getSnackId(), currentSnack);
        }
        //closing the scanner
        myScanner.close();
    }

 
    // Writes snack's in memory to a txt file
     
    public void writeSnackInventory() throws SnackInventoryPersistenceException {

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(SNACKS_FILE));

            //creating a List of type Snack and calling the getAllSnacks() method created above
            List<Snack> snackList = this.getAllSnacks();

            //Printing all the properties of the currentSnack object w/ the DELIMITER so it's formatted correctly
            for (Snack currentSnack : snackList) {
                pw.println(currentSnack.getSnackId() + DELIMITER
                        + currentSnack.getName() + DELIMITER
                        + currentSnack.getPrice() + DELIMITER
                        + currentSnack.getQuantity());

                //flushing the pw flusher each time through the for loop
                pw.flush();
            }

            pw.close();

        } catch (IOException e) {
            //translating the IOException
            throw new SnackInventoryPersistenceException("Error. Unable to save data.", e);
        }
    }

   
    @Override
    public Snack removeSnack(String snackId)
            throws SnackInventoryPersistenceException {
        Snack removedSnack = snackInventory.remove(snackId);
        writeSnackInventory();
        return removedSnack;
    }
    
    @Override
    public Snack addSnack(String snackId, Snack snack)
            throws SnackInventoryPersistenceException {
        Snack newSnack = snackInventory.put(snackId, snack);
        writeSnackInventory();
        return newSnack;
    }

    
}
