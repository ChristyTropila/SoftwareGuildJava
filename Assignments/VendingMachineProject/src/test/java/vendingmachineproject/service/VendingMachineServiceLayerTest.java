/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vendingmachineproject.dao.SnackInventoryPersistenceException;
import vendingmachineproject.dao.VendingMachineAuditDao;
import vendingmachineproject.dao.VendingMachineAuditDaoFileImpl;
import vendingmachineproject.dao.VendingMachineAuditStubImpl;
import vendingmachineproject.dao.VendingMachineDao;
import vendingmachineproject.dao.VendingMachineDaoFileImpl;
import vendingmachineproject.dao.VendingMachineDaoStubImpl;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public class VendingMachineServiceLayerTest {
    
    private VendingMachineServiceLayer service;
    
    List<Snack> snackList;

    
    
    public VendingMachineServiceLayerTest() throws Exception {
        
      VendingMachineDao dao= new VendingMachineDaoStubImpl();
      VendingMachineAuditDao audit= new VendingMachineAuditStubImpl();
      service= new VendingMachineServiceLayerImpl(dao, audit);
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllSnacks method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetAllSnacks() throws Exception {
            //create snack
      Snack test = new Snack("T1");
        test.setName("test one");
        test.setPrice(new BigDecimal("2.50"));
        test.setQuantity(5);
      
      //add snack to inventory
      service.addSnack(test.getSnackId(), test);
      
      //size should be 10
      assertEquals(2, service.getAllSnacks().size());
      
      //remove test snack from inventory
      
      service.removeSnack(test.getSnackId());
     
    }

    /**
     * Test of getOneSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testBuyOneSnack_String_BigDecimal() throws Exception {
        Snack test = new Snack("T1");
        test.setName("test one");
        test.setPrice(new BigDecimal("2.50"));
        test.setQuantity(5);
        
        int startingQuantity= test.getQuantity();
        service.addSnack(test.getSnackId(), test);
        
        service.buyOneSnack("T1", new BigDecimal("2.50"));
        
        assertNotEquals(startingQuantity, service.getOneSnack("T1").getQuantity());
        
        service.removeSnack(test.getSnackId());
       
    }

    /**
     * Test of getOneSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetOneSnack_String() throws Exception {
             //create a test to see if an added snack matches the snack ID
        
        Snack test= new Snack("T1");
        test.setName("test one");
        test.setPrice(new BigDecimal("2.50"));
        test.setQuantity(5);
        
        service.addSnack(test.getSnackId(), test);
        
        Snack serviceSnack = service.getOneSnack("T1");
        
        assertEquals(test, serviceSnack);
        
        service.removeSnack(test.getSnackId());
        

    }

    /**
     * Test of updateSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testUpdateSnack() throws Exception {
   
    }

    /**
     * Test of makeChange method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testMakeChange() {
          //creating a change due variable and then store the results into a map
        
        BigDecimal change= new BigDecimal(".85");
        
        Map<Coin, BigDecimal> changeReturned = new HashMap<>();
        
        changeReturned= service.makeChange(change);
        
        BigDecimal changeReturnedBigDecimal= BigDecimal.ZERO;
        
        BigDecimal quarter = new BigDecimal(".25");
        BigDecimal nickle = new BigDecimal(".05");
        BigDecimal dime= new BigDecimal(".10");
        BigDecimal penny = new BigDecimal(".01");
        
        BigDecimal numberOfQuarters;
        BigDecimal numberOfDimes;
        BigDecimal numberOfNickles;
        BigDecimal numberOfPennies;
        
        BigDecimal valueOfQuarters;
        BigDecimal valueOfDimes;
        BigDecimal valueOfNickles;
        BigDecimal valueOfPennies;
        
        //calculating the number of quarters returned
        numberOfQuarters= change.divide(quarter);
        valueOfQuarters= numberOfQuarters.setScale(0, RoundingMode.DOWN).multiply(quarter);
        
        //change remaining after quarters are subtracted
         BigDecimal afterQuarters= change.subtract(valueOfQuarters);
         
         //add value of quarters returned
         changeReturnedBigDecimal= changeReturnedBigDecimal.add(valueOfQuarters);
                 
                 
         //doing the same thing now for the dimes
         
         numberOfDimes= afterQuarters.divide(dime);
         valueOfDimes= numberOfDimes.setScale(0, RoundingMode.DOWN).multiply(dime);
         
         BigDecimal afterDimes= afterQuarters.subtract(valueOfDimes);
         changeReturnedBigDecimal= changeReturnedBigDecimal.add(valueOfDimes);
     
         //doing same for the nickles
         
        numberOfNickles= afterDimes.divide(nickle);
        valueOfNickles= numberOfNickles.setScale(0, RoundingMode.DOWN).multiply(nickle);
        
        BigDecimal afterNickles= afterDimes.subtract(valueOfNickles);
        changeReturnedBigDecimal= changeReturnedBigDecimal.add(valueOfNickles);
        
        
        //pennies
        
        numberOfPennies= afterNickles.divide(penny);
        valueOfPennies=numberOfPennies.setScale(0, RoundingMode.DOWN).multiply(penny);
        
        BigDecimal afterPennies= afterNickles.subtract(valueOfPennies);
        changeReturnedBigDecimal= changeReturnedBigDecimal.add(valueOfPennies);
        
        
         
         
    
    
    }

    
}
