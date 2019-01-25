package com.capgemini.bankaccount.bankaccount.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.bankaccount.bankaccount.entity.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, Integer>{

	Optional<Account> findByAccountHolderName(String accountHolderName);
	 
}
