package fr.sg.poc.bank.repository;

import fr.sg.poc.bank.entities.Account;

public interface AccountDao {

	/**
	 * find Account by account number
	 * @param accountNumber
	 * @return
	 */
	Account findByAcountNumber(Integer accountNumber);

	/**
	 * uppdate account
	 */
	void uppdateAccount(Integer accountNumber, Account updatedAccount);

	/**
	 * save new accunt
	 * @param newAccount
	 */
	void save(Account newAccount);

}
