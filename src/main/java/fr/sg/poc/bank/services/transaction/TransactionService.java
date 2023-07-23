package fr.sg.poc.bank.services.transaction;

public interface TransactionService {
	
	/**
	 * make a deposit of amount in account accountNumber
	 * @param amount
	 * @param accountNumber
	 */
	public void deposit(Integer accountNumber, Double amount);
	
	/**
	 * make a withdrawal of amount from account accountNumber
	 * @param amount
	 * @param accountNumber
	 */
	public void withDrawal(Integer accountNumber, Double amount);

}
