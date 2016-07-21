package com.example.jaybhatt.expensetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaybhatt.expensetracker.Model.Budget;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewBudgetActivity extends AppCompatActivity {

    EditText title, description, date, amount, savings;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_budget);
        title = (EditText) findViewById(R.id.edit_text_budget_title);
        description = (EditText) findViewById(R.id.edit_text_budget_description);
        date = (EditText) findViewById(R.id.edit_text_budget_date);
        amount = (EditText) findViewById(R.id.edit_text_budget_amount);
        savings = (EditText) findViewById(R.id.edit_text_budget_saving);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        date.setText(String.format("%d-%d-%d", year, month+1, day));
    }

    public void show(View view) {
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.setText(String.format("%d-%d-%d", year, monthOfYear+1, dayOfMonth));
            }
        }, year, month, day);
        dpd.show();
    }

    public void save(View view) throws ParseException {
        String titleText = title.getText().toString();
        String descriptionText = description.getText().toString();
        Float amountText = Float.valueOf(amount.getText().toString());
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateText = date.getText().toString();
        Float savingsText = Float.valueOf(savings.getText().toString());

        Budget budget = new Budget(titleText, descriptionText, amountText, dateText, savingsText);
        budget.save();

        setResult(RESULT_OK);
        finish();
        //Toast.makeText(getBaseContext(), budget.toString(), Toast.LENGTH_LONG).show();
    }
}
