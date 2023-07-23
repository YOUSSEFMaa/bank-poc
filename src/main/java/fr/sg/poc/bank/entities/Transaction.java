package fr.sg.poc.bank.entities;

import java.time.LocalDateTime;

import fr.sg.poc.bank.enums.TransactionType;

public class Transaction {
		
	private Long id;
	private Double amount;
	private TransactionType transactionType;
	private LocalDateTime statementDate;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the statementDate
	 */
	public LocalDateTime getStatementDate() {
		return statementDate;
	}
	/**
	 * @param statementDate the statementDate to set
	 */
	public void setStatementDate(LocalDateTime statementDate) {
		this.statementDate = statementDate;
	}
	/**
	 * @return the transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	
	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", transactionType=" + transactionType
				+ ", statementDate=" + statementDate + "]";
	}
	
	
	
}
