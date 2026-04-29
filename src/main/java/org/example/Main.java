package org.example;

import java.util.*;

import static org.example.Methods.*;

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
                                    } else if (move == 4){
                                        showPreviousYear(transactions);
                                    } else if (move == 5){
                                        showYearToDate(transactions);
                                    } else if (move == 0) {
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
}
