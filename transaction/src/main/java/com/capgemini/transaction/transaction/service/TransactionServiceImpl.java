package com.capgemini.transaction.transaction.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capgemini.transaction.transaction.entity.Transaction;
import com.capgemini.transaction.transaction.entity.TransactionType;
import com.capgemini.transaction.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Double deposit(int accountNumber, String transactionDetails, double currentBalance, double amount) {
		Transaction transaction = new Transaction(accountNumber, amount, TransactionType.DEPOSIT, transactionDetails);
		currentBalance += amount;
		transaction.setCurrentBalance(currentBalance);
		transaction.setTransactionDate(LocalDateTime.now());
		transactionRepository.save(transaction);
		return currentBalance;

	}

	public Double withdraw(int accountNumber, String transactionDetails, double currentBalance, double amount) {
		Transaction transaction = new Transaction(accountNumber, amount, TransactionType.WITHDRAW, transactionDetails);
		if (currentBalance > amount) {
			currentBalance -= amount;
		}
		transaction.setCurrentBalance(currentBalance);
		transaction.setTransactionDate(LocalDateTime.now());
		transactionRepository.save(transaction);
		return currentBalance;

	}

	@Override
	public Double[] fundTransfer(Transaction sender, Transaction reciever) {
		reciever.setTransactionDetails(sender.getTransactionDetails());
		Double senderCurrentbal = withdraw(sender.getAccountNumber(), sender.getTransactionDetails(),
				sender.getCurrentBalance(), sender.getAmount());
		Double receiverCurrentBal = deposit(reciever.getAccountNumber(), reciever.getTransactionDetails(),
				reciever.getCurrentBalance(), sender.getAmount());
		return new Double[] { senderCurrentbal, receiverCurrentBal };
	}

	 

	public List<Transaction> listOfTransactions() {
		
 		return  transactionRepository.findAll();
	}
}
