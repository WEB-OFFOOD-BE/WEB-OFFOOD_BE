package com.example.websitedatmon.services;

import com.example.websitedatmon.domain.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role findRoleById(int id);
}
