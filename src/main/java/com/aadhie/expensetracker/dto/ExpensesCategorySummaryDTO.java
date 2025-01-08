package com.aadhie.expensetracker.dto;

import com.aadhie.expensetracker.models.Category;

public class ExpensesCategorySummaryDTO {
    private Category category;

    private long count;

    private double total;

    private double average;

    public ExpensesCategorySummaryDTO(Category category, long count, double total, double average) {
        this.category = category;
        this.count = count;
        this.total = total;
        this.average = average;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
