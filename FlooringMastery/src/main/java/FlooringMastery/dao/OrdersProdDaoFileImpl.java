/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Order;
import FlooringMastery.dto.Products;
import FlooringMastery.dto.Taxes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author ctrop
 */
public class OrdersProdDaoFileImpl implements OrdersDao {
   private boolean isTrain=false;
   private final String SYSTEM_FILE= "Mode.txt";
    public static final String DELIMITER = "::";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final String header = ("OrderNumber,CustomerName,State,TaxRate,"
            + "ProductType,Area,CostPerSquareFoot,LaborCost,PerSquareFoot,MaterialCost,LaborCost,Tax,Total");

    private List<Order> orders = new ArrayList<>();

    @Override
    public Order addOrder(Order order) {
        int num = (order.getOrderNumber() + 1); // So initial Order starts at 1 and not 0
        order.setOrderNumber(num);
        try {
//            readOrder(order.getTimeStamp());
            for (Order id : orders) {
                if (id.getOrderNumber() == order.getOrderNumber()) {
                    order.setOrderNumber(id.getOrderNumber() + 1);
                }
            }
            LocalDate date = order.getTimeStamp();
            orders.add(order);
            writeOrder(date, orders);
        } catch (Exception ex) {
            System.out.println("Couldnt Write Order " + ex);
        }
        return order;
    }

    @Override
    public Order getOrder(List<Order> orderList, int orderNumber) {
        Order updatedOrder = new Order();
        for (Order order : orderList) {
            if (orderNumber == order.getOrderNumber()) {
                updatedOrder = order;
            }
        }
        return updatedOrder;
    }

    @Override
    public List<Order> searchOrders(LocalDate date) {
        try {
            readOrder(date);
        } catch (Exception ex) {
            System.out.println("Could Not Find Orders");
        }
        return new ArrayList<>(orders);
    }

    @Override
    public void removeOrder(LocalDate date, int orderNumber) {

        try {
            List<Order> currentOrder = readOrder(date);
            for (Order order : currentOrder) {
                if (orderNumber == order.getOrderNumber()) {
                    currentOrder.remove(order);
                    writeOrder(date, currentOrder);
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Order Not Removed");
            ex.printStackTrace();
        }
    }

    @Override
    public void editOrder(LocalDate date) {

        try {
            writeOrder(date, orders);
        } catch (Exception ex) {
            System.out.println("Order Not Updated!");
        }
    }

    private List<Order> readOrder(LocalDate date) throws Exception {

        String ITEM_FILE = "order_" + date;

        Scanner scanner;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)));
        } catch (FileNotFoundException e) {
            throw new Exception(
                    "-_- Could not Load Orders.", e);
        }
        String currentLine;

        String[] currentTokens;

        orders.clear();

        if (orders.isEmpty()) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {

                currentLine = scanner.nextLine();

                currentTokens = currentLine.split(DELIMITER);

                if (currentTokens.length == 13) {

                    Order currentOrder = new Order();

                    int id = Integer.parseInt(currentTokens[0]);
                    BigDecimal rate = new BigDecimal(currentTokens[3]);
                    BigDecimal area = new BigDecimal(currentTokens[5]);
                    BigDecimal costSQ = new BigDecimal(currentTokens[6]);
                    BigDecimal costLabSQ = new BigDecimal(currentTokens[7]);
                    BigDecimal material = new BigDecimal(currentTokens[8]);
                    BigDecimal labor = new BigDecimal(currentTokens[9]);
                    BigDecimal tax = new BigDecimal(currentTokens[10]);
                    BigDecimal total = new BigDecimal(currentTokens[11]);
                    date = LocalDate.parse(currentTokens[12], formatter);

                    currentOrder.setOrderNumber(id);
                    currentOrder.setCustomerName(currentTokens[1]);
                    currentOrder.setState(currentTokens[2]);
                    currentOrder.setTaxRate(rate);
                    currentOrder.setProductType(currentTokens[4]);
                    currentOrder.setArea(area);
                    currentOrder.setCostPerSqFt(costSQ);
                    currentOrder.setLaborCostPerSqFt(costLabSQ);
                    currentOrder.setMaterialCost(material);
                    currentOrder.setLaborCost(labor);
                    currentOrder.setTotalTax(tax);
                    currentOrder.setTotalCost(total);
                    currentOrder.setTimeStamp(date);

                    orders.add(currentOrder);
                }
            }
        }
        scanner.close();
        return orders;
    }

    private void writeOrder(LocalDate date, List<Order> completedOrders)throws FlooringMasteryPersistenceException {

        String ITEM_FILE = "order_" + date;
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save inventory data.", e);
        }

        out.print(header + "\n");

        for (Order currentOrder : completedOrders) {

            out.println(+currentOrder.getOrderNumber() + DELIMITER
                    + currentOrder.getCustomerName() + DELIMITER
                    + currentOrder.getState() + DELIMITER
                    + currentOrder.getTaxRate() + DELIMITER
                    + currentOrder.getProductType() + DELIMITER
                    + currentOrder.getArea() + DELIMITER
                    + currentOrder.getCostPerSqFt() + DELIMITER
                    + currentOrder.getLaborCostPerSqFt() + DELIMITER
                    + currentOrder.getMaterialCost() + DELIMITER
                    + currentOrder.getLaborCost() + DELIMITER
                    + currentOrder.getTotalTax() + DELIMITER
                    + currentOrder.getTotalCost() + DELIMITER
                    + currentOrder.getTimeStamp().format(formatter));

            out.flush();
        }

        out.close();
    }

    @Override
    public void saveCurrentChanges() throws FlooringMasteryPersistenceException{
     if(isTrain){
         throw new FlooringMasteryPersistenceException (
            "TRAINING MODE. YOU CANNOT SAVE" );
     }else{
       this.writeOrder(LocalDate.now(), orders);
         
     }
     }
    

    
    
    
    @Override
    public String getSystemState() throws FlooringMasteryPersistenceException {
       Scanner myScan;
   try{
       myScan= new Scanner(new BufferedReader(new FileReader(SYSTEM_FILE)));
   }catch(FileNotFoundException e){
       throw new FlooringMasteryPersistenceException("Could not load mode status");
       
   }
   String currentLine;
   String mode=null;
   
   while(myScan.hasNextLine()){
       currentLine=myScan.nextLine();
       mode=currentLine;
       
   }
   return mode;
   }
       
       

}


    
    
    
      
   

 

  
    


   


  
    
    
