package com.mariolorian.wheelerdealer.repository;

import com.mariolorian.wheelerdealer.model.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    boolean existsByPesel(String Pesel);

}
