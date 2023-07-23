package fr.sg.poc.bank.builder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import fr.sg.poc.bank.entities.Account;
import fr.sg.poc.bank.entities.Transaction;

public class AccountBuilder {
	
	private Integer accountNumber;
	private Double balance;
	private LocalDateTime creationDate;
	private Set<Transaction> transactions = new HashSet<Transaction>();
	
	public AccountBuilder withAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
		return this;
	}
	public AccountBuilder withBalance(Double balance) {
		this.balance = balance;
		return this;
	}
	
	
	public Account build() {
		Account toReturn = new Account();
		toReturn.setAccountNumber(accountNumber);
		toReturn.setBalance(balance);
		toReturn.setCreationDate(LocalDateTime.now());
		
		return toReturn;
	}
	
}
