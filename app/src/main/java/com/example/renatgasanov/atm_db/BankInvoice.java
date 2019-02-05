package com.example.renatgasanov.atm_db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;
import java.util.List;

public class BankInvoice extends AppCompatActivity {
    String TAG = "ClientInvoice";
    Button getBTN;
    TextView log;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_invoice);

        log = findViewById(R.id.text_log);
        log.setMovementMethod(new ScrollingMovementMethod());
        setNormalPicker();

        dbHelper = new DatabaseHelper(this);
        getBTN = findViewById(R.id.getBTN);
        getBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = findViewById(R.id.textView6);
                log = findViewById(R.id.text_log);
                if(t.getText().toString().equals("")) {log.append("Choose month and year!\n");} else
                {
                    String bank = getIntent().getStringExtra("BANK_ID");
                    log.append("BANK: " + bank + "\n");
                    List<Integer> A = dbHelper.getATMS(bank);
                    for(int i=0;i<A.size();i++){
                        //log.append(String.valueOf(A.get(i)) + "\n");
                        String M = t.getText().toString();
                        String MM = M.substring(0,1);
                        List<String> LLL = dbHelper.getBANKDATA(A.get(i), Integer.parseInt(MM));
                        log.append("ATM_ID: " + A.get(i) + "\n");
                        for(int j=0; j<LLL.size();j++){
                            log.append(LLL.get(j));
                        }
                    }

                    //log.append("CARD: " + card + "\n");
                    //String M = t.getText().toString();
                    //String MM = M.substring(0,1);//todo month length = 2
                    //List<Integer> res = dbHelper.getTRSbyDATA(card, Integer.parseInt(MM), 2019);
                    //List<String> res2 = dbHelper.getTRSbyDATA2(card, Integer.parseInt(MM), 2019);
                    //log.append(res);
                    //for(int i=0;i<res2.size();i++){
                      //  log.append(res2.get(i));
                    //}
                    //log.append("Итого выданно: ");
                    //int total = 0;
                    //for(int i=0;i<res.size();i++){
                      //  total += res.get(i);
                    //}
                    //log.append(total + "\n");
                    //List<Integer> res3 = dbHelper.getTRSbyDATA3(card, Integer.parseInt(MM), 2019);
                    //log.append("Итого коммисий: ");
                    //int totalС = 0;
                    //for(int i=0;i<res3.size();i++){
                      //  totalС += res3.get(i);
                    //}
                    //log.append(totalС + "\n");
                }
            }
        });
    }
    private void setNormalPicker() {
        setContentView(R.layout.activity_client_invoice);
        final Calendar today = Calendar.getInstance();
        findViewById(R.id.month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(BankInvoice.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        //Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
                        //Toast.makeText(ClientInvoice.this, "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                        TextView t = findViewById(R.id.textView6); t.setText(selectedMonth+1 + "-" + selectedYear);
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(2017)
                        .setActivatedYear(2019)
                        .setMaxYear(2020)
                        .setMinMonth(Calendar.JANUARY)
                        .setTitle("Выберите дату")
                        .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                        // .setMaxMonth(Calendar.OCTOBER)
                        // .setYearRange(1890, 1890)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        // .showYearOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {
                                Log.d(TAG, "Selected month : " + selectedMonth);
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {
                                Log.d(TAG, "Selected year : " + selectedYear);
                            }
                        })
                        .build()
                        .show();

            }
        });
    }
}
