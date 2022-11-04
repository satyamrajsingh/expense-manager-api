package in.satyam.expensemanangerapi.service;

import in.satyam.expensemanangerapi.entity.Expense;
import in.satyam.expensemanangerapi.exception.ResourceNotFoundException;
import in.satyam.expensemanangerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserService userService;

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(),page);
    }

    @Override
    public Expense getExpenseById(Long id) {
       Optional<Expense> expense= expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
       if(expense.isPresent()) return expense.get();
       throw new ResourceNotFoundException("Expense not found for id "+ id);
    }

    @Override
    public void deleteExpenseById(Long id) {
         Expense expense = getExpenseById(id);
         expenseRepository.delete(expense);
    }

    @Override
    public Expense createExpense(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense expenseOld= getExpenseById(id);

        expenseOld.setName(expense.getName() == null? expenseOld.getName() : expense.getName());
        expenseOld.setAmount(expense.getAmount() == null?expenseOld.getAmount() : expense.getAmount());
        expenseOld.setCategory(expense.getCategory() == null?expenseOld.getCategory():expense.getCategory());
        expenseOld.setDescription(expense.getDescription() == null?expenseOld.getDescription():expense.getDescription());
        expenseOld.setDate(expense.getDate() == null?expenseOld.getDate():expense.getDate());

        return expenseRepository.save(expenseOld);
    }

    @Override
    public List<Expense> getExpenseByCategory(String category, Pageable page) {
         return expenseRepository.getExpenseByUserIdAndCategory(userService.getLoggedInUser().getId(),category,page).toList();
    }

    @Override
    public List<Expense> getExpenseNameByKeyword(String keyword, Pageable page) {
         return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),keyword,page).toList();
    }

    @Override
    public List<Expense> getExpenseByDateRange(Date startDate, Date endDate, Pageable page) {
        if(startDate == null){
          startDate = new Date(0);
        }if(endDate == null){
          endDate = new Date(System.currentTimeMillis());
        }

        return expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),startDate,endDate,page).toList();

    }
}
