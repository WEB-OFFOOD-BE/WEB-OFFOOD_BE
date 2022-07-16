package com.example.websitedatmon.services;

import com.example.websitedatmon.domain.Role;
import com.example.websitedatmon.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User save(User user);
    User findUserById(int id);

    User findUserByEmail(String email);
    User findUserByEmailAndPassword(String email,String password);
    User findUserByUserNameAndPassword(String userName,String password);
    int delete(int id);
    int update(String email , String fullname, String phonenumber, String username, int id);

    List<User> listEmployee();
    List<User> listCustomer();
}
