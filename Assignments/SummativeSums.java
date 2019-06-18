/*
Assignment Summative Sums
Written By Christy Tropila
Date November 18, 2018
 */
package summativesums;

/**
 *
 * @author ctrop
 */
public class SummativeSums {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("#1 Array Sum is : " + addArrayOne());
        System.out.println("#2 Array Sum is : " + addArrayTwo());
        System.out.println("#3 Array Sum is : " + addArrayThree());
    }
    
    
//this method uses a for loop to go through each number in the array and add them together
    public static int addArrayOne() {
        int[] arrayOne = {1, 90, -33, -55, 67, -16, 28, -55, 15};
        int sumOne = 0;

        for (int i = 0; i < arrayOne.length; i++) {

            sumOne += arrayOne[i];
        }
        return sumOne;

    }

    //this method uses a for loop to go through each number in the array and add them together
    public static int addArrayTwo() {
        int[] arrayTwo = {999, -60, -77, 14, 160, 301};
        int sumTwo = 0;

        for (int i = 0; i < arrayTwo.length; i++) {
            sumTwo += arrayTwo[i];

        }
        return sumTwo;

    }

    //this method uses a for loop to go through each number in the array and add them together
    public static int addArrayThree() {
        int[] arrayThree = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,
            140, 150, 160, 170, 180, 190, 200, -99};
        int sumThree = 0;

        for (int i = 0; i < arrayThree.length; i++) {
            sumThree += arrayThree[i];

        }

        return sumThree;
    }

}
