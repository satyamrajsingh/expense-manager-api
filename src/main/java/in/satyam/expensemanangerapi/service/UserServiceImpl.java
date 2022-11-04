package in.satyam.expensemanangerapi.service;

import in.satyam.expensemanangerapi.entity.User;
import in.satyam.expensemanangerapi.entity.UserModel;
import in.satyam.expensemanangerapi.exception.ItemAlreadyPresentException;
import in.satyam.expensemanangerapi.exception.ResourceNotFoundException;
import in.satyam.expensemanangerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bcryptEncoder;

    @Override
    public User createUser(UserModel user) {

        if(userRepository.existsByEmail(user.getEmail())){
           throw new  ItemAlreadyPresentException("User is already registered with Email:"+ user.getEmail());
        }

        User newUser = new User();
        BeanUtils.copyProperties(user,newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User findById() {
        Long userId=getLoggedInUser().getId();
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given Id not found"));
    }

    @Override
    public User updateUser(User user) {
        User userOld= findById();
        userOld.setAge(user.getAge() == null?userOld.getAge():user.getAge());
        userOld.setName(user.getName() == null?userOld.getName():user.getName());
        userOld.setEmail(user.getEmail()== null?userOld.getEmail():user.getEmail());
        userOld.setPassword(user.getPassword()==null?userOld.getPassword(): bcryptEncoder.encode(user.getPassword()));

        return userRepository.save(userOld);
    }

    @Override
    public void deleteUser() {
         User user=findById();
         userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User with email doesn't exist:"+email)
        );
    }
}
