package com.aadhie.expensetracker.controllers;

import com.aadhie.expensetracker.dto.ExpenseItemDTO;
import com.aadhie.expensetracker.models.Category;
import com.aadhie.expensetracker.models.ExpenseItem;
import com.aadhie.expensetracker.services.CategoryService;
import com.aadhie.expensetracker.services.ExpensesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpensesController {

    private final ExpensesService expensesService;
    private final CategoryService categoryService;

    public ExpensesController(ExpensesService expensesService, CategoryService categoryService) {
        this.expensesService = expensesService;
        this.categoryService = categoryService;
    }

    @GetMapping("/expenses")
    public List<ExpenseItem> getAllExpenses(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid");

        try {
            return expensesService.getExpensesForUser(uid);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/expenses")
    public ExpenseItem createExpense(HttpServletRequest request, @RequestBody @Valid ExpenseItemDTO expense) {
        if (expense == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Category category = categoryService.getCategoryById(expense.getCategoryId());

        String uid = (String) request.getAttribute("uid");

        try {
            ExpenseItem expenseItem = new ExpenseItem(
                uid,
                expense.getItem(),
                expense.getDescription(),
                expense.getPrice(),
                category,
                expense.getDate()
            );

            // add expense to the db
            return expensesService.saveExpense(expenseItem);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expenses/{month}/{year}")
    public List<ExpenseItem> getExpensesForUserByMonthAndYear(HttpServletRequest request, @PathVariable("month") int month, @PathVariable("year") int year) {
        String uid = (String) request.getAttribute("uid");

        try {
            return expensesService.getExpensesForUserByMonthAndYear(uid, month, year);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expenses/months-years")
    public List<Object[]> getYearAndMonthForUserExpenses(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid");

        try {
            return expensesService.getMonthAndYearForUserExpenses(uid);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expenses/summary/{month}/{year}")
    public Object[] getExpensesStatsByYearAndMonth(HttpServletRequest request, @PathVariable("month") int month, @PathVariable("year") int year) {
        String uid = (String) request.getAttribute("uid");

        try {
            return expensesService.getSummaryForUserByMonthAndYear(uid, month, year);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expenses/{id}")
    public ExpenseItem getExpenseById(@PathVariable("id") String id) {
        ExpenseItem expense;

        try
        {
            expense = expensesService.getExpenseById(id);
        }
        catch (RuntimeException ex)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return expense;
    }
}
