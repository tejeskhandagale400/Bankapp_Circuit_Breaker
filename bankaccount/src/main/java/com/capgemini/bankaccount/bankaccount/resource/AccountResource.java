package com.capgemini.bankaccount.bankaccount.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.bankaccount.bankaccount.entity.Account;
import com.capgemini.bankaccount.bankaccount.entity.SavingsAccount;
import com.capgemini.bankaccount.bankaccount.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public void addAccount(@RequestBody SavingsAccount savingsAccount)
	{
		accountService.addNewAccount(savingsAccount);
 	}
	
	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts()
	{
		List<Account> account = accountService.getAllAccounts();
		return new ResponseEntity<>(account,HttpStatus.OK);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountById(@PathVariable Integer accountNumber)
	{
		Optional<Account> account=accountService.getAccountById(accountNumber);
		if(account.get()==null)
		{
			return new ResponseEntity<>(account.get(),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(account.get(),HttpStatus.OK);
	}
	
	@GetMapping("/{accountNumber}/balance")
	public Double getAccountBalance(@PathVariable Integer accountNumber)
	{
		Optional<Account> account=accountService.getAccountById(accountNumber);
		 
		return account.get().getCurrentBalance();
	}
	
	@DeleteMapping("/{accountNumber}")
	public void  closeAccount(@PathVariable int accountNumber)
	{
		 accountService.closeAccount(accountNumber);
		
	}
	
	@PutMapping("/{accountNumber}")
	public void  updateAccountBalance(@PathVariable Integer accountNumber , @RequestParam Double balance)
	{
 		 accountService.updateAccountBalance(accountNumber,balance);
		
	}
}
