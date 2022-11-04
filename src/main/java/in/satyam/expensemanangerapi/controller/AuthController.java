package in.satyam.expensemanangerapi.controller;

import in.satyam.expensemanangerapi.entity.AuthModel;
import in.satyam.expensemanangerapi.entity.JwtResponse;
import in.satyam.expensemanangerapi.entity.User;
import in.satyam.expensemanangerapi.entity.UserModel;
import in.satyam.expensemanangerapi.Security.CustomUserDetailsService;
import in.satyam.expensemanangerapi.service.UserService;
import in.satyam.expensemanangerapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;

    private void authenticate(String email,String password) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
        }catch(DisabledException e){
           throw new Exception("User Disabled");
        }catch(BadCredentialsException e){
           throw new Exception("Bad Credentials");
        }
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception{

        authenticate(authModel.getEmail(),authModel.getPassword());
        //After authentication,we need to generate Jwt token
        //TO generate token ,we need user Details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());

        //we need to pass this userDetails to generateToken method
        final String token=jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);

    }


    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserModel user){
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }

}
