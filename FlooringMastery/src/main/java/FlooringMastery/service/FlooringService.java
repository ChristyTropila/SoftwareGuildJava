/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.service;

import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Products;
import FlooringMastery.dto.Taxes;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface FlooringService {

    public Order addOrder(Order order) throws ProductValidationException, StateTaxValidationException;

    public void removeOrder(LocalDate date, int orderNumber) throws ProductValidationException, StateTaxValidationException;

    public List<Order> searchOrders(LocalDate date) throws ProductValidationException, StateTaxValidationException;

    public Order getOrder(List<Order> orderList, int orderNumber)throws ProductValidationException, StateTaxValidationException;

    public void editOrder(LocalDate date, Order order) throws ProductValidationException, StateTaxValidationException;

    public Order calculateCost(Order order) throws FlooringMasteryPersistenceException;

     public String getSystemState() throws FlooringMasteryPersistenceException;

    public void saveCurrentChanges() throws FlooringMasteryPersistenceException;
    
}
