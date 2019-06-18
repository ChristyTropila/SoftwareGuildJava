/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Products;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface ProductsDao {

 List<Products> loadProductList() throws FlooringMasteryPersistenceException; 


    
}
