public class Customer {
    private static int initialCustomerId = 1000001;
    private int customerId;
    private String name;
    private int numAccounts = 0;
    // private int accountCounter = 0;

    public CheckingAccount accounts[];

    public Customer(String inputName){
        customerId = initialCustomerId++;
        name = inputName;
        numAccounts = 0;
        accounts = new CheckingAccount[5];
        System.out.println("Customer ID: " + customerId);
    }

    public String getCustomerName() {
        return name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getNumAccounts() {
        return numAccounts;
    }

    //find the accNum
    public void addAccounts(int numOfAccs, double balance) {
      //balance = the inputted value that goes into the new acc
        if (numOfAccs < 5) {
            if (accounts[numOfAccs] == null) {
                accounts[numAccounts++] = new CheckingAccount(balance);
            } else {
                addAccounts(numOfAccs++, balance);
            }
        } else {
            System.out.println("Maximum number of accounts reached");
        }
    }

    public boolean deposit(int accountId, double depositAmount) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getAccountId() == accountId) {
                accounts[i].deposit(depositAmount);
                return true;
            }
        }
        return false;
    }

    public boolean withdraw(int accountId, double withdrawAmount) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].getAccountId() == accountId) {
                accounts[i].withdraw(withdrawAmount);
                return true;
            }
        }
        return false;
    }
}