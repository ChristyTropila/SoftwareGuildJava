/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Products;
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
public class ProductsDaoFileImpl implements ProductsDao {

      private final String ITEM_FILE = "Products.txt";
    private final String DELIMITER = "::";

    public List<Products> loadProductList() throws FlooringMasteryPersistenceException{
        List<Products> products = new ArrayList<>();
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

            if (currentTokens.length == 3) {
                Products currentProduct = new Products();

                BigDecimal cost = new BigDecimal(currentTokens[1]);
                BigDecimal labor = new BigDecimal(currentTokens[2]);

                currentProduct.setProductType(currentTokens[0]);
                currentProduct.setCostPerSqFt(cost);
                currentProduct.setLaborCostPerSqFt(labor);

                products.add(currentProduct);
            }

        }

        scanner.close();
        return products;
    }



}
