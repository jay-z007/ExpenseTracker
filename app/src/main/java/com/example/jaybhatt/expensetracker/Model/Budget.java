package com.example.jaybhatt.expensetracker.Model;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Jay Bhatt on 02-07-2016.
 */
public class Budget extends SugarRecord{

    String title;
    String description;
    Float amount;
    String date;
    Float saving;

    public Budget() {
    }

    public Budget(String title, String description, Float amount, String date, Float saving) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.saving = saving;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getSaving() {
        return saving;
    }

    public void setSaving(Float saving) {
        this.saving = saving;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", saving=" + saving +
                '}';
    }

    public Float getTotalBudget(){
        List<Budget> budgets = Budget.listAll(Budget.class);
        Float sum = 0.0f;

        for(int i = 0; i < budgets.size(); i++)
            sum += budgets.get(i).getAmount();

        return sum;
    }
}
