package fr.sg.poc.bank.repository;

import java.util.ArrayList;
import java.util.List;

import fr.sg.poc.bank.entities.Account;

/**
 * this class is just a DAO mock to simulate real DAO
 * @author youmaa
 *
 */
public class AccountDaoImpl implements AccountDao{
	private List<Account> data = new ArrayList<Account>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByAcountNumber(Integer accountNumber) {
		return data.stream().filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uppdateAccount(Integer accountNumber, Account updatedAccount) {
		Account AccountToUpdate = data.stream().filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
		var indexOfAcc = data.indexOf(AccountToUpdate);
		data.set(indexOfAcc, updatedAccount);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Account newAccount) {
		data.add(newAccount);
	}
}
