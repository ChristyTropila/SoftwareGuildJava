/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Taxes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author ctrop
 */
public class TaxesDaoFileImpl implements TaxesDao{

    private final String ITEM_FILE = "Taxes.txt";
    private final String DELIMITER = "::";

    List<Taxes> taxes = new ArrayList<>();

    @Override
    public List<Taxes> loadTaxRates() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not Find Products.", e);
        }
        String currentLine;

        String[] currentTokens;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentTokens = currentLine.split(DELIMITER);

            if (currentTokens.length
                    == 2) {
                Taxes currentTax = new Taxes();

                BigDecimal cost = new BigDecimal(currentTokens[1]);

                currentTax.setState(currentTokens[0]);
                currentTax.setTaxRate(cost);

                taxes.add(currentTax);
            }

        }

        scanner.close();
        return taxes;
    }
        
    }
    

