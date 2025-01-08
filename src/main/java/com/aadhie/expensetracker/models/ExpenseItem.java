package com.aadhie.expensetracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.UUID;

@Entity
public class ExpenseItem extends BaseEntity {

    @Id
    private String id;

    @NotEmpty
    private String item;

    private String description;

    private double price;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    private Date date;

    public String userId;

    public ExpenseItem() {}

    public ExpenseItem(String userId, String item, String description, double price, Category category, Date date) {
        this.setUserId(userId);
        this.setItem(item);
        this.setDescription(description);
        this.setPrice(price);
        this.setCategory(category);
        this.setDate(date);
    }

    @PrePersist
    public void generateId() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
