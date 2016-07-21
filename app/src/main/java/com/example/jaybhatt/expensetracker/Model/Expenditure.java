package com.example.jaybhatt.expensetracker.Model;

import com.orm.SugarRecord;

import java.util.List;


/**
 * Created by Jay Bhatt on 02-07-2016.
 */
public class Expenditure extends SugarRecord{

    String description;
    Float amount;
    String date;
    Category category;
    ExpenseType expenseType;

    public Expenditure() {
    }

    public Expenditure(String description, Float amount, String date, Category category, ExpenseType expenseType) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.expenseType = expenseType;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    @Override
    public String toString() {
        return "Expenditure{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", category=" + category +
                ", expenseType=" + expenseType +
                '}';
    }

    public Float getTotalUnpaidExpenditure()
    {
        List<Expenditure> unpaidExpenditures = Expenditure.find(Expenditure.class, "expense_type = ?", "unpaid");
        Float sum = 0.0f;
        for(int i = 0; i < unpaidExpenditures.size(); i++)
        {
            sum += unpaidExpenditures.get(i).getAmount();
        }
        return sum;
    }

    public Float getTotalPaidExpenditure()
    {
        List<Expenditure> paidExpenditures = Expenditure.find(Expenditure.class, "expense_type = ?", "paid");
        Float sum = 0.0f;
        for(int i = 0; i < paidExpenditures.size(); i++)
        {
            sum += paidExpenditures.get(i).getAmount();
        }
        return sum;
    }
}
