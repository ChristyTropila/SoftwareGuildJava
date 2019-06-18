/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import vendingmachineproject.dao.SnackInventoryPersistenceException;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public interface VendingMachineServiceLayer {
        //get all snacks
    List<Snack> getAllSnacks() throws SnackInventoryPersistenceException;

    //get one snack with snackId and UserMoney
    Snack buyOneSnack(String snackId, BigDecimal userMoney)
            throws VendingMachineInvalidSelectionException,NotSufficientAmountOfMoneyException,
            SnackInventoryPersistenceException;

    //get one snack with just snackId
    Snack getOneSnack(String snackId) throws SnackInventoryPersistenceException;

    //update one snack to reduce quantity
    void updateSnack(String snackId) throws SnackInventoryPersistenceException;

    //make change
    Map<Coin, BigDecimal> makeChange(BigDecimal changeDue);
    
        // for testing 
    Snack removeSnack(String snackId) throws SnackInventoryPersistenceException;
    
    //for testing
    Snack addSnack(String snackId, Snack snack) 
            throws SnackInventoryPersistenceException;
    
}
