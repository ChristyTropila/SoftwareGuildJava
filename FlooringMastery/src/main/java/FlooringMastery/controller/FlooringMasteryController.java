/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.controller;

import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dao.OrdersDao;
import FlooringMastery.dao.OrdersProdDaoFileImpl;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Products;
import FlooringMastery.dto.Taxes;
import FlooringMastery.service.FlooringService;
import FlooringMastery.service.InvalidOrderNumberException;
import FlooringMastery.service.ProductValidationException;
import FlooringMastery.service.StateTaxValidationException;
import FlooringMastery.ui.FlooringMasteryView;
import FlooringMastery.ui.UserIO;
import FlooringMastery.ui.UserIOConsoleImpl;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author ctrop
 */
public class FlooringMasteryController {
    
    
    private FlooringMasteryView view;
    private FlooringService service;
    private String mode=null;

    public FlooringMasteryController(FlooringMasteryView view, FlooringService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
       
        boolean keepGoing = true;
        boolean saveChanges=false;
        int menuSelection = 0;
     try{
      mode=service.getSystemState();
     }catch(FlooringMasteryPersistenceException e){
         view.displayError();
         
     }
        
         try {
 
            while (keepGoing) {
               if(mode.equalsIgnoreCase("production")){
                   view.prodBanner();
               }else if(mode.equalsIgnoreCase("training")){
                   view.trainingBanner();
               }

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        searchOrders();
                        break;
                    case 2:
                        if(mode.equalsIgnoreCase("production")){
                            createOrder();
                        }else if(mode.equalsIgnoreCase("training"))
                            view.trainingBanner();
                        break;
                    case 3:
                        if(mode.equalsIgnoreCase("production")){
                        editOrder();
                        }else if(mode.equalsIgnoreCase("training"))
                            view.trainingBanner();
                        break;
                    case 4:
                        if(mode.equalsIgnoreCase("production")){
                        removeOrder();
                        }else if(mode.equalsIgnoreCase("training"))
                            view.trainingBanner();
                        break;
                    case 5:
                        if(mode.equalsIgnoreCase("production")){
                        saveOrder();
                        }else if(mode.equalsIgnoreCase("training")){
                            view.trainingBanner();
                        }
                        break;
                    case 6:
                       keepGoing=false;
                       break;
                    default:
                        unknownCommand();
                }

            }

            exitMessage();
        } catch (Exception e) {
            System.out.println("ERROR" + e);
        }
    }

    private int getMenuSelection() throws FlooringMasteryPersistenceException {
        return view.getSelection();
    }
    
    //all methods will now only communicate with service layer and the view

    private void searchOrders() throws ProductValidationException, StateTaxValidationException {
        view.SearchBanner();
        LocalDate date = view.getDate();
        List<Order> orderedDates = service.searchOrders(date);
        view.displayByDate(date, orderedDates);

    }

    private void createOrder() {
        try {
            view.createOrderBanner();
            Order order = view.getOrderData();
            Order completedOrder = service.calculateCost(order);
            view.orderSummary(completedOrder);
            String accepted = view.acceptOrder();
            if (accepted.equals("Y")) {
                service.addOrder(completedOrder);
                view.orderSuccesfullBanner();
            } else {
                view.orderNotSavedBanner();
            }

        } catch (Exception ex) {
            System.out.println("\n");
            System.out.println(ex);
        }
    }

    private void editOrder() {
        try {
            view.editBanner();
            LocalDate date = view.getDate();
            int orderNumber = view.getOrderNum();
            List<Order> orderedDates = service.searchOrders(date);
            Order updated = service.getOrder(orderedDates, orderNumber);
            Order updatedOrder = view.editOrder(updated);
            Order finalOrder = service.calculateCost(updatedOrder);
            view.orderSummary(finalOrder);
            service.editOrder(date, finalOrder);
            view.editSuccessBanner();
        } catch (Exception ex) {
            System.out.println("\n");
            System.out.println(ex);

        }
    }
private void saveOrder()throws FlooringMasteryPersistenceException{
try{
    service.saveCurrentChanges();
    view.saveSuccessBanner();
}catch(FlooringMasteryPersistenceException e){
    view.displayError();
}
     }
 
    


    private void removeOrder() {
        view.removeBanner();
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNum();
        try {
            service.removeOrder(date, orderNumber);
            view.removeSucessBanner();
        } catch (Exception ex) {
            System.out.println("Order Not Removed!");
        }
    }

    private void unknownCommand() {
        System.out.println("Unknown Command! Please Enter a Valid Selection");
    }

    private void exitMessage() {
        System.out.println("Thanks for Visiting! See You Next Time!");
    }
}



