package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Methods {

    //Declaring color codes
    //Declaring reset code to stop color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_GREEN = "\u001b[32m";

    //METHODS accessed in other classes
    public static void showMenu() {
        System.out.println(ANSI_RED + "Home Page" + ANSI_RESET);
        System.out.println("----------------------------");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment(Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.println("Enter Letter Option: ");
    }
    public static void showLedgerMenu(){
        System.out.println(ANSI_RED + "Ledger Page" + ANSI_RESET);
        System.out.println("----------------------------");
        System.out.println("A) Display all entries");
        System.out.println("D) Display all deposits");
        System.out.println("P) Display all payments");
        System.out.println("R) Display reports");
        System.out.println("H) Back to home page");
        System.out.println("Enter letter option: ");
    }
    public static void showReportsMenu(){
        System.out.println(ANSI_RED + "Reports Page" + ANSI_RESET);
        System.out.println("----------------------------");
        System.out.println("0. Back to ledger page");
        System.out.println("1. Search by vendor");
        System.out.println("2. Previous month transactions");
        System.out.println("3. Month to date transactions");
        System.out.println("4. Previous year transactions");
        System.out.println("5. Year to date transaction");
        System.out.println("Enter number option: ");
    }
    public static void addDeposit(double amount, List<Transactions> transactions) {
        Transactions deposit = new Transactions(
                LocalDate.now(), LocalTime.now().withNano(0), "Deposit", "BANK", amount);
        transactions.add(deposit);
        FileManager.writeTransaction(deposit);

    }

    public static void makePayment(double amount, String entity, List<Transactions> transactions) {
        Transactions payment = new Transactions(
                LocalDate.now(), LocalTime.now().withNano(0), "Payment", entity, -amount);
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
