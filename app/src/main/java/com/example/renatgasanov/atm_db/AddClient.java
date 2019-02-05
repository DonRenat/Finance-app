package com.example.renatgasanov.atm_db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddClient extends AppCompatActivity implements OnClickListener{
    Button btnAdd, btnRead, btnClear, btnDel;
    EditText card_edit, name_edit, pass_edit, del_edit;

    DatabaseHelper dbHelper;
    Long start, end;
    String bank_id_for_edit;
    TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        log = findViewById(R.id.log);
        log.setMovementMethod(new ScrollingMovementMethod());

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        card_edit = findViewById(R.id.card_edit);
        name_edit = findViewById(R.id.name_edit);
        pass_edit = findViewById(R.id.pass_edit);
        del_edit = findViewById(R.id.del_edit);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DatabaseHelper(this);

        bank_id_for_edit = getIntent().getStringExtra("BANK_ID");
        //bank_id_edit.setText(bank_id_for_edit);
        start = getIntent().getLongExtra("START", 0L);
        end = getIntent().getLongExtra("END", 0L);
        //card_field_edit.setText(start.toString() + " - " + end.toString());

        TextView bd = findViewById(R.id.bank_data);
        bd.setText(bank_id_for_edit+"\n CARDS: " + start + "-" + end);
    }

    @Override
    public void onClick(View v) {
        //int del_id = Integer.parseInt(del_edit.getText().toString()); todo

        switch (v.getId()) {
            case R.id.btnAdd:
                String name = name_edit.getText().toString();
                long pass = Long.parseLong(pass_edit.getText().toString());
                long card = Long.parseLong(card_edit.getText().toString());

                if(card<start || card>end) { card_edit.setError("Incorrect card number!"); log.append("Error in card number! \n"); break; } else {
                long RES = dbHelper.createCLIENT(new Client(card, name, pass));
                if (RES == -1) { pass_edit.setError("Check!"); card_edit.setError("Check!"); name_edit.setError("Check!"); log.append("Error in fields! \n"); break; }
                else { pass_edit.setError(null); card_edit.setError(null); name_edit.setError(null); log.append("Insert in clients: "); log.append("row inserted, ID = " + RES + "\n");
                pass_edit.setText(""); card_edit.setText(""); name_edit.setText(""); break; } }
            case R.id.btnRead:
                log.append(dbHelper.readAllClients());
                break;
            case R.id.btnClear:
                log.append("Clear clients: ");
                log.append("deleted rows count = " + dbHelper.deleteAllClients() + "\n");
                break;
            case R.id.btnDel:
                if (del_edit.getText().toString().equals("")){ log.append("Empty CARD! \n"); del_edit.setError("Enter CARD!"); break; }
                else {
                    int delID = Integer.parseInt(del_edit.getText().toString());
                    log.append("Deleted row from clients with card="+delID+"\n");
                    dbHelper.deleteClient(delID);
                    break;}
        }
        dbHelper.close();
    }
}
