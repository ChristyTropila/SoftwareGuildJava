/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vendingmachineproject.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import vendingmachineproject.dto.Coin;
import vendingmachineproject.dto.Snack;

/**
 *
 * @author ctrop
 */
public class VendingMachineView {
    
     private UserIO io;

    //Constructor 
    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    //Menu that will be displayed as soon as the program starts.
    public void displayOptions(List<Snack> allSnacks) {
        io.print("===========VENDING MACHINE========");

        //using an enhanced for loop to list all the items in my txt file.
        for (Snack snack : allSnacks) {
            io.print("----------------------------------");
            io.print("Selection ID: " + snack.getSnackId());
            io.print("Snack Name: " + snack.getName());
            io.print("Price: $" + snack.getPrice());

            if (snack.getQuantity() > 0) {
                io.print("Qty: " + snack.getQuantity());
            } else {
                io.print("Qty: OUT OF STOCK");  //if the quantity of the snack is 0, tell the user.
            }
            io.print("----------------------------------");
        }

    }

    //method that ask user to make a decision to either enter money or exit program
    public int continueOrExit() {
        return io.readInt("Press 1 to make a purchase or 2 to exit: ", 1, 2);
    }

    //method that ask user to enter in their money
    public BigDecimal promptForMoney() {
        return io.readBigDecimal("Please enter your money: ");
    }

    //method that ask user to enter in a snack ID
    public String promptForSelection() {
        return io.readString("Please enter the ID to dispense your snack: ");
    }

    //method that gives the user an opportunity to enter in more money to the vending machine
    public int enterMoreMoney() {
        return io.readInt("Press 1 to enter more money or 2 to continue: ", 1, 2);
    }

    //method that shows user their current balance they have entered in the machine
    public void displayCurrentBalance(BigDecimal userMoney) {
        io.print("Your current balance is: $" + userMoney.setScale(2, RoundingMode.HALF_UP));
    }

    //method that pops up if user enters in an unknown command
    public void displayUnknownCommand() {
        io.print("Unknown Command");
    }

    //message displayed when program has ended
    public void displayExitMessage() {
        io.print("Thank you for your business. Have a great day ");
    }

//Second menu that pops up when user doesn't pick a valid snack id
    public int displaySubMenu() {
        io.print("You need more funds to purchase that item!");
        io.print("");
        io.print("What would you like to do?");
        io.print(" ");
        io.print("1. Enter more money.");
        io.print("2. Try another selection.");
        io.print("3. Exit and get any available balance back.");
        return io.readInt("Make a choice from the menu above: ", 1, 3);
    }

//after user makes a selection, display the user's choice and their change due
    public void displaySelectionAndChangeDue(Snack snackToDispense, BigDecimal changeDue) {
        io.print(" ");
        io.print("You chose " + snackToDispense.getName());
        io.print("Please take your Change: $" + changeDue);
        io.print(" ");
    }

    //use an enhanced for loop to go through all the coin enums and calculate how many of each coin will be dispensed
    public void displayChange(Map<Coin, BigDecimal> listOfChange) {
        for (Coin coin : Coin.values()) {
            io.print("Number of " + coin.name() + ": "
                    + listOfChange.get(coin).setScale(0, RoundingMode.DOWN));

        }
        io.print(" ");
    }

    public void displayUpdatedQuantity(Snack snackQuantity) {
        io.print(" ");
        io.print(snackQuantity.getName() + " has an inventory of " + snackQuantity.getQuantity() + " remaining");
    }

    //when user chooses option 3 in the sub menu, this message will be displayed.
    public void displayNoPurchaseMessage() {
        io.print("You purchased nothing. Here is your money ");
    }
    
}
