/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import vendingmachineproject.dao.SnackInventoryPersistenceException;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;
import vendingmachineproject.service.NotSufficientAmountOfMoneyException;
import vendingmachineproject.service.VendingMachineInvalidSelectionException;
import vendingmachineproject.service.VendingMachineServiceLayer;
import vendingmachineproject.ui.VendingMachineView;

/**
 *
 * @author ctrop
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    //Constructor
    public VendingMachineController(VendingMachineView view, 
            VendingMachineServiceLayer service) {
        
        this.view = view;
        this.service = service;
    }

    
    //method that executes the program
    public void run() {
        int continueOrExit;  //declaring a variable to hold the user's choice of continuing or exiting
       // boolean useAgain=true;
        
      
        try {
            displayOptions(); //call to the view to display the menu
 
            continueOrExit = getContinueOrExit();
 
            BigDecimal changeDue;

            switch (continueOrExit) {
                case 1:
                    changeDue = makePurchase();
                    getChange(changeDue);
                   exitMessage();
                    break;
                case 2:
                    exitMessage();
                    break;
                default:
                    unknownCommand();
            }

        } catch (VendingMachineInvalidSelectionException e) {
    
        } catch (SnackInventoryPersistenceException e) {


    }
       
       }
  

    //Controller methods 
    private void displayOptions()
            throws SnackInventoryPersistenceException {

        List<Snack> allSnacks = service.getAllSnacks(); 
        view.displayOptions(allSnacks);
    }

    private int getContinueOrExit() {
        return view.continueOrExit();
    }

    private BigDecimal makePurchase()
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {

        BigDecimal userMoney = BigDecimal.ZERO;
        BigDecimal changeDue = BigDecimal.ZERO;
        String userSelection = "";
        int subMenuChoice = 1;

        do {

            switch (subMenuChoice) {
                case 1:
                    userMoney = getUserMoney(userMoney);
                  
                case 2:
                    userSelection = getUserSelection(userMoney);
                  

                    if (userSelection.isEmpty()) {
                        
                        //display submenu
                        view.displayCurrentBalance(userMoney);
                        subMenuChoice = view.displaySubMenu();
                        break;
                    }

                    //if the selection is valid: 
                    Snack snackToDispense = service.getOneSnack(userSelection);
                    
                    //updating quantity
                    
                    updateQty(snackToDispense);
                    
                    
                    //change due is calculated
                    changeDue = userMoney.subtract(snackToDispense.getPrice());
                    
                    view.displaySelectionAndChangeDue(snackToDispense, changeDue);
                    view.displayUpdatedQuantity(snackToDispense);

                    return changeDue;

                default:
                    unknownCommand();
            }

        } while (subMenuChoice != 3);

        //if the user does not buy anything and decides to exit, they get 
        //  all the money back
        
        changeDue = userMoney;
        view.displayNoPurchaseMessage();
        return changeDue;

    }

    private BigDecimal getUserMoney(BigDecimal userMoney) {

        int enterMoreMoney;
        BigDecimal moneyToAdd;
        BigDecimal newMoney;

        do {
            moneyToAdd = view.promptForMoney();
            newMoney = userMoney.add(moneyToAdd);
            userMoney = newMoney;
            view.displayCurrentBalance(userMoney);
            enterMoreMoney = view.enterMoreMoney();

        } while (enterMoreMoney == 1);

        return userMoney;
    }

    private String getUserSelection(BigDecimal userMoney)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {

        String selection = "";

        selection = view.promptForSelection();
        try {
            service.buyOneSnack(selection, userMoney);

        } catch (VendingMachineInvalidSelectionException e) {
            selection = "";
         //  view.displayErrorMessage(e.getMessage());
        } catch (NotSufficientAmountOfMoneyException ex) {
            Logger.getLogger(VendingMachineController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return selection;

    }

    private void unknownCommand() {
        view.displayUnknownCommand();
    }

    private void exitMessage() {
        view.displayExitMessage();
    }

   
    private void getChange(BigDecimal changeDue) {

        Map<Coin, BigDecimal> listOfChange = service.makeChange(changeDue);
        
        view.displayChange(listOfChange);

    }

    private void updateQty(Snack snackToDispense) 
            throws SnackInventoryPersistenceException {
        service.updateSnack(snackToDispense.getSnackId());
 
    
}
}

    

