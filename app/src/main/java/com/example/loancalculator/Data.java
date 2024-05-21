package com.example.loancalculator;

public class Data {
    int index;
    double monthlyPayment;
    double monthlyInterest;
    double remainingBalance;


    public Data(int index, double monthlyPayment, double monthlyInterest, double remainingBalance) {
        this.index = index;
        this.monthlyPayment = monthlyPayment;
        this.monthlyInterest = monthlyInterest;
        this.remainingBalance = remainingBalance;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getMonthName() {
        return String.valueOf(index);
    }
}
