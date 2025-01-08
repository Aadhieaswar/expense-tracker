package com.aadhie.expensetracker.repositories;

import com.aadhie.expensetracker.models.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpenseItem, String> {
    // find expenses by the userId
    List<ExpenseItem> findByUserId(String userId);

    // get all the month and year combinations when the user has added expenses
    @Query(value="SELECT DISTINCT EXTRACT(YEAR FROM date) AS year, EXTRACT(MONTH FROM date) AS month FROM expense_item WHERE user_id = :userId ORDER BY year DESC, month DESC;", nativeQuery = true)
    List<Object[]> expensesDateAndYearListForUser(@Param("userId") String uerId);

    @Query("SELECT COUNT(e) as count, SUM(e.price) as total FROM ExpenseItem e WHERE e.userId = :userId AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
    Object[] calculateTotalExpensesByMonth(@Param("userId") String userId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT e.userId, e.item, e.description, e.price, e.category, e.date FROM ExpenseItem e WHERE e.userId = :userId AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
    List<Object[]> findByUserIdForMonthAndYear(@Param("userId") String userId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT e.category, COUNT(e), SUM(e.price), AVG(e.price) FROM ExpenseItem e WHERE e.userId = :userId AND MONTH(e.date) = :month AND YEAR(e.date) = :year GROUP BY e.category")
    List<Object[]> getUserExpensesSummaryByCategory(@Param("userId") String userId, @Param("month") int month, @Param("year") int year);
}
