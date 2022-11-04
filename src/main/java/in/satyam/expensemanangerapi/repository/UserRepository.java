package in.satyam.expensemanangerapi.repository;

import in.satyam.expensemanangerapi.entity.Expense;
import in.satyam.expensemanangerapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

      Boolean existsByEmail(String email);

      Optional<User> findByEmail(String email);
}
