/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.dao;

import java.math.BigDecimal;
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
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public class VendingMachineDaoTest {
    
    
    public VendingMachineDao dao= new VendingMachineDaoStubImpl();
    List<Snack> snackList;
    
    public VendingMachineDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SnackInventoryPersistenceException {
     snackList= dao.getAllSnacks();
     
     List<Snack> setUpSnackInventory=snackList;
     
     for(Snack snack: setUpSnackInventory){
        dao.removeSnack(snack.getSnackId());
    }
    }
    
    @After
    public void tearDown()throws SnackInventoryPersistenceException {
        List<Snack> tearDownSnackInventory= snackList;
        
        for(Snack snack: tearDownSnackInventory){
            dao.addSnack(snack.getSnackId(), snack);
        }
    }

    /**
     * Test of getAllSnacks method, of class VendingMachineDao.
     */
    @Test
    public void testGetAllSnacks() throws Exception {
        
        //create snack
      Snack test= new Snack("A9");
      test.setName("Lollipop");
      test.setPrice(new BigDecimal(".76"));
      test.setQuantity(10);
      
      //add snack to inventory
      dao.addSnack(test.getSnackId(), test);
      
      //size should be 10
      assertEquals(2, dao.getAllSnacks().size());
      
      //remove test snack from inventory
      
      dao.removeSnack(test.getSnackId());
      
      
    }

    /**
     * Test of getOneSnack method, of class VendingMachineDao.
     */
    @Test
    public void testGetOneSnack() throws Exception {
 
        //create a test to see if an added snack matches the snack ID
        
        Snack test= new Snack("T1");
        test.setName("test one");
        test.setPrice(new BigDecimal("2.50"));
        test.setQuantity(5);
        
        dao.addSnack(test.getSnackId(), test);
        
        Snack daoSnack = dao.getOneSnack("T1");
        
        assertEquals(test, daoSnack);
        
        dao.removeSnack(test.getSnackId());
        
        
                
                
                }  
    /**
     * Test of updateSnack method, of class VendingMachineDao.
     */
    @Test
    public void testUpdateSnack() throws Exception {
        
        //test to see if the quantity of a snack updates when the snack quantity is updated
        Snack test = new Snack("B4");
        test.setName("Chocolate");
        test.setPrice(new BigDecimal(".99"));
        test.setQuantity(7);
        
        dao.addSnack(test.getSnackId(), test);
        
        int quantityBefore = test.getQuantity();
        dao.updateSnack(test.getSnackId());
        
        int quantityAfter= test.getQuantity();
        assertEquals(quantityBefore, quantityAfter + 1);
        dao.removeSnack(test.getSnackId());
        
        
        
   
    }

    /**
     * Test of makeChange method, of class VendingMachineDao.
     */
 /*   @Test
    public void testMakeChange() {
        
        //creating a change due variable and then store the results into a map
        
        BigDecimal change= new BigDecimal(".85");
        
        Map<Coin, BigDecimal> changeReturned = new HashMap<>();
        
        changeReturned= dao.makeChange(change);
        
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
         
         number
         
    }

  */  

}
