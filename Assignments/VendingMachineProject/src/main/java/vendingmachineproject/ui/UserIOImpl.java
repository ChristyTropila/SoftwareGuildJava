/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author ctrop
 */
public class UserIOImpl implements UserIO{
    
    Scanner console= new Scanner(System.in);
    
  @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (asking for an int)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
             
                System.out.println("That's not a valid int.");
            }
        }
        return num;
    }

   
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                
            }
        }
    }

    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

 
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
              
            }
        }
    }

   
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
           
            }
        }
    }

   
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public Date readDate(String msgPrompt) {
        Date date = new Date();
        return date;

    }

   
    @Override
    public BigDecimal readBigDecimal(String msgPrompt) {
        while (true) {
            try {
                return new BigDecimal(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid amount.");
            }
        }
    }

    @Override
    public BigDecimal readBigDecimal(String msgPrompt, BigDecimal minVal, BigDecimal maxVal) {
        BigDecimal result;
        boolean invalid = true; 
        do {
            result = readBigDecimal(msgPrompt);
            if (result.compareTo(minVal) < 0) {
                System.out.println("You must enter at least $" + minVal);
            } else if (result.compareTo(maxVal) > 0) {
                System.out.println("You cannot enter more than $" + maxVal);
            } else {
                invalid = false; 
            }
        } while (invalid);
        return result;
    }



}
