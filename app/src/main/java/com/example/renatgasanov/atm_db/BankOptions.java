package com.example.renatgasanov.atm_db;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class BankOptions extends AppCompatActivity {
    //Sberbank - 1000 0000 0000 0000 - 1999 9999 9999 9999
    Bank Sberbank = new Bank("СберБанк", 1000000000000000L, 1999999999999999L);
    //Rosbank - 2000 0000 0000 0000 - 2999 9999 9999 9999
    Bank Rosbank = new Bank("РосБанк", 2000000000000000L, 2999999999999999L);
    //HomeCreditBank - 3000 0000 0000 0000 - 3999 9999 9999 9999
    Bank HomeCreditBank = new Bank("ХоумКредитБанк", 3000000000000000L, 3999999999999999L);

    Bank[] banks = {Sberbank, Rosbank, HomeCreditBank};
    //String[] bank_names = {"Sberbank", "Rosbank", "HomeCreditBank"};
    String[] bank_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_options);

        bank_names = new String[banks.length];
        for (int i = 0; i <= banks.length-1; i++){
            bank_names[i] = banks[i].getName();
        }

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bank_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = findViewById(R.id.bank_id);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Название банка");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Button add_atm = findViewById(R.id.button_add_atm);
        add_atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Add_ATM = new Intent(getBaseContext(), Add_ATM.class);
                Add_ATM.putExtra("BANK_ID", spinner.getSelectedItem().toString());

                for (int i = 0; i <= banks.length-1; i++){
                    if (banks[i].getName().equals(spinner.getSelectedItem().toString())){
                        Add_ATM.putExtra("START", banks[i].getCard_start());
                        Add_ATM.putExtra("END", banks[i].getCard_end());
                    }
                }

                startActivity(Add_ATM);
            }
        });

        Button add_client = findViewById(R.id.button_add_client);
        add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Add_Client = new Intent(getBaseContext(), AddClient.class);
                Add_Client.putExtra("BANK_ID", spinner.getSelectedItem().toString());

                for (int i = 0; i <= banks.length-1; i++){
                    if (banks[i].getName().equals(spinner.getSelectedItem().toString())){
                        Add_Client.putExtra("START", banks[i].getCard_start());
                        Add_Client.putExtra("END", banks[i].getCard_end());
                    }
                }
                startActivity(Add_Client);
            }
        });
        Button check = findViewById(R.id.button_check_atm);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Add_Client = new Intent(getBaseContext(), BankInvoice.class);
                Add_Client.putExtra("BANK_ID", spinner.getSelectedItem().toString());

                for (int i = 0; i <= banks.length-1; i++){
                    if (banks[i].getName().equals(spinner.getSelectedItem().toString())){
                        Add_Client.putExtra("START", banks[i].getCard_start());
                        Add_Client.putExtra("END", banks[i].getCard_end());
                    }
                }
                startActivity(Add_Client);
            }
        });
    }
}
