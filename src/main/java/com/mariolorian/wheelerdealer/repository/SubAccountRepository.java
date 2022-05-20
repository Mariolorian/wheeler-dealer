package com.mariolorian.wheelerdealer.repository;

import com.mariolorian.wheelerdealer.model.entity.SubAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubAccountRepository extends CrudRepository<SubAccount, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM SUB_ACCOUNTS sa " +
                    "JOIN ACCOUNTS a ON sa.fk_account = a.id " +
                    "WHERE a.id = ?1")
    List<SubAccount> findAllByAccountId(@Param("id") String id);

}
