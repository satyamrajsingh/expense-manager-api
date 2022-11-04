package in.satyam.expensemanangerapi.service;

import in.satyam.expensemanangerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable page);

    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);

    Expense createExpense(Expense expense);

    Expense updateExpense(Long id,Expense expense);

    List<Expense> getExpenseByCategory(String category,Pageable page);

    List<Expense> getExpenseNameByKeyword(String keyword,Pageable page);

    List<Expense> getExpenseByDateRange(Date startDate , Date endDate, Pageable page);
}
