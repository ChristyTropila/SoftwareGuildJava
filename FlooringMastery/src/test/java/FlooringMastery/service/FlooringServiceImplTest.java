/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.service;

import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dao.OrdersDao;
import FlooringMastery.dao.OrdersProdDaoFileImpl;
import FlooringMastery.dao.ProductsDao;
import FlooringMastery.dao.ProductsDaoFileImpl;
import FlooringMastery.dao.TaxesDao;
import FlooringMastery.dao.TaxesDaoFileImpl;
import FlooringMastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ctrop
 */
public class FlooringServiceImplTest {

    OrdersDao dao1 = new OrdersProdDaoFileImpl();
    ProductsDao dao2 = new ProductsDaoFileImpl();
    TaxesDao dao3 = new TaxesDaoFileImpl();
    FlooringService service = new FlooringServiceImpl(dao1, dao2, dao3);
    LocalDate date = LocalDate.of(2019, Month.FEBRUARY, 26);

    @Before
    public void setUp() throws FlooringMasteryPersistenceException {

        List<Order> orders = dao1.searchOrders(date);

        for (Order order : orders) {
            dao1.removeOrder(date, order.getOrderNumber());
        }
    }

    @Test
    public void testArea() throws FlooringMasteryPersistenceException {

        List<Order> itemList = dao1.searchOrders(date);
        Order testOne = new Order();
        BigDecimal area = new BigDecimal(-25);

        testOne.setCustomerName("Christy");
        testOne.setState("IN");
        testOne.setProductType("Tile");
        testOne.setArea(area);

        try {
            service.calculateCost(testOne);
            fail("Cannot have a negative area.");
        } catch (FlooringMasteryPersistenceException e) {

        }
    }

    @Test
    public void testName() throws ProductValidationException, StateTaxValidationException{

        List<Order> itemList = dao1.searchOrders(date);
        Order testName = new Order();
        BigDecimal area = new BigDecimal(25);

        testName.setCustomerName("");
        testName.setState("IN");
        testName.setProductType("Wood");
        testName.setArea(area);

        try {
            service.addOrder(testName);
            fail("Name cannot be null");
        } catch (ProductValidationException e) {

        }

    }

    @Test
    public void testProduct() throws ProductValidationException, StateTaxValidationException{
        List<Order> itemList = dao1.searchOrders(date);
        Order testProduct = new Order();
        BigDecimal area = new BigDecimal(25);

        testProduct.setCustomerName("Christy");
        testProduct.setState("PA");
        testProduct.setProductType("fur");
        testProduct.setArea(area);

        try {
            service.addOrder(testProduct);
        } catch (ProductValidationException e) {

        }
    }
}
