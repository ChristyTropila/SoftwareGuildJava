/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    Snack testSnack1;
    Snack testSnack2;
    List<Snack> testSnackList = new ArrayList<>();
    Map<String, Snack> snackInventory = new HashMap<>();

    public VendingMachineDaoStubImpl() {
        testSnack1 = new Snack("T1");
        testSnack1.setName("test one");
        testSnack1.setPrice(new BigDecimal("2.50"));
        testSnack1.setQuantity(5);

        testSnack2 = new Snack("T2");
        testSnack2.setName("test two");
        testSnack2.setPrice(new BigDecimal("2.50"));
        testSnack2.setQuantity(0);

          snackInventory.put("T1", testSnack1);
         snackInventory.put("T2", testSnack2);
        testSnackList.add(testSnack1);
        testSnackList.add(testSnack2);
    }

    @Override
    public List<Snack> getAllSnacks() throws SnackInventoryPersistenceException {
        return testSnackList;
    }

    @Override
    public Snack getOneSnack(String snackId) throws SnackInventoryPersistenceException {
        if (snackId.equals(testSnack1.getSnackId())) {
            return testSnack1;
        } else {
            return null;
        }
    }

    @Override
    public void updateSnack(String snackId) throws SnackInventoryPersistenceException {
        Snack snackToUpdate = snackInventory.get(snackId);
        int currentQty = snackToUpdate.getQuantity();
        snackToUpdate.setQuantity(currentQty - 1);
        snackInventory.put(snackId, snackToUpdate);

    }

    @Override
    public Snack removeSnack(String snackId) throws SnackInventoryPersistenceException {
        Snack removedSnack = snackInventory.remove(snackId);

        return removedSnack;
    }

    @Override
    public Snack addSnack(String snackId, Snack snack) throws SnackInventoryPersistenceException {
           Snack newSnack = snackInventory.put(snackId, snack);
    
        return newSnack;
    }

}
