package com.example.jaybhatt.expensetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.example.jaybhatt.expensetracker.Model.Category;
import com.example.jaybhatt.expensetracker.Model.Expenditure;
import com.example.jaybhatt.expensetracker.Model.ExpenseType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NewExpenseActivity extends AppCompatActivity {

    EditText description, date, amount;
    Spinner category;
    RadioGroup type;
    private int year, month, day;
    ArrayList<Category> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        category = (Spinner) findViewById(R.id.spinner_expense_category);
        type = (RadioGroup) findViewById(R.id.radio_type);
        description = (EditText) findViewById(R.id.edit_text_expenditure_description);
        date = (EditText) findViewById(R.id.edit_text_expenditure_date);
        amount = (EditText) findViewById(R.id.edit_text_expenditure_amount);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        date.setText(String.format("%d-%d-%d", year, month + 1, day));

        Collections.addAll(list, Category.values());

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        category.setAdapter(adapter);
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

    public void saveExpense(View view) throws ParseException {

        String descriptionText = description.getText().toString();
        Float amountText = Float.valueOf(amount.getText().toString());
        String dateText = date.getText().toString();
        Category categoryText = list.get(category.getSelectedItemPosition());
        ExpenseType types[] = ExpenseType.values();
        ExpenseType typeText = types[type.getCheckedRadioButtonId()-1];
        Expenditure expenditure = new Expenditure(descriptionText, amountText, dateText, categoryText, typeText);
        expenditure.save();
        setResult(RESULT_OK);
        finish();
    }
}
