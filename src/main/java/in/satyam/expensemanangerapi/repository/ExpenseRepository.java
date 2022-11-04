package in.satyam.expensemanangerapi.repository;

import in.satyam.expensemanangerapi.entity.Expense;
import in.satyam.expensemanangerapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

      //Behind the scene,DataJpa will use this query ::
      //Select * from tbl_expenses where category=?
      Page<Expense> getExpenseByCategory(String category, Pageable page);

      //Select * from tbl_expenses where name like '%keyword%'
      Page<Expense> findByNameContaining(String keyword,Pageable page);

      //Select * from tbl_expenses where date between 'startDate' and 'endDate'
      Page<Expense> findByDateBetween(Date startDate ,Date endDate,Pageable page);

      Page<Expense> findByUserId(Long userId,Pageable page);

      //Select * from tbl_expenses where userId=? and id=?
      Optional<Expense> findByUserIdAndId(Long userId, Long id);

      Page<Expense> getExpenseByUserIdAndCategory(Long userId,String category, Pageable page);


      Page<Expense> findByUserIdAndNameContaining(Long userId,String keyword,Pageable page);


      Page<Expense> findByUserIdAndDateBetween(Long userId,Date startDate ,Date endDate,Pageable page);
}
