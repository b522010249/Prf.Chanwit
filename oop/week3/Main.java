package week3;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. จำลองฐานข้อมูล สร้างบัญชีเตรียมไว้ 5 บัญชี (เงินแยกกัน)
        BankAccount[] database = new BankAccount[5];
        database[0] = new BankAccount("Napintor", "Napintor", 1111, 10000.0);
        database[1] = new BankAccount("Rimochi", "Rimochi", 2222, 5500.0);
        database[2] = new BankAccount("Haru", "Haru", 3333, 15000.0);
        database[3] = new BankAccount("Renoa", "Renoa", 4444, 20000.0);
        database[4] = new BankAccount("Aemeath", "Aemeath", 5555, 50000.0);

        // ลูปชั้นนอก: หน้าต่าง Login
        while (true) {
            System.out.println("\n=== ATM LOGIN SYSTEM ===");
            System.out.println("(Type 'exit' to shutdown system)");
            System.out.print("Enter UserID: ");
            String inputId = sc.next();

            // เช็คว่าต้องการปิดตู้ ATM หรือไม่
            if (inputId.equalsIgnoreCase("exit")) {
                System.out.println("Shutting down ATM... Goodbye!");
                break;
            }

            // 2. ค้นหาไอดีในระบบ
            BankAccount currentAccount = null;
            for (int i = 0; i < database.length; i++) {
                if (database[i].getAccountNo().equals(inputId)) {
                    currentAccount = database[i];
                    break; // เจอไอดีแล้ว หยุดค้นหา
                }
            }

            // 3. ตรวจสอบรหัสผ่าน
            if (currentAccount != null) {
                System.out.print("Enter 4-digit PIN: ");
                int inputPin = sc.nextInt();

                if (currentAccount.checkPin(inputPin)) {
                    System.out.println("\nLogin Successful! Welcome, " + currentAccount.getAccountNo());
                    
                    // นำบัญชีที่ล็อกอินผ่านไปใส่ในตู้ ATM
                    ATM atm = new ATM(currentAccount);
                    int menu;
                    
                    // ลูปชั้นใน: หน้าต่างเมนูหลักของ ATM
                    do {
                        System.out.println("\n=== Main ATM Menu ===");
                        System.out.println("1. Show Account Info");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. Check Balance");
                        System.out.println("0. Logout");
                        System.out.print("Select menu: ");
            
                       menu = sc.nextInt();
            
                       if (menu == 1){
                        atm.showAccountInfo();
                       } else if (menu == 2){
                        System.out.print("Enter deposit amount: ");
                        double amount = sc.nextDouble();
                        atm.depositMoney(amount);
                       } else if (menu == 3){
                        System.out.print("Enter withdrawal amount: ");
                        double amount = sc.nextDouble();
                        atm.withdrawMoney(amount);
                       } else if (menu == 4){
                        atm.checkBalance();
                       } else if (menu == 0){
                        System.out.println("Logging out...");
                        // เมื่อกด 0 จะหลุดจากลูปนี้ กลับไปหน้า Login ใหม่
                       } else {
                        System.out.println("Invalid menu, please try again.");
                       }
                    } while (menu != 0); 

                } else {
                    System.out.println("Error: Incorrect PIN. Please try again.");
                }
            } else {
                System.out.println("Error: UserID not found.");
            }
        }    
        sc.close();
    }
}