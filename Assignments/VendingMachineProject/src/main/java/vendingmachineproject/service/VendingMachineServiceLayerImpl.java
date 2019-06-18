/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vendingmachineproject.dao.SnackInventoryPersistenceException;
import vendingmachineproject.dao.VendingMachineAuditDao;
import vendingmachineproject.dao.VendingMachineDao;
import static vendingmachineproject.dao.VendingMachineDaoFileImpl.DELIMITER;
import static vendingmachineproject.dao.VendingMachineDaoFileImpl.SNACKS_FILE;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
    
    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    Map<String, Snack> snackInventory = new HashMap<>();

    //constructor
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao= auditDao;

    }

    @Override
    public List<Snack> getAllSnacks() throws SnackInventoryPersistenceException {
        return dao.getAllSnacks();
    }

    @Override
    public Snack getOneSnack(String snackId)
            throws SnackInventoryPersistenceException {

        return dao.getOneSnack(snackId);
    }

    /**
     *
     * @param snackId
     * @param userMoney
     * @return
     * @throws VendingMachineInvalidSelectionException
     * @throws NotSufficientAmountOfMoneyException
     * @throws SnackInventoryPersistenceException
     */
    @Override
    public Snack buyOneSnack(String snackId, BigDecimal userMoney)
            throws VendingMachineInvalidSelectionException, NotSufficientAmountOfMoneyException,
            SnackInventoryPersistenceException {

        //is the selection valid
        validateSelection(snackId);

        //is there inventory for the selected item
        checkInventory(snackId);

        //is there enough money
        sufficientMoney(snackId, userMoney);
         updateSnack(snackId);
        return dao.getOneSnack(snackId);
    }

    @Override
    public void updateSnack(String snackId) throws SnackInventoryPersistenceException {
       dao.updateSnack(snackId);
     
    //    auditDao.writeAuditEntry(snackId);
        
    }

    @Override
     public Map<Coin, BigDecimal> makeChange(BigDecimal changeDue) {
    BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");
        BigDecimal penny = new BigDecimal("0.01");

        BigDecimal numberOfQuarters;
        BigDecimal numberOfDimes;
        BigDecimal numberOfNickels;
        BigDecimal numberOfPennies;

        BigDecimal valueOfQuartersDispensed;
        BigDecimal valueOfDimesDispensed;
        BigDecimal valueOfNickelsDispensed;
        BigDecimal valueOfPenniesDispensed;
  
          Map<Coin, BigDecimal> listOfChange = new HashMap<>();

        //calculates the number of quarters
        numberOfQuarters = changeDue.divide(quarter); //divides the number remaaining by .25
        valueOfQuartersDispensed = numberOfQuarters.setScale(0, RoundingMode.DOWN).multiply(quarter);

        //adding quarters to the hashmap
        listOfChange.put(Coin.QUARTER, numberOfQuarters);

        BigDecimal changeAfterQuarters = changeDue.subtract(valueOfQuartersDispensed);

        //calculates the number of dimes
        //after subtracting the amount of quarters, determine how many dimes can be calculated by dividing by .10
        numberOfDimes = changeAfterQuarters.divide(dime);
        valueOfDimesDispensed = numberOfDimes.setScale(0, RoundingMode.DOWN).multiply(dime);

        //adding dimes to the hashmap
        listOfChange.put(Coin.DIME, numberOfDimes);

        BigDecimal changeAfterDimes = changeAfterQuarters.subtract(valueOfDimesDispensed);

        //calculates the number of nickels that can be dispensed after determining the dimes. Divide by .05
        numberOfNickels = changeAfterDimes.divide(nickel);
        valueOfNickelsDispensed = numberOfNickels.setScale(0, RoundingMode.DOWN).multiply(nickel);

        listOfChange.put(Coin.NICKEL, numberOfNickels);

        BigDecimal changeAfterNickels = changeAfterDimes.subtract(valueOfNickelsDispensed);

        //calculates the number of pennies that are left over after all possible nickles, dimes, and quarters
        numberOfPennies = changeAfterNickels.divide(penny);
        valueOfPenniesDispensed= numberOfPennies.setScale(0, RoundingMode.DOWN).multiply(penny);
        
        listOfChange.put(Coin.PENNNY, numberOfPennies);
        
     
        return listOfChange;
        
        
    }
 
    

    //Helper Methods ***********************************************************
    private void validateSelection(String selection)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {

        if (dao.getOneSnack(selection) == null) {
            throw new VendingMachineInvalidSelectionException("ERROR: That"
                    + " is not a valid selection.");
        }
    }

    private void checkInventory(String snackId)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {

        Snack selectedSnack = dao.getOneSnack(snackId);
        if (selectedSnack.getQuantity() < 1) {
            throw new VendingMachineInvalidSelectionException("ERROR: Sorry,"
                    + " that's out of stock.");
        }
    }

    private void sufficientMoney(String snackId, BigDecimal userMoney)
            throws NotSufficientAmountOfMoneyException,
            SnackInventoryPersistenceException {

        Snack selectedSnack = dao.getOneSnack(snackId);

     if (userMoney.compareTo(selectedSnack.getPrice()) < 0) {
            throw new NotSufficientAmountOfMoneyException("ERROR: Sorry, "
                    + "you don't have enough money to buy that. ");
        }
    }
    
     // Writes snack's in memory to a txt file
    


    @Override
    public Snack removeSnack(String snackId) throws SnackInventoryPersistenceException {
      Snack removedSnack = snackInventory.remove(snackId);
       
        return removedSnack;    
    }

    @Override
    public Snack addSnack(String snackId, Snack snack) throws SnackInventoryPersistenceException {
        Snack newSnack = snackInventory.put(snackId, snack);
       
        return newSnack;
    }
}
