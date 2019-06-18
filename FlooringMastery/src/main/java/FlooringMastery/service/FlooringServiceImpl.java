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
import FlooringMastery.dao.TaxesDao;
import FlooringMastery.dto.Order;
import FlooringMastery.dto.Products;
import FlooringMastery.dto.Taxes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static java.math.RoundingMode.HALF_UP;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author ctrop
 */
public class FlooringServiceImpl implements FlooringService {

    
    private OrdersDao orderDao;
    private ProductsDao productDao;
    private TaxesDao taxDao;

    public FlooringServiceImpl(OrdersDao orderDao, ProductsDao productDao,
            TaxesDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public Order addOrder(Order order) throws ProductValidationException, StateTaxValidationException{
        if (order.getCustomerName().equals("")) {
            throw new ProductValidationException("You must Enter a Customer Name!");
        } else {
            orderDao.addOrder(order);
        }
        return order;
    }

    @Override
    public void removeOrder(LocalDate date, int orderNumber) throws ProductValidationException, StateTaxValidationException {
        orderDao.removeOrder(date, orderNumber);
    }

    @Override
    public List<Order> searchOrders(LocalDate date) {
        return orderDao.searchOrders(date);
    }

    @Override
    public Order getOrder(List<Order> orderList, int orderNumber) {
        return orderDao.getOrder(orderList, orderNumber);
    }

    @Override
    public void editOrder(LocalDate date, Order order) throws ProductValidationException, StateTaxValidationException {
        if (order.getCustomerName().equals("")) {
            throw new ProductValidationException("You must Enter a Customer Name!");
        } else {
            orderDao.editOrder(date);
        }
    }

    @Override
    public Order calculateCost(Order order) throws FlooringMasteryPersistenceException{

        BigDecimal taxRate = new BigDecimal(0);
        BigDecimal area = order.getArea();
        List<Taxes> taxes = taxDao.loadTaxRates();
        List<Products> products = productDao.loadProductList();

        if (area.compareTo(BigDecimal.ZERO) >= 0) {

            for (Taxes t : taxes) {
                if (t.getState().equals(order.getState())) {
                    taxRate = t.getTaxRate();
                }
            }

            for (Products p : products) {

                if (p.getProductType().equals(order.getProductType())) {

                    BigDecimal costSqFt = (p.getCostPerSqFt());
                    BigDecimal laborCostSqFt = (p.getLaborCostPerSqFt());

                    BigDecimal material = area.multiply(costSqFt);
                    BigDecimal materialCost = material.setScale(2, HALF_UP);

                    BigDecimal labor = area.multiply(laborCostSqFt);
                    BigDecimal laborCost = labor.setScale(2, HALF_UP);

                    BigDecimal subTotal = materialCost.add(laborCost);

                    BigDecimal tax = subTotal.multiply(taxRate);
                    BigDecimal taxCost = tax.setScale(2, HALF_UP);

                    BigDecimal totalCost = subTotal.add(taxCost);

                    order.setTaxRate(taxRate);
                    order.setCostPerSqFt(costSqFt);
                    order.setLaborCostPerSqFt(laborCostSqFt);
                    order.setMaterialCost(materialCost);
                    order.setLaborCost(laborCost);
                    order.setTotalTax(taxCost);
                    order.setTotalCost(totalCost);
                }
            }
        } else {
            throw new FlooringMasteryPersistenceException("Area Must be Larger than Zero!");
        }

        return order;
    }


    @Override
    public void saveCurrentChanges() throws FlooringMasteryPersistenceException{
      orderDao.saveCurrentChanges();
    }

    @Override
    public String getSystemState() throws FlooringMasteryPersistenceException {
    try{
        return orderDao.getSystemState();
    }catch(FlooringMasteryPersistenceException e){
        throw new FlooringMasteryPersistenceException(e.getMessage());
    }
    }

}

