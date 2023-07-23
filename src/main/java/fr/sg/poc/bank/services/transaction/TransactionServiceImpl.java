package fr.sg.poc.bank.services.transaction;

import java.time.LocalDateTime;

import fr.sg.poc.bank.entities.Account;
import fr.sg.poc.bank.entities.Transaction;
import fr.sg.poc.bank.enums.TransactionType;
import fr.sg.poc.bank.exceptions.BankPOCRuntimeException;
import fr.sg.poc.bank.repository.AccountDao;

/**
 * 
 * @author youmaa
 *
 */
public class TransactionServiceImpl implements TransactionService {
	
	private AccountDao accountDao;
	
	
	//here we can use spring for DI if it was tolerated in this POC
	public TransactionServiceImpl(AccountDao accountDao) {
		super();
		this.accountDao = accountDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deposit(Integer accountNumber,Double amount) {
		validateAmount(amount);
		//get account with account number
		Account account = getAccount(accountNumber);
		
		synchronized(account) {
			//update balance adding new amount
			account.setBalance(account.getBalance() + amount);
			//add this new transaction to our account history 
			account.getTransactions().add(createTransaction(amount, TransactionType.DEPOSIT));
			//update account in db
			accountDao.uppdateAccount(accountNumber, account);
		}
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void withDrawal(Integer accountNumber, Double amount) {
		validateAmount(amount);
		//get account with account number
		Account account = getAccount(accountNumber);
		
		//balance must be greater than account balance
		if(Double.compare(account.getBalance(),amount) <0) {
			throw new BankPOCRuntimeException("your balance is greater than your balance");
		 }
		synchronized(account) {
			//remove amount from account balance
			account.setBalance(account.getBalance() - amount);
			//add this new transaction to our account history 
			account.getTransactions().add(createTransaction(amount, TransactionType.WITHDRAWAL));
			
			//update account in db
			accountDao.uppdateAccount(accountNumber, account);
		}
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
	
	/**
	 * log Transaction
	 * @param amount
	 * @return
	 */
	private Transaction createTransaction(Double amount, TransactionType transactionType) {
		Transaction toReturn = new Transaction();
		toReturn.setAmount(amount);
		toReturn.setStatementDate(LocalDateTime.now());
		toReturn.setTransactionType(transactionType);
		return toReturn;
	}

	
	/**
	 * validate amount
	 * @param amount
	 */
	private void validateAmount(Double amount) {
		if(Double.compare(amount,0) < 0) {
			 throw new BankPOCRuntimeException("Invalid amount, it must be greater or equal to zero");
		}
		
	}

}
