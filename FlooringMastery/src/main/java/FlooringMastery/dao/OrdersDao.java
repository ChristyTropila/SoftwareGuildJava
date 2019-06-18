/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Order;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface OrdersDao {

    Order addOrder(Order order);

    List<Order> searchOrders(LocalDate date);

    void removeOrder(LocalDate date, int orderNumber);

    void editOrder(LocalDate date);

    Order getOrder(List<Order> orderList, int orderNumber);

    void saveCurrentChanges() throws FlooringMasteryPersistenceException;

    String getSystemState() throws FlooringMasteryPersistenceException;
}
