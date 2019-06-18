/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.service;

/**
 *
 * @author ctrop
 */
public class StateTaxValidationException extends Exception {
    
    
    public StateTaxValidationException(String message) {
        super(message);
    }

    public StateTaxValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
