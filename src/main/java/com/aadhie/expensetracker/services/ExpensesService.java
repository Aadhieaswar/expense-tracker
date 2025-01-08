package com.aadhie.expensetracker.services;

import com.aadhie.expensetracker.models.Category;
import com.aadhie.expensetracker.models.ExpenseItem;
import com.aadhie.expensetracker.repositories.CategoriesRepository;
import com.aadhie.expensetracker.repositories.ExpensesRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpensesService {
    private final ExpensesRepository expensesRepository;
    private final CategoriesRepository categoriesRepository;

    public ExpensesService(ExpensesRepository expensesRepository, CategoriesRepository categoriesRepository) {
        this.expensesRepository = expensesRepository;
        this.categoriesRepository = categoriesRepository;
    }

    public List<ExpenseItem> getAllExpenses() {
        return expensesRepository.findAll();
    }

    public ExpenseItem saveExpense(ExpenseItem expense) {
        return expensesRepository.save(expense);
    }

    public ExpenseItem getExpenseById(String id) {
        Optional<ExpenseItem> expenseItem = expensesRepository.findById(id);
        return expenseItem.orElseThrow(() -> new RuntimeException("Expense item not found with id " + id));
    }

    public List<ExpenseItem> getExpensesForUser(String userId) {
        return expensesRepository.findByUserId(userId);
    }

    public List<Object[]> getMonthAndYearForUserExpenses(String userId) {
        return expensesRepository.expensesDateAndYearListForUser(userId);
    }

    public Object[] getSummaryForUserByMonthAndYear(String userId, int month, int year) {
        return expensesRepository.calculateTotalExpensesByMonth(userId, month, year);
    }

    public List<ExpenseItem> getExpensesForUserByMonthAndYear(String userId, int month, int year) {
        List<Object[]> expenses = expensesRepository.findByUserIdForMonthAndYear(userId, month, year);
        return expenses.stream().map(record -> new ExpenseItem(
                (String) record[0],
                (String) record[1],
                (String) record[2],
                (Double) record[3],
                (Category) record[4],
                (Date) record[5]
        )).collect(Collectors.toList());
    }
}
