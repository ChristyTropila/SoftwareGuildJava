/*
Assignment DogGenetics
Written By Christy Tropila
Date November 18, 2018
 */
package doggenetics;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author ctrop
 */
public class DogGenetics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner myScan = new Scanner(System.in); //calling the Scanner method
        Random rnd = new Random();//calling the Random method

       //variable that holds user's dog name
       String dogName;


        System.out.println("What is your dogs name?");
        dogName = myScan.nextLine();
        System.out.println("Well then, I have this highly reliable report on " + dogName + "'s prestigious background right here.");

        //initializing an array that holds 5 different percent values
       int percents[] = new int[5];
        for (int i = 0; i < 100; i++) {
            int dogBreed = rnd.nextInt(5); //generates a random number for 5 different dog breeds that will add up to 100
            percents[dogBreed] = percents[dogBreed] + 1; 
          
        }
        //a for loop that will go through the code 5 times for 5 different dog breeds
        for (int i=0; i<5; i++){
          String[] dogGenetics={"St Bernard", "Huskey", "Asian Pug", "Bull Dog", "Chihuahua"};
            System.out.println(percents[i] +"%" + " " + dogGenetics[i]);
        }

        System.out.println("Wow! What a cool dog you have!!!");
        }

    }

