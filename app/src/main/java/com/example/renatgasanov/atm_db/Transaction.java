package com.example.renatgasanov.atm_db;

public class Transaction {
    long card;
    int atm_id;
    String datetime;
    int fee;
    int amount;
    int month;

    public Transaction(long card, int id, String datetime, int fee, int a, int MM) {
        this.card = card;
        this.atm_id = id;
        this.datetime = datetime;
        this.fee = fee;
        this.amount = a;
        this.month = MM;
    }

    // setters
    public void setAtm_id(int atm_id) {
        this.atm_id = atm_id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCard(long card) {
        this.card = card;
    }

    public void setFee(int fee) { this.fee = fee; }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    // getters
    public int getAtm_id() {
        return atm_id;
    }

    public long getCard() {
        return card;
    }

    public int getAmount() {
        return amount;
    }

    public int getFee() {
        return fee;
    }

    public String getDatetime() {
        return datetime;
    }
}
