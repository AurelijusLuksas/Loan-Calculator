package com.example.loancalculator;

import com.example.loancalculator.ui.home.HomeFragment;

import java.util.ArrayList;

import java.util.List;

public class Calculations {
    boolean isAnnuity;
    boolean isLinear;
    double loanAmount;
    double interestRate;
    int loanTerm;
    double monthlyPayment;
    int postponeStart;
    int postponeEnd;
    ArrayList<Data> monthList;
    HomeFragment homeFragment;
    
    public Calculations(boolean isAnnuity, boolean isLinear, double loanAmount, double interestRate, int loanTerm, int postponeStart, int postponeEnd, HomeFragment homeFragment) {
        this.isAnnuity = isAnnuity;
        this.isLinear = isLinear;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.postponeStart = postponeStart;
        this.postponeEnd = postponeEnd;
        this.homeFragment = homeFragment;

        createCalculation(isAnnuity, isLinear);
    }


    public void createCalculation(boolean isAnnuity, boolean isLinear) {
        monthList = new ArrayList<>();

        if (isAnnuity && !isLinear) {
            createAnnuity();
        } else if (isLinear && !isAnnuity) {
            createLinear();
        } else return;
    }

    public void createAnnuity() {
        monthList.clear();
        double monthlyInterestRate = interestRate / 12 / 100;
        int totalMonths = loanTerm;
        double monthlyPayment;
        if ((1 - Math.pow((1 + monthlyInterestRate), -totalMonths)) == 0) {
            monthlyPayment = loanAmount / totalMonths;
        } else {
            monthlyPayment = loanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -totalMonths));
        }
        int counter = 1;
        double remainingBalance = monthlyPayment * totalMonths;
        double principal = loanAmount;
        double percent;
        int delayStart = postponeStart;
        int delayEnd = postponeEnd;
        for (int month = 1; month <= totalMonths; month++) {
            remainingBalance -= monthlyPayment;
            if (month >= delayStart && month < delayEnd && delayEnd < totalMonths) {
                monthlyPayment = 0;
                counter++;
            }
            if (delayEnd == month && month > 1 && delayEnd < totalMonths) {
                remainingBalance += remainingBalance * counter * monthlyInterestRate;
                monthlyPayment = remainingBalance / (totalMonths - month);
                principal += principal * monthlyInterestRate;
            }
            if (principal <= 0) principal = 0;
            percent = principal * monthlyInterestRate;
            principal -= percent;
            monthList.add(new Data(month, monthlyPayment, percent, Math.abs(remainingBalance)));
        }
        homeFragment.setMonthList(monthList);
    }

    public void createLinear() {
        int totalMonths = loanTerm;
        int months = loanTerm % 12;
        int counter = 1;
        double remainingMortgage = loanAmount;
        double monthlyReduction = loanAmount / totalMonths;
        double monthlyInterestRate = interestRate / 12 / 100;
        double monthlyPayment;
        double totalToPay = 0;
        int delayStart = postponeStart;
        int delayEnd = postponeEnd;
        monthList.clear();
        for (int month = 1; month <= totalMonths; month++) {
            monthlyPayment = monthlyReduction + monthlyInterestRate * remainingMortgage;
            if (month >= delayStart && month < delayEnd && delayEnd < totalMonths) {
                monthlyPayment = 0;
                counter++;
            }
            else if (delayEnd == month && month > 1 && delayEnd < totalMonths) {
                remainingMortgage += remainingMortgage * counter * monthlyInterestRate;
                monthlyReduction = remainingMortgage / (totalMonths - months);
                monthlyPayment = monthlyReduction + remainingMortgage * monthlyInterestRate;
            }
            else {
                remainingMortgage -= monthlyReduction;
            }
            if (remainingMortgage < 0) remainingMortgage = 0;
            totalToPay += monthlyPayment;
        }
        monthlyReduction = loanAmount / totalMonths;
        remainingMortgage = loanAmount;
        counter = 1;
        double percent;
        for (int month = 1; month <= totalMonths; month++) {
            monthlyPayment = monthlyReduction + monthlyInterestRate * remainingMortgage;
            if (month >= delayStart && month < delayEnd && delayEnd < totalMonths) {
                monthlyPayment = 0;
                counter++;
                percent = remainingMortgage * monthlyInterestRate;
            }
            else if (delayEnd == month && month > 1 && delayEnd < totalMonths) {
                remainingMortgage += remainingMortgage * counter * monthlyInterestRate;
                monthlyReduction = remainingMortgage / (totalMonths - months);
                monthlyPayment = monthlyReduction + remainingMortgage * monthlyInterestRate;
                percent = remainingMortgage * monthlyInterestRate;
            }
            else {
                percent = remainingMortgage * monthlyInterestRate;
                remainingMortgage -= monthlyReduction;
            }
            if (remainingMortgage < 0) remainingMortgage = 0;
            totalToPay -= monthlyPayment;
            monthList.add(new Data(month, monthlyPayment, percent, Math.abs(totalToPay)));
        }
        homeFragment.setMonthList(monthList);
    }

    public ArrayList<Data> getMonthList() {
        return monthList;
    }

}
