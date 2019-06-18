/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeerlibrary.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ctrop
 */
public class UserIOConsoleImpl implements UserIO{

    //here we implement all the methods contain in the UserIO
    
    Scanner myScan= new Scanner(System.in);

    @Override
    public void print(String message) {

        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        while (true) {
            try {
                double input = myScan.nextDouble();
                myScan.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Sorry input is not a valid number " + prompt);
                myScan.nextLine();
            }
        }
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        String newPrompt = prompt;
        double input = 0.0;
        while (true) {
            input = readDouble(newPrompt);
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.println("NUmber is not within parameters " + prompt + "between " + min + " and " + max);

            }
        }

    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        while (true) {
            try {
                float input = myScan.nextFloat();
                myScan.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Sorry, input is not a valid number " + prompt);
                myScan.nextLine();
            }
        }
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        String newPrompt = prompt + "Between " + min + " and " + max + " .";
        float input = 0.0f;
        while (true) {
            input = readFloat(newPrompt);
            myScan.nextLine();
            if (input >= min && input <= max) {
                return input;

            } else {
                System.out.println("Number is not within parameters ");

            }

        }
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        while (true) {
            try {
                int input = myScan.nextInt();
                myScan.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Sorry, in put is not a valid number " + prompt);
                myScan.nextLine();
            }
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        String newPrompt = prompt;
        int input = 0;
        while (true) {
            input = readInt(newPrompt);
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.println("number is not within parameters Please select a number between " + min + " and " + max);

            }
        }

    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        while (true) {
            try {
                long input = myScan.nextLong();
                myScan.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Sorry, input is not a valid number " + prompt);
                myScan.nextLine();
            }
        }
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        String newPrompt = prompt + "between " + min + " and " + max;
        long input = 0;
        while (true) {
            input = readLong(newPrompt);
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.println("Number is not within parameters");
            }

        }
    }

    @Override
    public String readString(String prompt) {
     print(prompt);
     String input= myScan.nextLine();
     return input;
    }
    
}