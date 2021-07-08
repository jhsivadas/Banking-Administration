package system;

import java.util.InputMismatchException;
import java.util.Scanner;
import dbUtil.DatabaseManagement;

/* This Class Controls the operations that the banking application does */
public class BankingApp {

    DatabaseManagement dm;
    private Scanner scanner;
    
    public BankingApp() {
        dm = new DatabaseManagement();
        scanner = new Scanner(System.in);
    }

    // Runs the application until user exits
    public void runApp() {
        
        System.out.println("\nWelcome! Choose an Option: ");
        
        boolean run = true;
        while (run) {
            System.out.println("\n(1) Create a new account ");
            System.out.println("(2) View Account Information");
            System.out.println("(3) Exit");

            int choice = getIntegerInput(3);

            if (choice == 1) {
                createNewAccount();
            } else if (choice == 2) {
                viewAccount();
            }  else {
                run = false;
            }
        }
    }

    // Gets user input. Throws an error and repeats if input is not an integer.
    public int getIntegerInput(int range) {
        int choice = 0;
        boolean catched = true;
        do {
            try {
                System.out.print("Enter: ");
                choice = scanner.nextInt();
                if (choice > range || choice <= 0) {
                    System.out.println("Enter a valid amount: ");
                } else {
                    catched = false;
                }
            }
            catch (InputMismatchException ex) {
                System.out.println("Enter a valid Integer:");
                scanner.next();
            }
        } while (catched);
        return choice;
    }

    // Creates new account by inserting data into database with unique ID.
    public void createNewAccount() {
        System.out.println("\nWelcome! Enter the following to"
        + "create a new account");
        System.out.println("What is your full name (first and last): ");

        String choice = "";
        boolean catched = true;
        do {
            try {
                choice = scanner.nextLine();
                if (!choice.equals("")) {
                    catched = false;
                }
            }
            catch (InputMismatchException ex) {
                catched = true;
                System.out.println("Enter a valid name: ");
                scanner.next();
            }
        } while (catched);
        dm.insert(choice, 0);
        System.out.println("\nAccount created!\n");
        System.out.println("Login Personal ID: " + dm.retrieveID(choice));
    }

    // Allows user to view account information giving specific ID.
    public void viewAccount() {
        System.out.println("\n---Enter your unique User ID---");
        int id = getIntegerInput(Integer.MAX_VALUE);
        double balance = dm.retrieveBalance(id);
        String name = dm.retrieveName(id);

        System.out.println("\nWelcome " + name + ".\n");
        System.out.println("Current Balance: " + balance);

        // User Input information
        while (true) {
            System.out.println("\n---Choose an Option---");
            System.out.println("(1) View Balance ");
            System.out.println("(2) View ID ");
            System.out.println("(3) Deposit Money ");
            System.out.println("(4) Withdraw Money ");
            System.out.println("(5) Exit ");

            int new_choice = getIntegerInput(5);

            if (new_choice == 1) {
                System.out.println("Current Balance: " + 
                dm.retrieveBalance(id));
            } else if (new_choice == 2) {
                System.out.println("ID: " + id);
            } else if (new_choice == 3) {
                this.deposit(id);
            } else if (new_choice == 4) {
                this.withdraw(id);
            } else {
                break;
            }
        }
    }

    /* Gets user withdrawal ammount and updates database with given amount.
    Throws an error if withdraw amount is over balance. */
    private void withdraw(int id) {
        while (true) {
            System.out.println("How much would you like to withdraw? ");
            int withdraw = getIntegerInput(Integer.MAX_VALUE);
            if (withdraw > dm.retrieveBalance(id)) {
                System.out.println("Sorry, that amount exceeds your" +
                "current balance\n");
            } else {
                dm.updateBalance(id, -1 * withdraw);
                break;
            }
        }
    }

    // Gets deposit amount and updates database.
    private void deposit(int id) {
        System.out.println("How much would you like to deposit (1 - 10000)? ");
        int deposit = getIntegerInput(10000);
        dm.updateBalance(id, deposit);
    }
}