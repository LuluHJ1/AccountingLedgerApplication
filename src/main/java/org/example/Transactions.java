package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transactions {
    private LocalDate parsedDate;
    private LocalTime parsedTime;
    private String description;
    private String vendor;
    private double amount;

    public Transactions(LocalDate parsedDate, LocalTime parsedTime, String description, String vendor, double amount) {
        this.parsedDate = parsedDate;
        this.parsedTime = parsedTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getParsedDate() {
        return parsedDate;
    }

    public void setParsedDate(LocalDate parsedDate) {
        this.parsedDate = parsedDate;
    }

    public LocalTime getParsedTime() {
        return parsedTime;
    }

    public void setParsedTime(LocalTime parsedTime) {
        this.parsedTime = parsedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
