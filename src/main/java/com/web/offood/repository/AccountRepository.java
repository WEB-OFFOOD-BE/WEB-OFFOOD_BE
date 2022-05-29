package com.web.offood.repository;

import com.web.offood.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Account findByUsername(String username);

    Account findByEmail(String email);
}
