package com.example.renatgasanov.atm_db;

public class Bank {
    private String name;
    private Long card_start;
    private Long card_end;

    Bank(String name, Long start, Long end){
        this.name = name;
        this.card_start = start;
        this.card_end = end;
    }
    public String getName() {
        return name;
    }

    public Long getCard_start() {
        return card_start;
    }

    public Long getCard_end() {
        return card_end;
    }
}
