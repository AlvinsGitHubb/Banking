
/*
 * Author: Alvin Mathew
 * Description: In this bank account program the user has 5 options. The user can create a new customer 
 * account with a name and customer id attached to the customer account while having to add an initial deposit
 * while initially creating the customer account, the user can also add a new account within their customer
 * account (with a maximum of 5 account per customer account), deposit into an account via account id
 * withdraw from an account via account id, view a customer account, or close the application entirely.
 * The unique aspect about this code is that it allows the user to deposit in terms of rupees, not just USD.
 */
import java.util.*;

public class BankOperations {
  static final double INR_TO_USD_RATE = 1.0 / 75.0; // Example conversion rate: 1 USD = 75 INR

  static Customer customers[] = new Customer[1000];

  static Customer checkingAccIDSearch(int accountId) {
    for (int i = 0; i < customers.length; i++) {
      if (customers[i] == null) {
        continue; // Skip the rest of the loop if customer is null.
      }
      for (int j = 0; j < customers[i].getNumAccounts(); j++) {
        if (customers[i].accounts[j].getAccountId() == accountId) {
          return customers[i];
        }
      }
    }
    return null;
  }

  static Customer customerIDSearch(int id) {
    for (int i = 0; i < customers.length; i++) {
      if (customers[i] == null) {
        break;
      } else if (customers[i].getCustomerId() == id) {
        return customers[i];
      }
    }
    return null;
  }

  static Customer customerNameSearch(String inputName) {
    for (int i = 0; i < customers.length; i++) {
      if (customers[i] == null) {
        break;
      } else if (customers[i].getCustomerName().contains(inputName)) {
        return customers[i];
      }
    }
    return null;
  }

  public static void main(String[] args) {
    try (Scanner input = new Scanner(System.in)) {

      while (true) {
        // Display the menu to the user
        System.out.println("\nBank Operations:");
        System.out.println("1. New Customer (new)");
        System.out.println("2. Add Account (add)");
        System.out.println("3. Deposit (deposit)");
        System.out.println("4. Withdraw (withdraw)");
        System.out.println("5. Display (display)");
        System.out.println("6. Close Application (close)");
        System.out.print("Enter command: ");

        String Command = input.next().toLowerCase(); // Read the command from user
        String command = convertInputToCommand(Command); // Convert input to command if necessary
        switch (command) {
          case "new":
            handleNewCustomer(input, customers);
            break;
          case "add":
            handleAddAccount(input, customers);
            break;
          case "deposit":
            handleDeposit(input, customers);
            break;
          case "withdraw":
            handleWithdraw(input, customers);
            break;
          case "display":
            displayCustomerAccounts(input);
            break;
          case "close":
            System.out.println("Closing application.");
            return;
          default:
            System.out.println("Invalid command. Please try again.");
            break;
        }
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  /*
   * convert input
   * made private to provide encapsulation
   */
  private static String convertInputToCommand(String input) {
    switch (input) {
      case "1":
      case "new":
        return "new";
      case "2":
      case "add":
        return "add";
      case "3":
      case "deposit":
        return "deposit";
      case "4":
      case "withdraw":
        return "withdraw";
      case "5":
      case "display":
        return "display";
      case "6":
      case "close":
        return "close";
      default:
        return input; // Return the input as is if it doesn't match any case
    }
  }

  /*
   * New
   * ask how many accounts user wants
   * asks user for customer name
   * create new account with an initial deposit
   */
  public static void handleNewCustomer(Scanner input, Customer[] customers) {
    int numAccounts;
    String inputName;
    int customerIndex = findNextCustomerIndex(customers); // Find the next empty index for a new customer

    System.out.println("How many accounts would you like to add: ");
    numAccounts = input.nextInt();

    if (numAccounts > 5) {
      System.out.println("MAX 5 accounts per customer!");
      return; // Exit the method if the number of accounts exceeds the maximum allowed
    }

    System.out.print("Enter the name of the account owner: ");
    input.nextLine(); // Consume the newline left by nextInt()
    inputName = input.nextLine(); // Read the full line including spaces

    Customer newCustomer = new Customer(inputName);
    customers[customerIndex] = newCustomer; // Add the new customer to the array

    for (int i = 0; i < numAccounts; i++) {
      System.out.println("Enter initial deposit for account " + (i + 1) + ": ");
      double initialDeposit = input.nextDouble();
      newCustomer.addAccounts(i, initialDeposit); // Assumes addAccounts method properly handles account creation
      System.out.println("Account ID: " + newCustomer.accounts[i].getAccountId());
    }
  }

  private static int findNextCustomerIndex(Customer[] customers) {
    for (int i = 0; i < customers.length; i++) {
      if (customers[i] == null) {
        return i; // Returns the index of the first null element in the array
      }
    }
    return -1; // Returns -1 if the array is full
  }

  /*
   * Add
   */
  public static void handleAddAccount(Scanner input, Customer[] customers) {
    System.out.println("Enter the customer id you would like to add an account to: ");
    int trackID = input.nextInt();

    Customer existingCustomer = customerIDSearch(trackID);
    if (existingCustomer == null) {
      System.out.println("Customer does not exist!");
      return; // Exit the method if the customer does not exist
    }

    System.out.println("How many accounts would you like to add: ");
    int numAccounts = input.nextInt();

    if (existingCustomer.getNumAccounts() + numAccounts <= 5) {
      for (int i = 0; i < numAccounts; i++) {
        System.out.println("Enter deposit amount for account " + (i + 1) + ": ");
        double deposit = input.nextDouble();
        existingCustomer.addAccounts(existingCustomer.getNumAccounts(), deposit);
        System.out
            .println("Account ID: " + existingCustomer.accounts[existingCustomer.getNumAccounts() - 1].getAccountId());
      }
    } else {
      System.out
          .println("Error: Adding " + numAccounts + " accounts exceeds the maximum limit of 5 accounts per customer.");
    }
  }

  /*
   * Deposit
   */
  public static void handleDeposit(Scanner input, Customer[] customers) {
    System.out.println("Enter the customer id you would like to add an account to: ");
    int trackID = input.nextInt();

    // Verify customer ID
    Customer existingCustomer = customerIDSearch(trackID);
    if (existingCustomer == null) {
      System.out.println("Customer does not exist!");
      return; // Exit the method if the customer does not exist
    }

    System.out.println("Enter the account id you would like to add an account to: ");
    int acctIdInput = input.nextInt();

    // Verify account ID
    Customer depositAccount = checkingAccIDSearch(acctIdInput);
    if (depositAccount == null) {
      System.out.println("Account does not exist!");
      return; // Exit the method if the account does not exist
    }
    System.out.println("Enter Which currency you would like to choose to deposit with: ");
    System.out.println("1. USD: ");
    System.out.println("2. INR: ");
    int choice = input.nextInt();
    if (choice == 1) {
      System.out.println("Enter the amount you would like to deposit to " + acctIdInput + ": ");
      double depositValue = input.nextDouble();
      // Perform the deposit operation
      boolean depositMade = false;
      for (int i = 0; i < depositAccount.getNumAccounts(); i++) {
        if (depositAccount.accounts[i].getAccountId() == acctIdInput) {
          depositAccount.accounts[i].deposit(depositValue);
          depositMade = true;
          break; // Exit the loop once the deposit is made
        }
      }
      if (!depositMade) {
        System.out.println("Deposit operation failed. Account ID not found under the given customer.");
      }
    } else {
      System.out.println("Enter the amount (in INR) you would like to deposit to " + acctIdInput + ": ");
      double depositValueInr = input.nextDouble();
      double depositValueUsd = depositValueInr * INR_TO_USD_RATE; // Convert INR to USD
      // Perform the deposit operation
      boolean depositMade = false;
      for (int i = 0; i < depositAccount.getNumAccounts(); i++) {
        if (depositAccount.accounts[i].getAccountId() == acctIdInput) {
          depositAccount.accounts[i].deposit(depositValueUsd);
          depositMade = true;
          break; // Exit the loop once the deposit is made
        }
      }
      if (!depositMade) {
        System.out.println("Deposit operation failed. Account ID not found under the given customer.");
      }
    }
  }

  /*
   * Withdraw
   */
  public static void handleWithdraw(Scanner input, Customer[] customers) {
    System.out.println("Enter the customer id you would like to withdraw from: ");
    int trackID = input.nextInt();

    // Verify customer ID
    Customer existingCustomer = customerIDSearch(trackID);
    if (existingCustomer == null) {
      System.out.println("Customer does not exist!");
      return; // Exit the method if the customer does not exist
    }

    System.out.println("Enter the account id you would like to withdraw from: ");
    int acctIdInput = input.nextInt();

    // Verify account ID
    Customer withdrawAccount = checkingAccIDSearch(acctIdInput);
    if (withdrawAccount == null) {
      System.out.println("Account does not exist!");
      return; // Exit the method if the account does not exist
    }

    System.out.println("Enter the amount you would like to withdraw from " + acctIdInput + ": ");
    double withdrawValue = input.nextDouble();

    // Perform the withdrawal operation
    boolean withdrawalMade = false;
    for (int i = 0; i < withdrawAccount.getNumAccounts(); i++) {
      if (withdrawAccount.accounts[i].getAccountId() == acctIdInput) {
        withdrawAccount.accounts[i].withdraw(withdrawValue);
        withdrawalMade = true;
        break; // Exit the loop once the withdrawal is made
      }
    }

    if (!withdrawalMade) {
      System.out.println("Withdrawal operation failed. Account ID not found under the given customer.");
    }
  }

  /*
   * Display
   */
  private static void displayCustomerAccounts(Scanner input) {
    System.out.println("Enter the customer ID to display accounts: ");
    int custId = input.nextInt();
    Customer customer = customerIDSearch(custId);
    if (customer == null) {
      System.out.println("Customer does not exist!");
    } else {
      System.out.println("Displaying accounts for customer ID: " + custId);
      for (int i = 0; i < customer.getNumAccounts(); i++) {
        if (customer.accounts[i] != null) { // Ensure the account exists
          System.out.println("Account ID: " + customer.accounts[i].getAccountId() +
              ", Balance: " + customer.accounts[i].getBalance() + " USD");
        }
      }
    }
  }

}