/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author ctrop
 */
public interface UserIO {

    void print(String msg);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);
    
    String readOptionalState(String prompt);

    BigDecimal readBigDecimal(String prompt);

    BigDecimal readOptionalBigDecimal(String prompt);

    LocalDate readLocalDate(String prompt);
    
    String readState(String prompt);
    
    String readProduct(String prompt);
    
    String readOptionalProduct(String prompt);

    String readString(String prompt);
    
}
