package org.example;

import java.util.*;

import static org.example.Methods.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Transactions> transactions = FileManager.getTransactions();

        while (true) {
            //method to show menu
            showMenu();


            String choice = scanner.next();
            //make it user-friendly with casing
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
                        //another example making it user-friendly with equalsIgnoreCase
                        // to ignore casing

                        if (option.equalsIgnoreCase("A")) {
                            //adding color to titles
                            System.out.println(ANSI_BLUE + "All Transactions: " + ANSI_RESET);
                            showAllEntries(transactions);

                        } else if (option.equalsIgnoreCase("D")) {
                            System.out.println(ANSI_GREEN + "All Deposits: " + ANSI_RESET);
                            showAllDeposits(transactions);

                        } else if (option.equalsIgnoreCase("P")) {
                            System.out.println( ANSI_BLUE + "All Payments: " + ANSI_RESET);
                            showAllPayments(transactions);

                        } else if (option.equalsIgnoreCase("R")) {

                            while (true) {
                                //method to showReportsMenu option
                                showReportsMenu();
                                //sorting through file in order by date
                                transactions.sort(Comparator.comparing(Transactions::getParsedDate).thenComparing(Transactions::getParsedDate));
                                try {
                                    int move = scanner.nextInt();

                                    if (move == 1) {
                                        System.out.println("Enter vendor name: ");
                                        scanner.nextLine();
                                        String vendor = scanner.nextLine().toLowerCase();
                                        System.out.println("Transaction by selected vendor: ");
                                        searchByVendor(transactions, vendor);

                                    } else if (move == 2) {
                                        System.out.println( ANSI_BLUE + "Previous month transactions: " + ANSI_RESET);
                                        showPreviousMonth(transactions);
                                    } else if (move == 3){
                                        System.out.println(ANSI_BLUE+ "Month to date transactions: " + ANSI_RESET);
                                        showMonthToDate(transactions);
                                    } else if (move == 4){
                                        System.out.println(ANSI_BLUE + "Previous year transactions: " + ANSI_RESET);
                                        showPreviousYear(transactions);
                                    } else if (move == 5){
                                        System.out.println(ANSI_BLUE + "Year to date transactions: " + ANSI_RESET);
                                        showYearToDate(transactions);
                                    } else if (move == 0) {
                                        break;
                                    } else {
                                        //defensive coding
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
                                //defensive coding
                                System.out.println("Invalid option");
                            }
                        }

                    break;

                case "X":
                    System.out.println("Exiting");
                    System.exit(0);
                    break;
                default:
                    //defensive coding
                    System.out.println("Invalid option");
            }
        }
    }
}
