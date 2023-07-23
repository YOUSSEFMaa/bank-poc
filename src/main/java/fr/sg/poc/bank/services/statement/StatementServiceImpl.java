package fr.sg.poc.bank.services.statement;

import fr.sg.poc.bank.entities.Account;
import fr.sg.poc.bank.exceptions.BankPOCRuntimeException;
import fr.sg.poc.bank.repository.AccountDao;

public class StatementServiceImpl implements StatementService {
	
	private AccountDao accountDao;
	
	
	//here we can use spring for DI if it was tolerated in this POC
	public StatementServiceImpl(AccountDao accountDao) {
		super();
		this.accountDao = accountDao;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String printAccountStatements(Integer accountNumber) {
		var printer = new StringBuilder();
		//get account with account number
		Account account = getAccount(accountNumber);
		
		//here we construct a strring with all operations
		account.getTransactions().forEach(t->printer.append(t.toString()).append("\n"));
		
		//print transactions history
		return printer.toString();
	}
	
	
	/**
	 * get account
	 * @param accountNumber
	 * @return
	 */
	private Account getAccount(Integer accountNumber) {
		Account account = accountDao.findByAcountNumber(accountNumber);
		 if(account == null) {
			 throw new BankPOCRuntimeException("Couldn't find any Account Number under unmber: "+accountNumber);
		 }
		return account;
	}

}
