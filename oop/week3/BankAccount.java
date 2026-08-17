package week3;

public class BankAccount {
    private String accountNo; // ใช้เป็น UserID
    private String ownerName;
    private int pin;          // เพิ่มรหัสผ่าน PIN 4 หลัก
    private double balance;

    public BankAccount(String accountNo, String ownerName, int pin, double balance) {
        this.accountNo = accountNo;
        this.ownerName = ownerName;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    // เมธอดสำหรับตรวจสอบรหัสผ่าน
    public boolean checkPin(int enteredPin) {
        return this.pin == enteredPin;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful: " + amount + " THB");
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    public void withdraw(double amount){
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than 0 THB");
        } else if (amount > balance) {
            System.out.println("Insufficient balance");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful: " + amount + " THB");
        } 
    }

    public void showAccountInfo() {
        System.out.println("Account Number (UserID): " + accountNo);
        System.out.println("Account Name: " + ownerName);
        System.out.println("Available Balance: " + balance + " THB");
    }
}