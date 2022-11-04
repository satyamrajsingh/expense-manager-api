package in.satyam.expensemanangerapi.Security;

import in.satyam.expensemanangerapi.entity.User;
import in.satyam.expensemanangerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    //this overrides a custom method which loadsUsername a(here email,throws exception,if not present)
    //and returns that username in UserDetails class(Defined in spring).
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser=userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found for Email:"+ email)
        );
        return new org.springframework.security.core.userdetails.User(
                existingUser.getEmail(),existingUser.getPassword(),new ArrayList<>()
        );
    }
}
