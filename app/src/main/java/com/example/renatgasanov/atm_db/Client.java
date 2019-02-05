package com.example.renatgasanov.atm_db;

public class Client {
    long card;
    String name;
    long pass;

    public Client(long c, String n, long p) {
        this.card = c;
        this.name = n;
        this.pass = p;
    }

    // setters
    public void setCard(long c) {
        this.card = c;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setPass(long p) {
        this.pass = p;
    }

    // getters
    public long getPass() {
        return this.pass;
    }

    public String getName() {
        return this.name;
    }

    public long getCard() {
        return this.card;
    }
}
