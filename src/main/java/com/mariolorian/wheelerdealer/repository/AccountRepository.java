package com.mariolorian.wheelerdealer.repository;

import com.mariolorian.wheelerdealer.model.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    boolean existsById(String id);

    Optional<Account> findById(String id);

}
