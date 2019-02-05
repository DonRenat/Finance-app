package com.example.renatgasanov.atm_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "banks";

    private static final String TABLE_ATM = "atms";
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_TRANSACTION = "transactions";

    private static final String ID = "id";

    private static final String ATM_ID = "atm_id";
    private static final String ATM_ADR = "atm_adr";
    private static final String BANK_ID = "bank_id";
    private static final String CARD_START = "card_start";
    private static final String CARD_END = "card_end";

    private static final String CARD = "card";
    private static final String NAME = "name";
    private static final String PASS = "pass";

    private static final String DATETIME = "dt";
    private static final String MONTH = "month";
    private static final String FEE = "fee";
    private static final String AMOUNT = "amount";

    private static final String CHECK_ADR = "CHECK(typeof(\"atm_adr\") = \"text\" AND length(\"atm_adr\") > 0 AND length(\"atm_id\") = 6)";
    //private static final String CHECK_CLIENT = "CHECK(typeof(\"name\") = \"text\" AND length(\"name\") > 0 AND typeof(\"pass\") = \"integer\" AND length(\"pass\") < 10 AND typeof(\"card\") = \"long\" AND length(\"card\") < 16)";
    private static final String CHECK_CLIENT = "CHECK(length(\"name\") > 0 AND length(\"pass\") = 10 AND length(\"card\") = 16)";
    //private static final String CHECK_ID = "CHECK(length(\"atm_id\") = 6)";

    private static final String CREATE_TABLE_ATM = "CREATE TABLE "
            + TABLE_ATM + "(" + ATM_ID + " INTEGER PRIMARY KEY," + ATM_ADR + " TEXT NOT NULL," + BANK_ID
            + " TEXT," + CARD_START + " LONG," + CARD_END + " LONG," + CHECK_ADR + ")";

    private static final String CREATE_TABLE_CLIENTS = "CREATE TABLE " + TABLE_CLIENTS
            + "(" + CARD + " LONG PRIMARY KEY," + NAME + " TEXT NOT NULL,"
            + PASS + " LONG NOT NULL," + CHECK_CLIENT + ")";

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE "
            + TABLE_TRANSACTION + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CARD + " LONG NOT NULL," + ATM_ID + " INTEGER NOT NULL,"
            + DATETIME + " DATETIME NOT NULL," + FEE + " INTEGER NOT NULL ," + AMOUNT + " INTEGER NOT NULL, " + MONTH + " INTEGER NOT NULL" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ATM);
        db.execSQL(CREATE_TABLE_CLIENTS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }

     //Creating ATM
    public long createATM(ATM atm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATM_ID, atm.getAtm_id());
        values.put(ATM_ADR, atm.getAtm_adr());
        values.put(BANK_ID, atm.getBank_id());
        values.put(CARD_START, atm.card_start);
        values.put(CARD_END, atm.card_end);
        //values.put(CARD_START, getDateTime());
        return db.insert(TABLE_ATM, null, values);
    }

     //Deleting ATM
    public void deleteATM(int atm_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATM, ATM_ID + " = ?",
                new String[] { String.valueOf(atm_id) });
    }

    //Deleting all atms
    public int deleteAllATMS(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("atms", null, null);
    }

    //Reading all atms
    public String readAllATMS(){
        SQLiteDatabase db = this.getWritableDatabase();
        String result = "Rows in atms: \n";

        // делаем запрос всех данных из таблицы atms, получаем Cursor
        Cursor c = db.query("atms", null, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int atmIDColIndex = c.getColumnIndex("atm_id");
            int atmADRColIndex = c.getColumnIndex("atm_adr");
            int bankIDColIndex = c.getColumnIndex("bank_id");
            int startColIndex = c.getColumnIndex("card_start");
            int endColIndex = c.getColumnIndex("card_end");
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                result+=("atm_id = " + c.getString(atmIDColIndex) +
                        ", atm_adr = " + c.getString(atmADRColIndex) +
                        ", bank_id = " + c.getString(bankIDColIndex) +
                        ", card_start = " + c.getString(startColIndex) +
                        ", card_end = " + c.getString(endColIndex) + "\n");
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            result = "0 rows \n";
        c.close();
        return result;
    }

    //Creating CLIENT
    public long createCLIENT(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARD, client.getCard());
        values.put(NAME, client.getName());
        values.put(PASS, client.getPass());
        //values.put(CARD_START, getDateTime());
        return db.insert(TABLE_CLIENTS, null, values);
    }

    //Reading all clients
    public String readAllClients(){
        SQLiteDatabase db = this.getWritableDatabase();
        String result = "Rows in clients: \n";

        // делаем запрос всех данных из таблицы atms, получаем Cursor
        Cursor c = db.query("clients", null, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int cardColIndex = c.getColumnIndex("card");
            int nameColIndex = c.getColumnIndex("name");
            int passColIndex = c.getColumnIndex("pass");
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                result+=("card = " + c.getString(cardColIndex) +
                        ", name = " + c.getString(nameColIndex) +
                        ", pass = " + c.getString(passColIndex) +  "\n");
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            result = "0 rows \n";
        c.close();
        return result;
    }

    //Deleting all clients
    public int deleteAllClients(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("clients", null, null);
    }

    //Deleting Client
    public void deleteClient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTS, CARD + " = ?",
                new String[] { String.valueOf(id) });
    }
    public List<Long> getAllCards(){
        List<Long> labels = new ArrayList<Long>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getLong(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }
    public String getClientByCard(Long card){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTS + " WHERE " + CARD + " = " + card;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        String name = c.getString(c.getColumnIndex(NAME));

        return name;
    }
    public List<String> getAllATM_ADRs(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_ATM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1) + " " +  cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }
    public int getATMByADRandBANK(String adr, String bank){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ATM + " WHERE " + "(" + ATM_ADR + " = '" + adr + "' AND " + BANK_ID + " = '" + bank + "')";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        int name = c.getInt(c.getColumnIndex(ATM_ID));

        return name;
    }

    //Creating TRANSACTION
    public long createT(Transaction T) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARD, T.getCard());
        values.put(ATM_ID, T.getAtm_id());
        values.put(DATETIME, T.getDatetime());
        values.put(FEE, T.getFee());
        values.put(AMOUNT, T.getAmount());
        values.put(MONTH, T.getMonth());
        return db.insert(TABLE_TRANSACTION, null, values);
    }
    //Reading all transactions
    public String readAllTRS(){
        SQLiteDatabase db = this.getWritableDatabase();
        String result = "Rows in transactions: \n";

        Cursor c = db.query("transactions", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int id = c.getColumnIndex("id");
            int cardd = c.getColumnIndex("card");
            int atmid = c.getColumnIndex("atm_id");
            int dtt = c.getColumnIndex("dt");
            int feee = c.getColumnIndex("fee");
            int amnt = c.getColumnIndex("amount");
            do {
                result+=("id = " + c.getString(id) +
                        ", card = " + c.getString(cardd) +
                        ", atm_id = " + c.getString(atmid) +
                        ", date/time = " + c.getString(dtt) +
                        ", fee = " + c.getString(feee) +
                        ", amount = " + c.getString(amnt) + "\n");
            } while (c.moveToNext());
        } else
            result = "0 rows \n";
        c.close();
        return result;
    }
    //Deleting all transactions
    public int deleteAllTRS(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("transactions", null, null);
    }

    //Deleting Client
    public void deleteTR(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTION, ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    public List<Integer> getTRSbyDATA(Long card, int month, int year){
        List<Integer> res = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String aaa = " strftime('%Y', dt) ";
        //String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + "(" + CARD + " = '" + card + "' AND " + aaa + " = '" + 10 + "')";
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + CARD + " = " + card + " AND " + MONTH + " = " + month;
        Cursor c = db.rawQuery(selectQuery, null);

        //if (c != null)
            //c.moveToFirst();
        //int name = c.getInt(c.getColumnIndex(AMOUNT));
        //String res = String.valueOf(name);
        if (c.moveToFirst()) {
            do {

                int name = c.getInt(c.getColumnIndex(AMOUNT));
                res.add(name);
            } while (c.moveToNext());
        }

        return res;
    }
    public List<String> getTRSbyDATA2(Long card, int month, int year){
        List<String> res = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String aaa = " strftime('%Y', dt) ";
        //String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + "(" + CARD + " = '" + card + "' AND " + aaa + " = '" + 10 + "')";
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + CARD + " = " + card + " AND " + MONTH + " = " + month;
        Cursor c = db.rawQuery(selectQuery, null);

        //if (c != null)
        //c.moveToFirst();
        //int name = c.getInt(c.getColumnIndex(AMOUNT));
        //String res = String.valueOf(name);
        if (c.moveToFirst()) {
            do {

                int name = c.getInt(c.getColumnIndex(AMOUNT));
                String dt = c.getString(c.getColumnIndex(DATETIME));
                int atmid = c.getInt(c.getColumnIndex(ATM_ID));
                int fff = c.getInt(c.getColumnIndex(FEE));
                res.add(dt + " " + "ATM: " + atmid + " Снятие: " + name + " рублей | комиссия " + fff + " рублей \n");
            } while (c.moveToNext());
        }

        return res;
    }
    public List<Integer> getTRSbyDATA3(Long card, int month, int year){
        List<Integer> res = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String aaa = " strftime('%Y', dt) ";
        //String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + "(" + CARD + " = '" + card + "' AND " + aaa + " = '" + 10 + "')";
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + CARD + " = " + card + " AND " + MONTH + " = " + month;
        Cursor c = db.rawQuery(selectQuery, null);

        //if (c != null)
        //c.moveToFirst();
        //int name = c.getInt(c.getColumnIndex(AMOUNT));
        //String res = String.valueOf(name);
        if (c.moveToFirst()) {
            do {

                int name = c.getInt(c.getColumnIndex(FEE));
                res.add(name);
            } while (c.moveToNext());
        }

        return res;
    }
    public List<Integer> getATMS(String bank){
        List<Integer> res = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ATM + " WHERE " + BANK_ID + " = " + "\"" + bank + "\"";
        Cursor c = db.rawQuery(selectQuery, null);

        //if (c != null)
        //c.moveToFirst();
        //int name = c.getInt(c.getColumnIndex(AMOUNT));
        //String res = String.valueOf(name);
        if (c.moveToFirst()) {
            do {
                int rrr = c.getInt(c.getColumnIndex(ATM_ID));
                res.add(rrr);
            } while (c.moveToNext());
        }

        return res;
    }
    public List<String> getBANKDATA(int id, int month){
        List<String> res = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + "(" + CARD + " = '" + card + "' AND " + aaa + " = '" + 10 + "')";
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION + " WHERE " + ATM_ID + " = " + id + " AND " + MONTH + " = " + month;
        Cursor c = db.rawQuery(selectQuery, null);

        //if (c != null)
        //c.moveToFirst();
        //int name = c.getInt(c.getColumnIndex(AMOUNT));
        //String res = String.valueOf(name);
        if (c.moveToFirst()) {
            do {

                int name = c.getInt(c.getColumnIndex(AMOUNT));
                //String bank = c.getString(c.getColumnIndex(BANK_ID));
                int atmid = c.getInt(c.getColumnIndex(ATM_ID));
                int fff = c.getInt(c.getColumnIndex(FEE));
                Long card = c.getLong(c.getColumnIndex(CARD));
                String bankName = "";
                if (card >= 1000000000000000L  && card <= 1999999999999999L) {bankName = "СберБанк";}
                if (card >= 2000000000000000L  && card <= 2999999999999999L) {bankName = "РосБанк";}
                if (card >= 3000000000000000L  && card <= 3999999999999999L) {bankName = "ХоумКредитБанк";}
                res.add("CARD: " + card + " " + "BANK: " + bankName  + " Снятие: " + name + " рублей | комиссия " + fff + " рублей \n");
            } while (c.moveToNext());
        }

        return res;
    }
}

