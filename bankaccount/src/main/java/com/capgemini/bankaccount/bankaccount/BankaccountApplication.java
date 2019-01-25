package com.capgemini.bankaccount.bankaccount;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.capgemini.bankaccount.bankaccount.entity.CurrentAccount;
import com.capgemini.bankaccount.bankaccount.entity.SavingsAccount;
import com.capgemini.bankaccount.bankaccount.repository.AccountRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class BankaccountApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(BankaccountApplication.class, args);
	}

	@Bean
	public CommandLineRunner savingData(AccountRepository accountRepository)
	{
		return (args)->{
		accountRepository.save(new SavingsAccount(101,"Deepika", 10010.00,true));
		accountRepository.save(new SavingsAccount(102,"Tejas", 2322.00,false));
		accountRepository.save(new SavingsAccount(103,"Ankita", 12342.00,true));
		accountRepository.save(new SavingsAccount(104,"Ajay", 35432.00,false));
		accountRepository.save(new SavingsAccount(105,"Shubham", 6543.00,true));
		accountRepository.save(new SavingsAccount(106,"Rohan", 2123.00,false));
		accountRepository.save(new CurrentAccount(107,"Vijay", 443.00,102.00));
		accountRepository.save(new CurrentAccount(108,"Dinanath", 23323.00,1214.00));
		accountRepository.save(new CurrentAccount(109,"Chauhan", 2344.00,100.00));
		accountRepository.save(new CurrentAccount(110,"Joker", 6454.00,8441.00));
		accountRepository.save(new CurrentAccount(111,"Ankganiti", 234532.00,410.00));
		
		};
	}


	
}
