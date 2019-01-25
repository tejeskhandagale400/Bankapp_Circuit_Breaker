package com.capgemini.bankaccount.bankaccount.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.capgemini.bankaccount.bankaccount.entity.Account;


public interface AccountService {

	List<Account> getAllAccounts();

	void addNewAccount(Account account);

	Optional<Account> getAccountById(int accountNumber);

	void closeAccount(int accountNumber);

	Optional<Account> getAccountByName(String accountHolderName);

	void updateAccountBalance(int accountNumber, Double balance);

}