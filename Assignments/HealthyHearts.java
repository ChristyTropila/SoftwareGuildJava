/*
Assignment Resting Hearts
Written by Christy Tropila
Date November 17, 2018
 */
package lessonthreescanner;

import java.util.Scanner;

/**
 *
 * @author ctrop
 */
public class HealthyHearts {

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);

        int age;
        String stringAge;
        int heartRate;
        int restingHeart = 60;

        System.out.println("What is your age?");
        stringAge = userInput.nextLine();
        age = Integer.parseInt(stringAge);//converting the user input to an integer to use for math calculations

        //to calculate your maximum heart rate subtract your age from 220
        heartRate = 220 - age;
        
        //using the Math.rint method to round up to the nearest whole number for calculated heart range.
        int eightyFivePercent=(int) Math.rint(heartRate*.85);
        int fiftyPercent=(int) Math.rint(heartRate*.5);

        System.out.println("Your maximum heart rate should be " + heartRate + "beats per minute");
        System.out.println("Your target heart rate is between " + fiftyPercent + " and " + eightyFivePercent +" beats per minute");
       
        
    }
}
