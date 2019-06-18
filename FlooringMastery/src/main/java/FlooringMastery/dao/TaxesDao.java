/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Taxes;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ctrop
 */
public interface TaxesDao {

  List<Taxes> loadTaxRates() throws FlooringMasteryPersistenceException;
    
    
}
