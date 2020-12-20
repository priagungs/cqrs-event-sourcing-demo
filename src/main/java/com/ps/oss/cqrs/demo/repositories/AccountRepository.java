package com.ps.oss.cqrs.demo.repositories;

import com.ps.oss.cqrs.demo.models.read.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface AccountRepository extends MongoRepository<Account, String> {

  Optional<Account> findByUsername(String username);

}
