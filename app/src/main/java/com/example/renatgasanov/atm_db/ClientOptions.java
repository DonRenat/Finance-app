package com.example.renatgasanov.atm_db;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ClientOptions extends AppCompatActivity {
    Spinner spinner;
    TextView tv;
    Button withdraw, invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_options);

        tv = findViewById(R.id.textView3);
        withdraw = findViewById(R.id.button_withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Withdraw.class);
                Long card = Long.parseLong(spinner.getSelectedItem().toString());
                intent.putExtra("CARD", card);
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                //Long label = Long.parseLong(card);
                String ClientName = db.getClientByCard(card);
                intent.putExtra("NAME", ClientName);
                startActivity(intent);
            }
        });
        invoice = findViewById(R.id.button_check);
        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ClientInvoice.class);
                Long card = Long.parseLong(spinner.getSelectedItem().toString());
                intent.putExtra("CARD", card);
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                //Long label = Long.parseLong(card);
                String ClientName = db.getClientByCard(card);
                intent.putExtra("NAME", ClientName);
                startActivity(intent);
            }
        });
        spinner = findViewById(R.id.card_id);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                Long label = Long.parseLong(parent.getItemAtPosition(position).toString());
                String ClientName = db.getClientByCard(label);
                tv.setText("Добро пожаловать, "+  ClientName + ". Выберите интересующую Вас опцию:");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadSpinnerData();
    }

    private void loadSpinnerData() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<Long> lables = db.getAllCards();
        // Creating adapter for spinner
        ArrayAdapter<Long> dataAdapter = new ArrayAdapter<Long>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

}
