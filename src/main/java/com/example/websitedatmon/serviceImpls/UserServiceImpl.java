package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.domain.Role;
import com.example.websitedatmon.domain.User;
import com.example.websitedatmon.repositorys.UserRepository;
import com.example.websitedatmon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }



    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email,password);
    }

    @Override
    public User findUserByUserNameAndPassword(String userName, String password) {
        return userRepository.findUserByUserNameAndPassword(userName,password);
    }

    @Override
    public int delete(int id) {
        return userRepository.delete(id);
    }

    @Override
    public int update(String email , String fullname, String phonenumber, String username, int id) {
        return userRepository.update(email,fullname,phonenumber,username,id);
    }

    @Override
    public List<User> listEmployee() {
        return userRepository.listEmployee();
    }

    @Override
    public List<User> listCustomer() {
        return userRepository.listCustomer();
    }
}
