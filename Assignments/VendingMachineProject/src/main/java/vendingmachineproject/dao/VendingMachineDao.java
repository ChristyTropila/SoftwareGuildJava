/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public interface VendingMachineDao {
    
       
    //menthods/functionality of the vending machine
    
    
    //get all snacks
    List<Snack> getAllSnacks() throws SnackInventoryPersistenceException; 
    
    //get one snack
    Snack getOneSnack(String snackId) throws SnackInventoryPersistenceException; 
            
    //update one snack to reduce quantity
    void updateSnack(String snackId) throws SnackInventoryPersistenceException; 

    
       
    // for testing 
    Snack removeSnack(String snackId) throws SnackInventoryPersistenceException;
    
    //for testing
    Snack addSnack(String snackId, Snack snack) 
            throws SnackInventoryPersistenceException;
}
