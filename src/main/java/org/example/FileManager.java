package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<Transactions> getTransactions(){
        List<Transactions> transactions = new ArrayList<>();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        try(BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/main/resources/transactions.csv"))){
            
            String input;
            
            while((input = bufferedReader.readLine()) != null){
                String[] csvRow = input.split("\\|");
                
                String dateString = csvRow[0];
                String  timeString = csvRow[1];
                
                LocalDate parsedDate = LocalDate.parse(dateString, dateFormatter);
                LocalTime parsedTime = LocalTime.parse(timeString, timeFormatter);
                
                String description = csvRow[3];
                String vendor = csvRow[4];
                double amount = Double.parseDouble(csvRow[5]);
                Transactions transaction = new Transactions(
                        parsedDate, parsedTime, description, vendor,amount);
                transactions.add(transaction);
            }
            bufferedReader.close();
            return transactions;

        }catch(IOException e){
            System.out.println("There was a problem reading the file");
        }catch(Exception ex){
            System.out.println("There was  problem with the file.");
        }
        return transactions;  
    }
    public static void writeTransaction(Transactions transactions){
        try{
            File file = new File("src/main/resources/transactions.csv");
            FileWriter fileWriter = new FileWriter(file, true);
            if(file.length() > 0){
                fileWriter.write(System.lineSeparator());
            }

        }
        catch(IOException e){
            System.out.println("Error writing file.");
        }
    }
}