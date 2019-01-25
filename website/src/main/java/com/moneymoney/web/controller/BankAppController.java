package com.moneymoney.web.controller;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import com.moneymoney.web.entity.CurrentDataSet;
import com.moneymoney.web.entity.Transaction;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
public class BankAppController {

	private static CurrentDataSet storeCurrentDataSet;
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/getAllStatments")
	@HystrixCommand(fallbackMethod = "failedgetAllStatments")
	public String getAllStatements(Model model) {
		Transaction transactionList = restTemplate
				.getForObject("http://localhost:9096/transaction/transactions/getAllStatments", Transaction.class);
		model.addAttribute("transactionList", transactionList);
		return "statements";
	}

	@HystrixCommand(fallbackMethod = "failedgetAllStatments")
	public String failedgetAllStatments(Model model) {
		model.addAttribute("transactionList", "Unavailable");
		return "statements";
	}

	@RequestMapping("/depositform")
	public String depositForm() {
		return "DepositForm";
	}

	@RequestMapping("/deposit")
	@HystrixCommand(fallbackMethod = "failedDeposit")
	public String deposit(@ModelAttribute Transaction transaction, Model model) {
		restTemplate.postForEntity("http://localhost:9096/transaction/transactions", transaction, null);
		model.addAttribute("message", "Success!");
		return "DepositForm";
	}

	public String failedDeposit(@ModelAttribute Transaction transaction, Model model) {
		model.addAttribute("message", "Deposite service is unavialble!");
		return "DepositForm";
	}

	@RequestMapping("/withdrawform")
	public String withdrawForm() {
		return "WithdrawForm";
	}

	@RequestMapping("/withdraw")
	@HystrixCommand(fallbackMethod = "failedWithdraw")
	public String withdraw(@ModelAttribute Transaction transaction, Model model) {
		restTemplate.postForEntity("http://localhost:9096/transaction/transactions/withdraw", transaction, null);
		model.addAttribute("message", "Success!");
		return "WithdrawForm";
	}

	public String failedWithdraw(@ModelAttribute Transaction transaction, Model model) {
		model.addAttribute("message", "Withdraw service is unavialble!!");
		return "WithdrawForm";
	}

	@RequestMapping("/fundtransferform")
	public String fundtransferForm() {
		return "fundtransferform";
	}

	@RequestMapping("/transfer")
	@HystrixCommand(fallbackMethod = "failedtransfer")
	public String fundTransferForm(@RequestParam("senderAccountNumber") Integer senderAccountNumber,
			@RequestParam("receiverAccountNumber") Integer receiverAccountNumber, @RequestParam("amount") Double amount,
			Model model) {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(senderAccountNumber);
		transaction.setTransactionDetails("Online");
		transaction.setAmount(amount);
		restTemplate.postForEntity("http://localhost:9096/transaction/transactions/transfer?receiverAccountNumber="
				+ receiverAccountNumber, transaction, null);
		model.addAttribute("message", "Success!");
		return "fundtransferform";
	}

	public String failedtransfer(@RequestParam("senderAccountNumber") Integer senderAccountNumber,
			@RequestParam("receiverAccountNumber") Integer receiverAccountNumber, @RequestParam("amount") Double amount,
			Model model) {
		model.addAttribute("message", "fundTransfer service is unavialble!!");
		return "fundtransferform";
	}

	/*
	 * @RequestMapping("/statement")
	 * 
	 * @HystrixCommand(fallbackMethod = "failedStatement") public ModelAndView
	 * getStatement(@RequestParam("offset") int offset, @RequestParam("size") int
	 * size) { CurrentDataSet currentDataSet =
	 * restTemplate.getForObject("http://localhost:9096/transaction/transactions",
	 * CurrentDataSet.class); int currentSize = size == 0 ? 5 : size; int
	 * currentOffset = offset == 0 ? 1 : offset; Link previous =
	 * ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BankAppController
	 * .class) .getStatement(currentOffset - currentSize,
	 * currentSize)).withRel("previous"); Link next =
	 * ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BankAppController
	 * .class) .getStatement(currentOffset + currentSize,
	 * currentSize)).withRel("next");
	 * 
	 * 
	 * storeCurrentDataSet = currentDataSet; System.out.println(currentDataSet);
	 * List<Transaction> transactionList = currentDataSet.getTransactions();
	 * List<Transaction> transactions = new ArrayList<Transaction>(); for (int value
	 * = currentOffset - 1; value < currentOffset + currentSize - 1; value++) { if
	 * ((transactionList.size() <= value && value > 0) || currentOffset < 1) break;
	 * Transaction transaction = transactionList.get(value);
	 * transactions.add(transaction); } currentDataSet.setPreviousLink(previous);
	 * currentDataSet.setNextLink(next);
	 * currentDataSet.setTransactions(transactions); return new
	 * ModelAndView("DepositForm", "currentDataSet", currentDataSet); }
	 * 
	 * public ModelAndView failedStatement(@RequestParam("offset") int
	 * offset, @RequestParam("size") int size) { CurrentDataSet currentDataSet
	 * =storeCurrentDataSet; int currentSize = size == 0 ? 5 : size; int
	 * currentOffset = offset == 0 ? 1 : offset; Link previous =
	 * ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BankAppController
	 * .class) .getStatement(currentOffset - currentSize,
	 * currentSize)).withRel("previous"); Link next =
	 * ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BankAppController
	 * .class) .getStatement(currentOffset + currentSize,
	 * currentSize)).withRel("next");
	 * 
	 * 
	 * System.out.println(currentDataSet); List<Transaction> transactionList =
	 * currentDataSet.getTransactions(); List<Transaction> transactions = new
	 * ArrayList<Transaction>(); for (int value = currentOffset - 1; value <
	 * currentOffset + currentSize - 1; value++) { if ((transactionList.size() <=
	 * value && value > 0) || currentOffset < 1) break; Transaction transaction =
	 * transactionList.get(value); transactions.add(transaction); }
	 * currentDataSet.setPreviousLink(previous); currentDataSet.setNextLink(next);
	 * currentDataSet.setTransactions(transactions); return new
	 * ModelAndView("DepositForm", "currentDataSet", currentDataSet); }
	 * 
	 */

	@RequestMapping("/statement")
	@HystrixCommand(fallbackMethod = "fallbackDepositStatement")
	public ModelAndView getStatementDeposit(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://localhost:9096/transaction/transactions",
				CurrentDataSet.class);
		int currentSize = size == 0 ? 5 : size;
		int currentOffset = offset == 0 ? 1 : offset;
		Link next = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset + currentSize, currentSize))
						.withRel("next");
		Link previous = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset - currentSize, currentSize))
						.withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		storeCurrentDataSet = currentDataSet;
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) {
			if ((transactions.size() <= i && i > 0) || currentOffset < 1)
				break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);

		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, previous,next);
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet", dataSet);
		modelView.setViewName("statements");
		return modelView;
	}

	public ModelAndView fallbackDepositStatement(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = storeCurrentDataSet;
		int currentSize = size == 0 ? 5 : size;
		int currentOffset = offset == 0 ? 1 : offset;
		Link next = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset + currentSize, currentSize))
						.withRel("next");
		Link previous = linkTo(
				methodOn(BankAppController.class).getStatementDeposit(currentOffset - currentSize, currentSize))
						.withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();

		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) {
			if ((transactions.size() <= i && i > 0) || currentOffset < 1)
				break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);

		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, previous,next);
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet", dataSet);
		modelView.setViewName("statements");
		modelView.addObject("message", "Updated statements not available ...try again later!!");
		return modelView;
	}

}
