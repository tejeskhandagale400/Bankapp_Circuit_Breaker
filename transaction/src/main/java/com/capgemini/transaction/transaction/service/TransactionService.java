package com.capgemini.transaction.transaction.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.capgemini.transaction.transaction.entity.Transaction;

public interface TransactionService {

	Double deposit(int accountNumber, String transactionDetails, double currentBalance, double amount);

	Double withdraw(int accountNumber, String transactionDetails, double currentBalance, double amount);

	Double[] fundTransfer(Transaction sender, Transaction reciever);

	List<Transaction> listOfTransactions();

}
