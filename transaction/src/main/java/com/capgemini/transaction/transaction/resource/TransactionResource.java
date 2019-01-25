package com.capgemini.transaction.transaction.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.transaction.transaction.entity.CurrentDataSet;
import com.capgemini.transaction.transaction.entity.Transaction;
import com.capgemini.transaction.transaction.service.TransactionService;

@RestController
@RequestMapping("/{transactions}")
public class TransactionResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://bankaccount/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = transactionService.deposit(transaction.getAccountNumber(),
				transaction.getTransactionDetails(), currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://bankaccount/accounts/" + transaction.getAccountNumber() + "?balance=" + updateBalance, null);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<CurrentDataSet> listOfTransactions(){
		CurrentDataSet currentDataSet =new CurrentDataSet();
		List<Transaction> transactions = transactionService.listOfTransactions();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<>(currentDataSet, HttpStatus.OK);
	}
	

	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://bankaccount/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = transactionService.withdraw(transaction.getAccountNumber(),
				transaction.getTransactionDetails(), currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://bankaccount/accounts/" + transaction.getAccountNumber() + "?balance=" + updateBalance, null);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PostMapping("/transfer")
	public ResponseEntity<Transaction> fundtransfer(@RequestBody Transaction sender,
			@RequestParam Integer receiverAccountNumber) {
		System.out.println(receiverAccountNumber);
		Double[] updtaedbalance = new Double[2];
		Transaction reciever = new Transaction();
		ResponseEntity<Double> entitySender = restTemplate
				.getForEntity("http://bankaccount/accounts/" + sender.getAccountNumber() + "/balance", Double.class);
		Double senderCurrentBalance = entitySender.getBody();
		ResponseEntity<Double> entityReciever = restTemplate
				.getForEntity("http://bankaccount/accounts/" + receiverAccountNumber + "/balance", Double.class);
		Double recieverCurrentBalance = entityReciever.getBody();
		reciever.setAccountNumber(receiverAccountNumber);
		sender.setCurrentBalance(senderCurrentBalance);
		reciever.setCurrentBalance(recieverCurrentBalance);
		updtaedbalance = transactionService.fundTransfer(sender, reciever);
		restTemplate.put(
				"http://bankaccount/accounts/" + sender.getAccountNumber() + "?balance=" + updtaedbalance[0], null);
		restTemplate.put(
				"http://bankaccount/accounts/" + reciever.getAccountNumber() + "?balance=" + updtaedbalance[1],
				null);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}
}
