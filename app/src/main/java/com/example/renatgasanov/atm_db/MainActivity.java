package com.example.renatgasanov.atm_db;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button client = findViewById(R.id.client);
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent client_options = new Intent(MainActivity.this, ClientOptions.class);
                startActivity(client_options);
            }
        });

        Button bank = findViewById(R.id.bank);
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bank_options = new Intent(MainActivity.this, BankOptions.class);
                startActivity(bank_options);
            }
        });
    }
}
