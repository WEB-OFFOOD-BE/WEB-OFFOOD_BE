package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.domain.Role;
import com.example.websitedatmon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(int id);
    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email,String password);
    User findUserByUserNameAndPassword(String userName,String password);
    @Query(value = "SELECT * from  user  WHERE role_id = 3 ",nativeQuery = true)
    List<User> listEmployee();

    @Query(value = "SELECT * from  user  WHERE role_id = 2 ",nativeQuery = true)
    List<User> listCustomer();

    User save(User user);

    @Modifying
    @Transactional
    @Query(value = "Update user SET  email = ? , full_name = ?, phone_number = ? , user_name = ? WHERE id = ?",nativeQuery = true)
    int update(String email , String fullname, String phonenumber, String username, int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user WHERE id = ?",nativeQuery = true)
    int delete( int id);
}
