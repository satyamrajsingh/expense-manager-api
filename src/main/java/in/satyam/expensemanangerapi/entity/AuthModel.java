package in.satyam.expensemanangerapi.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class AuthModel {

    @NotBlank(message ="Email must not be Null")
    @Email(message = "Email must be Valid")
    private String email;

    @NotBlank(message = "Please enter password")
    @Size(min=5,message = "Password should be atleast 5 characters")
    private String password;
}
