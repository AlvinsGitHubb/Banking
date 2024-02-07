public class CheckingAccount {
    private static int nextAccountId = 5000001;
    private int accountId;
    private double amount;
    // private int originalAccountId;

    CheckingAccount(double initialAmount) {
        accountId = nextAccountId++;
        amount = initialAmount;
    }

    public double getBalance() {
        return this.amount; // Assuming 'amount' is the balance field
    }

    public int getAccountId() {
        return accountId;
    }

    public double deposit(double deposit) {
        amount += deposit;
        System.out.println("New balance: " + amount);
        return amount;
    }

    public double withdraw(double withdrawal) {
        if (withdrawal > amount) {
            System.out.println("Withdrawal rejected to avoid negative balance.");
            return amount;
        } else {
            amount -= withdrawal;
            amount = Math.round(amount * 100.0) / 100.0;
            System.out.println("New balance: " + amount);
            return amount;
        }
    }
}