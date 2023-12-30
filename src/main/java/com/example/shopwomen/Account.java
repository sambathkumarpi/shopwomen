package com.example.shopwomen;

public class Account {
    private int id;
    private double Capital;
    private double Income;
    private double cost;

    public int getId() {
        return id;
    }

    public double getCapital() {
        return Capital;
    }

    public double getIncome() {
        return Income;
    }

    public double getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCapital(double capital) {
        Capital = capital;
    }

    public void setIncome(double income) {
        Income = income;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Account(int id, double capital, double income, double cost) {
        this.id = id;
        Capital = capital;
        Income = income;
        this.cost = cost;
    }
}
