/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.service;

/**
 *
 * @author ctrop
 */
public class VendingMachineInvalidSelectionException extends Exception{
    
    public VendingMachineInvalidSelectionException(String message) {
        super(message);
    }

    public VendingMachineInvalidSelectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
