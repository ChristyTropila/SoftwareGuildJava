/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.dao;

/**
 *
 * @author ctrop
*/

public class SnackInventoryPersistenceException extends Exception{
 
    
    public SnackInventoryPersistenceException(String message) {
        super(message); 
    }
    
    public SnackInventoryPersistenceException(String message, Throwable cause) {
        super(message, cause); 
    }
}
