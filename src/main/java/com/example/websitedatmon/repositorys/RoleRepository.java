package com.example.websitedatmon.repositorys;

import com.example.websitedatmon.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleById(int id);
}
