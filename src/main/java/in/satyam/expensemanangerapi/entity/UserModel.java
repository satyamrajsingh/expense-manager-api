package in.satyam.expensemanangerapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class UserModel {
    //Validations are added here as usermodel is passed in requestBody

    @NotBlank(message ="User Name must not be Null")
    @Size(min=3 ,message ="User Name must be atleast 3 characters")
    private String name;

    @NotBlank(message ="Email must not be Null")
    @Email(message = "Email must be Valid")
    private String email;

    @NotBlank(message = "Please enter password")
    @Size(min=5,message = "Password should be atleast 5 characters")
    private String password;

    @NotNull(message = "Age Must not be null")
    private Long age;

}
