/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ctrop
 */
public class OrdersProdDaoFileImplTest {
    
   
    OrdersDao dao = new OrdersProdDaoFileImpl();
    private Order firstTest;
    private Order secondTest;
    private List<Order> orderList = new ArrayList<>();
    LocalDate date = LocalDate.of(2019, Month.FEBRUARY, 26);

    @Before
    public void setUp() throws Exception {

        List<Order> orders = dao.searchOrders(date);

        for (Order order : orders) {
            dao.removeOrder(date, order.getOrderNumber());
        }
    }

    //created 2 different mock orders to test the add method
    @Test
    public void testAddOrder() throws FlooringMasteryPersistenceException{
        firstTest = order1();
        secondTest = order2();

        assertEquals(firstTest, dao.getOrder(orderList, 1));

        assertEquals(secondTest, dao.getOrder(orderList, 2));

    }

    //test the search orders method by date
    @Test
    public void testSearchOrders() throws FlooringMasteryPersistenceException{
        order1();
        order2();

        List<Order> orders = dao.searchOrders(date);
        assertEquals(2, orders.size());
    }

    @Test
    public void testRemoveOrder() throws FlooringMasteryPersistenceException{
        
        order1();
        order2();
        
        dao.removeOrder(date, 1);
        assertEquals(1, dao.searchOrders(date).size());

        dao.removeOrder(date, 2);
        assertEquals(0, dao.searchOrders(date).size());


    }

    //test edit order method in the dao
    @Test
    public void testEditOrder() throws FlooringMasteryPersistenceException{

        Order order = order1();
        order.setCustomerName("testName");

        dao.editOrder(date);
        assertEquals("testName", order.getCustomerName());

    }

    public Order order1() {

        Order current = new Order();

        BigDecimal rate = new BigDecimal(0.06);
        BigDecimal area = new BigDecimal(100);
        BigDecimal costSQ = new BigDecimal(10.25);
        BigDecimal costLabSQ = new BigDecimal(6.75);
        BigDecimal material = new BigDecimal(140.00);
        BigDecimal labor = new BigDecimal(118.75);
        BigDecimal tax = new BigDecimal(14.85);
        BigDecimal total = new BigDecimal(262.35);

        current.setOrderNumber(0);
        current.setCustomerName("Christy");
        current.setState("PA");
        current.setTaxRate(rate);
        current.setProductType("Tile");
        current.setArea(area);
        current.setCostPerSqFt(costSQ);
        current.setLaborCostPerSqFt(costLabSQ);
        current.setMaterialCost(material);
        current.setLaborCost(labor);
        current.setTotalTax(tax);
        current.setTotalCost(total);
        current.setTimeStamp(date);

        dao.addOrder(current);
        orderList.add(current);
        return current;
    }

    public Order order2() {

        Order secondCurrent = new Order();

        BigDecimal rate = new BigDecimal(0.0675);
        BigDecimal area = new BigDecimal(25);
        BigDecimal costSQ = new BigDecimal(2.25);
        BigDecimal costLabSQ = new BigDecimal(2.10);
        BigDecimal material = new BigDecimal(56.25);
        BigDecimal labor = new BigDecimal(52.50);
        BigDecimal tax = new BigDecimal(7.34);
        BigDecimal total = new BigDecimal(126.09);

       secondCurrent.setOrderNumber(1);
        secondCurrent.setCustomerName("Joby");
        secondCurrent.setState("PA");
        secondCurrent.setTaxRate(rate);
        secondCurrent.setProductType("Carpet");
        secondCurrent.setArea(area);
        secondCurrent.setCostPerSqFt(costSQ);
        secondCurrent.setLaborCostPerSqFt(costLabSQ);
        secondCurrent.setMaterialCost(material);
        secondCurrent.setLaborCost(labor);
       secondCurrent.setTotalTax(tax);
        secondCurrent.setTotalCost(total);
        secondCurrent.setTimeStamp(date);

        dao.addOrder(secondCurrent);
        orderList.add(secondCurrent);
        return secondCurrent;
    }
}
