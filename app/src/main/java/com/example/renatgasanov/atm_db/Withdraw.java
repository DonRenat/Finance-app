package com.example.renatgasanov.atm_db;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

public class Withdraw extends AppCompatActivity implements OnClickListener{
    TextView clientdata;
    EditText atm_id_tv, amount, del_id_edit;
    Spinner spinner;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    Button btnAdd, btnRead, btnClear, btnDel;
    DatabaseHelper dbHelper;
    TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DatabaseHelper(this);

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

        clientdata = findViewById(R.id.client_data);
        atm_id_tv = findViewById(R.id.atm_id_edit);
        amount = findViewById(R.id.card_field_edit);
        del_id_edit = findViewById(R.id.del_id_edit);
        Long card = getIntent().getLongExtra("CARD", 0L);
        String name = getIntent().getStringExtra("NAME");
        String cc = String.valueOf(card);
        clientdata.setText("Клиент: " + name + "\n" + "Карта: " + cc);

        spinner = findViewById(R.id.atm_adr_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                String label = (parent.getItemAtPosition(position).toString());
                String[] splited = label.split("\\s+");
                int atm_id = db.getATMByADRandBANK(splited[0], splited[1]);
                atm_id_tv.setText(String.valueOf(atm_id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadSpinnerData();

        btnDatePicker=findViewById(R.id.btn_date);
        btnTimePicker=findViewById(R.id.btn_time);
        txtDate=findViewById(R.id.in_date);
        txtTime=findViewById(R.id.in_time);

        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnDatePicker) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(Withdraw.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                if (v == btnTimePicker) {

                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(Withdraw.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    txtTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, true);
                    timePickerDialog.show();
                }
            }
        };
        btnDatePicker.setOnClickListener(OCL);
        btnTimePicker.setOnClickListener(OCL);
    }
    private void loadSpinnerData() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> lables = db.getAllATM_ADRs();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    @Override
    public void onClick(View v) {
        //String bank_id = bank_id_edit.getText().toString();
        //Long start = getIntent().getLongExtra("START", 0L);
        //Long end = getIntent().getLongExtra("END", 0L);

        switch (v.getId()) {
            case R.id.btnAdd:
                Long card = getIntent().getLongExtra("CARD", 0L);
                int atm_id = Integer.parseInt(atm_id_tv.getText().toString());
                String name = getIntent().getStringExtra("NAME");
                String datetime = txtDate.getText().toString() + " " + txtTime.getText().toString();
                String MMM = txtDate.getText().toString();
                int MMMsub = Integer.parseInt(MMM.substring(5, 6));
                int fee;
                String bank = "";
                if (card >= 1000000000000000L  && card <= 1999999999999999L) {bank = "СберБанк";}
                if (card >= 2000000000000000L  && card <= 2999999999999999L) {bank = "РосБанк";}
                if (card >= 3000000000000000L  && card <= 3999999999999999L) {bank = "ХоумКредитБанк";}
                String label = (spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
                String[] splited = label.split("\\s+");

                int amountT = Integer.parseInt(amount.getText().toString());

                if (bank.equals(splited[1])) fee = 0; else { double d = amountT*0.012; fee = (int) d; }

                long RES = dbHelper.createT(new Transaction(card, atm_id, datetime, fee, amountT, MMMsub));
                if (RES == -1 || amountT > 500000) { log.append("Incorrect data! \n"); break; }
                //if (RES == -1 && String.valueOf(atm_id).length()==6) { atm_adr_edit.setError("Пустой адрес!"); log.append("Empty address! \n"); break; }
                else { log.append("Insert in Transactions: "); log.append("row inserted, ID = " + RES + "\n"); break; }
            case R.id.btnRead:
                log.append(dbHelper.readAllTRS());
                break;
            case R.id.btnClear:
                log.append("Clear transactions: ");
                log.append("deleted rows count = " + dbHelper.deleteAllTRS() + "\n");
                break;
            case R.id.btnDel:
                if (del_id_edit.getText().toString().equals("")){ log.append("Empty ID! \n"); del_id_edit.setError("Enter ID!"); break; }
                else {
                    int delID = Integer.parseInt(del_id_edit.getText().toString());
                    log.append("Deleted row from transactions with id="+delID+"\n");
                    dbHelper.deleteTR(delID);
                    break;}
        }
        dbHelper.close();
    }
}
