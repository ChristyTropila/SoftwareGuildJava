/*
Assignment Rock Paper Scissors
By Christy Tropila
Date last edited November 18, 2018

 */
package rockpaperscissor;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author ctrop
 */
public class RockPaperScissor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //calling the scanner method
        Scanner myScan = new Scanner(System.in);
        //calling the random method
        Random randomizer = new Random();

        //creating variables for rock, paper, scissor
        int rock = 1;
        int paper = 2;
        int scissors = 3;

        //creating variables to keep count of wins, loses, ties
        int wins = 0;
        int loses = 0;
        int ties = 0;

        //creating variables to hold value of rounds user wises to play
        String rounds;
        int roundsToPlay;

        //creating varible that will hold the players input
        String userPlay;
        int userPlayInt;

        //variable play will ask the user if they want to play again 
        String play;

        System.out.println("Lets Play A Game!!!");
        System.out.println("Rock Paper Scissors Shoot!");

        //I chose to use a do/while loop to execute the game at least once and continue to do so if the user choses to play again     
        do {
            System.out.println("How many rounds do you want to play? 1-10?");
            rounds = myScan.nextLine();
            roundsToPlay = Integer.parseInt(rounds);//the user input by default is entered in as a string so here  I converted the input to an integer

            if (roundsToPlay > 10 || roundsToPlay < 0) { //if the user enters in a number higher than 10 or lower than 0, break out of the loop and end the game

                System.out.println("That is invalid! Good-Bye");
                break;
            }

            for (int i = 0; i < roundsToPlay; i++) {
                int computer = randomizer.nextInt(3) + 1;  //This tells the computer to randomly choose a number 1, 2, or 3
                System.out.println(" Lets play!");
                System.out.println("Rock (1), Paper(2), Scissors(3), shoot!");
                userPlay = myScan.nextLine();
                userPlayInt = Integer.parseInt(userPlay);//converting the user answer to an integer

                if (userPlayInt != 1 && userPlayInt != 2 && userPlayInt != 3) {//if user enters in a number that isn't 1 2 or 3, break out the loop and end game
                    System.out.println("You have entered an invalid choice");
                    break;
                } else if (userPlayInt == computer) {
                    System.out.println("Its a tie!!!");
                    ties++;
                } else if (userPlayInt == 1 && computer == 2) {
                    System.out.println("Rock Loses to Paper!");
                    loses++;
                } else if (userPlayInt == 1 && computer == 3) {
                    System.out.println("Rock beats Scissors!");
                    wins++;

                } else if (userPlayInt == 2 && computer == 1) {
                    System.out.println("Paper beats rock!!");
                    wins++;
                } else if (userPlayInt == 2 && computer == 3) {
                    System.out.println("Paper loses to scissors!");
                    loses++;
                } else if (userPlayInt == 3 && computer == 1) {
                    System.out.println("Scissors loses to rock!");
                    loses++;
                } else if (userPlayInt == 3 && computer == 2) {
                    System.out.println("Scissors beats Paper!");
                    wins++;
                }

            }

            System.out.println("Game over!!!! Here are your results");
            System.out.println(wins + " wins");
            System.out.println(loses + " loses");
            System.out.println(ties + " ties");
            
            if (wins > loses) {
                System.out.println("The final verdict is you are a winner!");
            } else if (wins < loses) {
                System.out.println("The final verdict is you are a loser!");

            } else if (ties > wins && ties > loses) {
                System.out.println("We tied!");
            } else {
                System.out.println("Please Enter In (1) for rock, (2) for paper, or (3) for scissors");
            }

            System.out.println("Play Again? Yes(yes) or NO (no)?");
            play = myScan.nextLine();

        } while (play.equals("yes")); //as long as the user enters in yes, play the game.

        System.out.println("Goodbye thanks for playing!");
    }
}
