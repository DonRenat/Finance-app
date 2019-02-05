package com.example.renatgasanov.atm_db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Add_ATM extends AppCompatActivity implements OnClickListener {

    Button btnAdd, btnRead, btnClear, btnDel;
    EditText del_id_edit, atm_adr_edit, bank_id_edit, card_field_edit, atm_id_edit;

    DatabaseHelper dbHelper;

    TextView log;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__atm);

        log = findViewById(R.id.text_log);
        log.setMovementMethod(new ScrollingMovementMethod());

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        atm_adr_edit = findViewById(R.id.atm_adr_edit);
        atm_id_edit = findViewById(R.id.atm_id_edit);
        bank_id_edit = findViewById(R.id.bank_id_edit);
        card_field_edit = findViewById(R.id.card_field_edit);
        del_id_edit = findViewById(R.id.del_id_edit);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DatabaseHelper(this);

        String bank_id_for_edit = getIntent().getStringExtra("BANK_ID");
        bank_id_edit.setText(bank_id_for_edit);

        Long start = getIntent().getLongExtra("START", 0L);
        Long end = getIntent().getLongExtra("END", 0L);

        card_field_edit.setText(start.toString() + " - " + end.toString());
    }


    @Override
    public void onClick(View v) {
        String bank_id = bank_id_edit.getText().toString();
        Long start = getIntent().getLongExtra("START", 0L);
        Long end = getIntent().getLongExtra("END", 0L);

        switch (v.getId()) {
            case R.id.btnAdd:
                int atm_id = Integer.parseInt(atm_id_edit.getText().toString());
                String atm_adr_raw = atm_adr_edit.getText().toString();
                String atm_adr = atm_adr_raw.replaceAll("\\s+","-");
                long RES = dbHelper.createATM(new ATM(atm_id, atm_adr, bank_id, start, end));
                if (RES == -1 && (String.valueOf(atm_id).length()<6 || String.valueOf(atm_id).length()>6)) { atm_id_edit.setError("ID может быть только шестизначный!"); log.append("Incorrect ID! \n"); break; }
                if (RES == -1 && String.valueOf(atm_id).length()==6) { atm_adr_edit.setError("Пустой адрес!"); log.append("Empty address! \n"); break; }
                else { atm_adr_edit.setError(null); atm_adr_edit.setText(""); atm_id_edit.setError(null); atm_id_edit.setText(""); log.append("Insert in atms: "); log.append("row inserted, ID = " + RES + "\n"); break; }
            case R.id.btnRead:
                log.append(dbHelper.readAllATMS());
                break;
            case R.id.btnClear:
                log.append("Clear atms: ");
                log.append("deleted rows count = " + dbHelper.deleteAllATMS() + "\n");
                break;
            case R.id.btnDel:
                if (del_id_edit.getText().toString().equals("")){ log.append("Empty ID! \n"); del_id_edit.setError("Enter ID!"); break; }
                else {
                int delID = Integer.parseInt(del_id_edit.getText().toString());
                log.append("Deleted row from atms with id="+delID+"\n");
                dbHelper.deleteATM(delID);
                break;}
        }
        dbHelper.close();
    }
}
