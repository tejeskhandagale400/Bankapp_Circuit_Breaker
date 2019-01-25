package com.capgemini.bankaccount.bankaccount.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.bankaccount.bankaccount.entity.Account;
import com.capgemini.bankaccount.bankaccount.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	/* (non-Javadoc)
	 * @see com.capgemini.bankaccount.bankaccount.service.AccountService#getAllAccount()
	 */
	@Override
	public List<Account> getAllAccounts()
	{
		return accountRepository.findAll();
	}
	 
	/* (non-Javadoc)
	 * @see com.capgemini.bankaccount.bankaccount.service.AccountService#addNewAccount(com.capgemini.bankaccount.bankaccount.entity.Account)
	 */
	@Override
	public void addNewAccount(Account account)
	{
		 accountRepository.save(account);
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.bankaccount.bankaccount.service.AccountService#getAccountById(int)
	 */
	@Override
	public Optional<Account>  getAccountById(int accountNumber)
	{
		return accountRepository.findById(accountNumber);
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.bankaccount.bankaccount.service.AccountService#closeAccount(int)
	 */
	@Override
	public void  closeAccount(int accountNumber)
	{
		 accountRepository.deleteById(accountNumber);
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.bankaccount.bankaccount.service.AccountService#updateAccountBalance(com.capgemini.bankaccount.bankaccount.entity.Account)
	 */
 
	
	/* (non-Javadoc)
	 * @see com.capgemini.bankaccount.bankaccount.service.AccountService#getAccountByName(java.lang.String)
	 */
	@Override
	public Optional<Account>  getAccountByName(String accountHolderName)
	{
		return accountRepository.findByAccountHolderName(accountHolderName);
	}

	@Override
	public void updateAccountBalance(int accountNumber, Double balance) {

		Optional<Account> account = getAccountById(accountNumber);
		account.get().setCurrentBalance(balance);
		accountRepository.save(account.get());
		
	}
	
}
