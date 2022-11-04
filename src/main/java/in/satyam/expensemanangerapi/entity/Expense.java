package in.satyam.expensemanangerapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="tbl_expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="expense_name")
    @NotBlank(message ="Expense Name must not be Null")
    @Size(min=3 ,message ="Expense Name must be atleast 3 characters")
    private String name;

    @NotBlank(message ="Expense Description must not be Null")
    private String description;

    @Column(name ="expense_amount")
    @NotNull(message = "Expense Amount must not be Null" )
    private BigDecimal amount;

    @NotBlank(message ="Expense Category must not be Null")
    private String category;

    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "Date must not be Null" )
    private Date date;

    @Column(name="created_at", nullable = false,updatable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp createdAt;

    @Column(name="updated_at", nullable = false,updatable = false)
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Timestamp updatedAt;


    //We use unidirectional mapping,as bidirectional loads all the expenses every time user is called
    //leading to performance issue,that can be used in Q&A type table

    //Join column :: As this column is not present originally in this schema
    //OnDelete,as when user is deleted,we want all expenses associated to also get deleted
    //Many to one relation,fetch when needed
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}



