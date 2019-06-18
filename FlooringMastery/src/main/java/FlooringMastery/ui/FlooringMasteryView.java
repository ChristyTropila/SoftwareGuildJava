/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.ui;

import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Products;
import FlooringMastery.dto.Taxes;
import FlooringMastery.service.FlooringService;
import java.lang.Thread.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author ctrop
 */
public class FlooringMasteryView {

    private UserIO io;

    private final String stateNames = " OH, PA, MI, IN";
    private final String types = "Carpet, Laminate, Tile, Wood";

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int getSelection() {
        io.print("WELCOME TO THE FLOORING STORE");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    
    //Method to get the date entered in by the user
    public LocalDate getDate() {
        LocalDate dateIn = io.readLocalDate("Please Enter Order Date (MM/DD/YYYY)");
        return dateIn;
    }

    //this will get the order number when the user chooses to edit and order
    public int getOrderNum() {
        int orderNumber = io.readInt("Please Enter your Order Number");
        return orderNumber;
    }

    //method to confirm order
    public String acceptOrder() {
        String acceptOrder = io.readString("Confirm Order, Y/N ??");
        return acceptOrder;
    }
    
    
    //Method to get data from user for the add an order option
   public Order getOrderData() {

        String customerName = io.readString("Please Enter Customer Name");
        String state = io.readState("Please enter the State of Where the Sale is Occuring " + stateNames);
        String productType = io.readProduct("Please enter the Product Type " + types);
        BigDecimal area = io.readBigDecimal("Please enter the Area in SqFeet that you want to Cover");
        LocalDate time = LocalDate.now();

        Order currentOrder = new Order();

        currentOrder.setOrderNumber(currentOrder.getOrderNumber());
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);
        currentOrder.setTimeStamp(time);
        return currentOrder;
    }

    

    //this is the order summary after the user enters in each piece of info
    public void orderSummary(Order order) {
        System.out.println("Material Cost Per Square Foot: $" + order.getCostPerSqFt());
        System.out.println("Labor Cost Per Sqaure Foot $" + order.getLaborCostPerSqFt());
        System.out.println("Total Material Cost $" + order.getMaterialCost());
        System.out.println("Total Labor Cost $" + order.getLaborCost());
        System.out.println("Total Taxes $" + order.getTotalTax());
        System.out.println("Total Cost $" + order.getTotalCost());
        System.out.println("Order Date: " + order.getTimeStamp());
    }

    public Order editOrder(Order order) {

        BigDecimal zero = BigDecimal.ZERO;

        System.out.println("Leave Blank if no Changes are Needed");
        String customerName = io.readString("Change Customer Name To? ");
        String state = io.readOptionalState("Change Location To? " + stateNames);
        String productType = io.readOptionalProduct("Change Product Type To? " + types);
        BigDecimal area = io.readOptionalBigDecimal("Change Area of Project?");

        //if the user doesn't enter in anything new, dont change it. If they do, change it
        if (customerName.equals("")) {
            customerName = order.getCustomerName();
        } else {
            order.setCustomerName(customerName);
        }
        if (state.equals("")) {
            state = order.getState();
        } else {
            order.setState(state);
        }
        if (productType.equals("")) {
            productType = order.getCustomerName();
        } else {
            order.setProductType(productType);
        }
        if (area.compareTo(zero) == 0) {
            area = order.getArea();
        } else {
            order.setArea(area);
        }

        return order;
    }

    //method that will display a list of orders based on date entered
    public void displayByDate(LocalDate date, List<Order> orders) {
        for (Order order : orders) {
            if (date.equals(order.getTimeStamp())) {
                io.print(order.getOrderNumber() + ": "
                        + order.getCustomerName() + " : "
                        + order.getState() + " : "
                        + order.getProductType() + " $"
                        + order.getTotalCost() + " on date "
                        + order.getTimeStamp());
            } else {
                System.out.println("No Order Found");
            }
        }
    }
    
    
    
//this will ask user if they wish to save their work
    public boolean promptUserToSave() {
        io.print(" ");
        io.print("Would you like to save your current work?");

        while (true) {
            String userInput = io.readString("Enter Y/N ");
            switch (userInput.toLowerCase()) {
                case "y":
                    return true;
                case " n":
                    return false;
                default:
                    io.print("Invalid Entry");
                    break;
            }
        }
    }
    
    
    //BANNERS 
    
    
    public void saveSuccessBanner(){
        io.print("All changes were saved!");
    }
    
    public void trainingBanner(){
        io.print("SYSTEM IS IN TRAINING MODE.");
    }

    public void prodBanner(){
        io.print("SYSTEM IS IN PRODUCTION MODE.");
    }
    public void orderSuccesfullBanner() {
        System.out.println("Your Order Has Been Successfully Stored In The System.");
        System.out.println("\n");
    }

    public void SearchBanner() {
        System.out.println("===SEARCH BY DATE===");
    }

    public void editBanner() {
        System.out.println("===ORDER EDIT===");
    }

    public void createOrderBanner() {
        System.out.println("===CREATE AN ORDER===");
    }

    public void removeBanner() {
        System.out.println("===REMOVE ORDER===");
    }

    public void removeSucessBanner() {
        System.out.println("Your Order Has Been Successfully Removed From the System.");
    }

    public void editSuccessBanner() {
        System.out.println("Your Order Has Been Successfully Updated.");
    }

    public void orderNotSavedBanner() {
        System.out.println("Order not Saved, Returning To Main Menu!");
      
    }
    
    public void displayError(){
        System.out.println("ERROR");
    }
}
