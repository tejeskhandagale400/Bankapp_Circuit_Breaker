package com.capgemini.transaction.transaction;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.capgemini.transaction.transaction.entity.Transaction;
import com.capgemini.transaction.transaction.entity.TransactionType;
import com.capgemini.transaction.transaction.repository.TransactionRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/*
	 * @Bean public CommandLineRunner savingTransactions(TransactionRepository
	 * transactionRepository) { return (args)->{ transactionRepository.save(new
	 * Transaction(101, 4141.00, TransactionType.DEPOSIT, "Atm"));
	 * transactionRepository.save(new Transaction(102, 222.00,
	 * TransactionType.DEPOSIT, "Paytm")); transactionRepository.save(new
	 * Transaction(103, 4141.00, TransactionType.DEPOSIT, "Atm"));
	 * transactionRepository.save(new Transaction(104, 555.00,
	 * TransactionType.WITHDRAW, "Neft")); transactionRepository.save(new
	 * Transaction(105, 98.00, TransactionType.DEPOSIT, "Atm"));
	 * transactionRepository.save(new Transaction(106, 787.00,
	 * TransactionType.WITHDRAW, "Timepass")); transactionRepository.save(new
	 * Transaction(107, 66.00, TransactionType.DEPOSIT, "Atm"));
	 * transactionRepository.save(new Transaction(108, 945.00,
	 * TransactionType.WITHDRAW, "Freee")); transactionRepository.save(new
	 * Transaction(109, 213.00, TransactionType.DEPOSIT, "Atm"));
	 * transactionRepository.save(new Transaction(110, 4141.00,
	 * TransactionType.WITHDRAW, "Salary")); transactionRepository.save(new
	 * Transaction(111, 4141.00, TransactionType.DEPOSIT, "Atm"));
	 * 
	 * 
	 * };
	 * 
	 * }
	 */
}

