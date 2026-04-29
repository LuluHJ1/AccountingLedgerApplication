package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Transactions> transactions = FileManager.getTransactions();

        while (true) {

            showMenu();

            String choice = scanner.next();
            choice = choice.toUpperCase();

            switch (choice) {
                case "D":
                    try {
                        System.out.println("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        addDeposit(depositAmount, transactions);
                        System.out.println("Deposit complete.");
                    }catch(InputMismatchException ex){
                        System.out.println("Please enter number.");
                        scanner.nextLine();
                    }
                    break;

                case "P":
                    try {
                        System.out.println("Enter payment amount: ");
                        double paymentAmount = scanner.nextDouble();
                        System.out.println("Who is the payment to: ");
                        scanner.nextLine();
                        String entity = scanner.nextLine();
                        makePayment(paymentAmount, entity, transactions);
                        System.out.println("Payment Complete");
                    }catch(InputMismatchException e){
                        System.out.println("Invalid input");
                    }
                    break;
                case "L":
                    while(true) {
                        showLedgerMenu();

                        String option = scanner.next();

                        if (option.equalsIgnoreCase("A")) {
                            showAllEntries(transactions);

                        } else if (option.equalsIgnoreCase("D")) {
                            showAllDeposits(transactions);

                        } else if (option.equalsIgnoreCase("P")) {
                            showAllPayments(transactions);

                        } else if (option.equalsIgnoreCase("R")) {

                            while (true) {
                                showReportsMenu();
                                transactions.sort(Comparator.comparing(Transactions::getParsedDate).thenComparing(Transactions::getParsedDate));
                                try {
                                    int move = scanner.nextInt();

                                    if (move == 1) {
                                        System.out.println("Enter vendor name: ");
                                        scanner.nextLine();
                                        String vendor = scanner.nextLine().toLowerCase();
                                        searchByVendor(transactions, vendor);

                                    } else if (move == 2) {
                                        showPreviousMonth(transactions);
                                    } else if (move == 3){
                                        showMonthToDate(transactions);
                                    }
                                    else if (move == 4){
                                        showPreviousYear(transactions);
                                    }
                                    else if (move == 5){
                                        showYearToDate(transactions);
                                    }
                                    else if (move == 0) {
                                        break;
                                    } else {
                                        System.out.println("Invalid input");
                                        break;
                                    }
                                }catch(InputMismatchException ex){
                                    System.out.println("Invalid input");
                                    scanner.nextLine();
                                }
                            }
                            }else if (option.equalsIgnoreCase("H")) {
                                break;

                            } else {
                                System.out.println("Invalid option");
                            }
                        }

                    break;

                case "X":
                    System.out.println("Exiting");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public static void showMenu() {
        System.out.println("Home Screen");
        System.out.println("-----------------");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment(Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.println("Enter Letter Option: ");
    }
    public static void showLedgerMenu(){
        System.out.println("Ledger Page");
        System.out.println("----------------------------");
        System.out.println("A) Display all entries");
        System.out.println("D) Display all deposits");
        System.out.println("P) Display all payments");
        System.out.println("R) Display reports");
        System.out.println("H) Back to home page");
        System.out.println("Enter letter option: ");
    }
    public static void showReportsMenu(){
        System.out.println("0. Back to ledger page");
        System.out.println("1. Search by vendor");
        System.out.println("2. Previous month transactions");
        System.out.println("3. Month to date transactions");
        System.out.println("4. Previous year transactions");
        System.out.println("5. Year to date transaction");
    }
    public static void addDeposit(double amount, List<Transactions> transactions) {
        Transactions deposit = new Transactions(
                LocalDate.now(), LocalTime.now(), "Deposit", "BANK", amount);
        transactions.add(deposit);
        FileManager.writeTransaction(deposit);

    }

    public static void makePayment(double amount, String entity, List<Transactions> transactions) {
        Transactions payment = new Transactions(
                LocalDate.now(), LocalTime.now(), "Payment", entity, -amount);
        transactions.add(payment);
        FileManager.writeTransaction(payment);
    }

    public static void showAllEntries(List<Transactions> transactions) {
        for (Transactions t : transactions) {
            System.out.println(t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                    t.getVendor() + "|" + t.getAmount());
        }
    }
    public static void showAllDeposits(List<Transactions> transactions) {
        for (Transactions t : transactions) {
            if (t.getAmount() > 0) {
                System.out.println(t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                        t.getVendor() + "|" + t.getAmount());
            }
        }
    }
    public static void showAllPayments(List<Transactions> transactions){
        for(Transactions t : transactions){
            if(t.getAmount() < 0) {
                System.out.println(t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                        t.getVendor() + "|" + t.getAmount());
            }
        }
    }
    public static void searchByVendor(List<Transactions> transactions, String vendor){
            for (Transactions t : transactions){
                if(t.getVendor().equalsIgnoreCase(vendor)){
                    System.out.println(
                            t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                                    t.getVendor() + "|" + t.getAmount());

            }
        }
    }
    public static void showPreviousMonth(List<Transactions> transactions){

        LocalDate now = LocalDate.now();

        LocalDate firstDayLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayLastMonth = now.withDayOfMonth(1).minusDays(1);

        for(Transactions t : transactions){

            LocalDate date = t.getParsedDate();
            if((date.isEqual(firstDayLastMonth) || date.isAfter(firstDayLastMonth)) &&
                    (date.isEqual(lastDayLastMonth) || date.isBefore(lastDayLastMonth))){
                System.out.println(t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                        t.getVendor() + "|" + t.getAmount());


            }
        }
    }
    public static void showPreviousYear(List<Transactions> transactions){

        LocalDate now = LocalDate.now();

        LocalDate firstDayLastYear = now.minusYears(1).withDayOfYear(1);
        LocalDate lastDayLastYear = now.withDayOfYear(1).minusDays(1);

        for(Transactions t : transactions){

            LocalDate date = t.getParsedDate();

            if((date.isEqual(firstDayLastYear) || date.isAfter(firstDayLastYear)) &&
                    (date.isEqual(lastDayLastYear) || date.isBefore(lastDayLastYear))){

                System.out.println(t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                        t.getVendor() + "|" + t.getAmount());
            }
        }
    }
    public static void showYearToDate(List<Transactions> transaction){

        LocalDate now = LocalDate.now();
        LocalDate startOfYear = now.withDayOfYear(1);

        for(Transactions t : transaction){

        LocalDate date = t.getParsedDate();

        if((date.isEqual(startOfYear) || date.isAfter(startOfYear)) &&
                (date.isEqual(now) || date.isBefore(now))){

                System.out.println(t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                        t.getVendor() + "|" + t.getAmount());
            }
        }
    }
    public static void showMonthToDate(List<Transactions> transaction){

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);

        for(Transactions t : transaction){

            LocalDate date = t.getParsedDate();

            if((date.isEqual(startOfMonth) || date.isAfter(startOfMonth)) &&
                    (date.isEqual(now) || date.isBefore(now))){
                System.out.println(t.getParsedDate() + "|" + t.getParsedTime() + "|" + t.getDescription() + "|" +
                        t.getVendor() + "|" + t.getAmount());
            }
        }
    }
}
