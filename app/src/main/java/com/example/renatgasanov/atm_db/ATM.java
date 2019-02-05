package com.example.renatgasanov.atm_db;

public class ATM {
    int atm_id;
    String atm_adr;
    String bank_id;
    long card_start;
    long card_end;

    public ATM(int id, String adr, String bank_id, long start, long end) {
        this.atm_id = id;
        this.atm_adr = adr;
        this.bank_id = bank_id;
        this.card_start = start;
        this.card_end = end;
    }

    // setters

    public void setAtm_adr(String atm_adr) {
        this.atm_adr = atm_adr;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public void setCard_start(long card_start) {
        this.card_start = card_start;
    }

    public void setCard_end(long card_end) {
        this.card_end = card_end;
    }

    public void setAtm_id(int atm_id) { this.atm_id = atm_id; }

    // getters

    public long getCard_end() {
        return card_end;
    }

    public long getCard_start() {
        return card_start;
    }

    public String getAtm_adr() {
        return atm_adr;
    }

    public String getBank_id() {
        return bank_id;
    }

    public int getAtm_id() { return atm_id; }
}
