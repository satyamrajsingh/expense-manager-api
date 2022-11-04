package in.satyam.expensemanangerapi.service;

import in.satyam.expensemanangerapi.entity.User;
import in.satyam.expensemanangerapi.entity.UserModel;


public interface UserService {

    User createUser(UserModel user);

    User findById();

    User updateUser(User user);

    void deleteUser();

    User getLoggedInUser();
}
