package in.satyam.expensemanangerapi.controller;

import in.satyam.expensemanangerapi.entity.Expense;
import in.satyam.expensemanangerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    //get All Expenses
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page){
        return expenseService.getAllExpenses(page).toList();
    }

    //get Expenses By Id
    //pathVariable annotation is used to capture path variable
    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id){
        return expenseService.getExpenseById(id);
    }

    //Delete Expenses by Id
    //RequestParam annotation is used to capture query params
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")//using Query String
    public void deleteExpenseById(@RequestParam("id") Long id){
         expenseService.deleteExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense createExpense(@Valid @RequestBody Expense expense){
        return expenseService.createExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpense(@RequestBody Expense expense,@PathVariable("id") Long id){
        return expenseService.updateExpense(id,expense);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpenseByCategory(@RequestParam("category") String category,Pageable page){
        return expenseService.getExpenseByCategory(category,page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getExpenseByName(@RequestParam("keyword") String keyword,Pageable page){
        return expenseService.getExpenseNameByKeyword(keyword,page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getExpenseByDateRange(@RequestParam(value = "startDate",required = false) Date startDate, @RequestParam(value = "endDate",required = false) Date endDate, Pageable page){
        return expenseService.getExpenseByDateRange(startDate,endDate,page);
    }
}
