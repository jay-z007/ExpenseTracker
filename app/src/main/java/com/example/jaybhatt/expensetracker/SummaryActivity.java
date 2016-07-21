package com.example.jaybhatt.expensetracker;

import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jaybhatt.expensetracker.Model.Expenditure;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        barChart = (BarChart) findViewById(R.id.expense_bar_chart);

        List<Expenditure> expenses = Expenditure.listAll(Expenditure.class);

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        int i = 0;
        for (Expenditure ex : expenses)
        {
            valueSet1.add(new BarEntry(ex.getAmount(), i++));
            labels.add(ex.getCategory().name());
        }

        BarDataSet barDataSet = new BarDataSet(valueSet1, "Expense per category");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(labels, barDataSet);
        barChart.setData(barData);
        barChart.setDescription("Money Spent in different ways");
        barChart.animateXY(1000, 1000);
        barChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            //Toast.makeText(this, "Save Clicked", Toast.LENGTH_LONG).show();
            Calendar c = Calendar.getInstance();
            String filename = "expense_"+c.get(Calendar.DATE)+"_"+c.get(Calendar.MONTH)+"_"+c.get(Calendar.YEAR);
            //String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
            String expenseTracker = "ExpenseTracker";
            File imageDir = new File(getExternalCacheDir(),expenseTracker);
            imageDir.mkdirs();

            boolean flag =  barChart.saveToPath(filename, imageDir.toString().substring(19));
            if(flag)
                Toast.makeText(this, "Image saved to "+ imageDir, Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
