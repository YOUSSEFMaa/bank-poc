package fr.sg.poc.bank.services.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.sg.poc.bank.builder.AccountBuilder;
import fr.sg.poc.bank.entities.Account;
import fr.sg.poc.bank.exceptions.BankPOCRuntimeException;
import fr.sg.poc.bank.repository.AccountDao;
import fr.sg.poc.bank.repository.AccountDaoImpl;

public class TransactionServiceTest {
	/**constants**/
	private static final int ACCOUNT_NUMBER = 12345789;
	
	/**services**/
	private TransactionService transactionService;
	
	/**DAOS**/
	private AccountDao accountDao;
	

	/**
	 * in this case we are testing simple and multipes deposites
	 */
	@Test
	public void simpleAndMultiplesdepositTest() {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(0d).build();
		accountDao.save(account);
		
		//here i make multiples deposit of 100 - 50 -25 
		transactionService.deposit(account.getAccountNumber(), 100d);
		var acctuelBalance = accountDao.findByAcountNumber(account.getAccountNumber()).getBalance();
		assertEquals(Double.valueOf(100), acctuelBalance);
		
		transactionService.deposit(account.getAccountNumber(), 50d);
		transactionService.deposit(account.getAccountNumber(), 25d);
		var acctuelBalance2 = accountDao.findByAcountNumber(account.getAccountNumber()).getBalance();
		assertEquals(Double.valueOf(175), acctuelBalance2);
		
		//and also we must check number of transactions
		assertEquals(3, account.getTransactions().size());
	}
	
	/**
	 * test deposit of negative number
	 */
	@Test(expected = BankPOCRuntimeException.class)
	public void depositOfNegativeAmountTest() {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(0d).build();
		accountDao.save(account);
		//here this test must not pass due to validation error
		transactionService.deposit(account.getAccountNumber(), -100d);
	}
	
	/**
	 * test desposit in non existing account
	 */
	@Test(expected = BankPOCRuntimeException.class)
	public void depositInNonExtingAccountTest() {
		//account with with number 785469 doesn't exist
		transactionService.deposit(785469, 100d);
	}
	
	/**
	 * in this case we are testing simple and multipes withDrawal
	 */
	@Test
	public void simpleAndMultipleswithDrawalTest() {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(100d).build();
		accountDao.save(account);
		
		//here i make multiples deposit of 100 - 50 -25 
		transactionService.withDrawal(account.getAccountNumber(), 10d);
		var acctuelBalance = accountDao.findByAcountNumber(account.getAccountNumber()).getBalance();
		assertEquals(Double.valueOf(90), acctuelBalance);
		
		transactionService.withDrawal(account.getAccountNumber(), 20d);
		transactionService.withDrawal(account.getAccountNumber(), 30d);
		var acctuelBalance2 = accountDao.findByAcountNumber(account.getAccountNumber()).getBalance();
		assertEquals(Double.valueOf(40), acctuelBalance2);
		//and also we must check number of transactions
		assertEquals(3, account.getTransactions().size());
	}
	
	
	/**
	 * test withDrawal of negative number
	 */
	@Test(expected = BankPOCRuntimeException.class)
	public void withDrawalNegativeAmountTest() {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(0d).build();
		accountDao.save(account);
		//here this test must not pass due to validation error
		transactionService.withDrawal(account.getAccountNumber(), -100d);
	}
	
	/**
	 * test withDrawal in non existing account
	 */
	@Test(expected = BankPOCRuntimeException.class)
	public void withDrawalInNonExtingAccountTest() {
		//account with with number 785469 doesn't exist
		transactionService.withDrawal(785469, 100d);
	}
	
	/**
	 * test withDrawal more than account balance
	 */
	@Test(expected = BankPOCRuntimeException.class)
	public void withDrawalMoreThanBalanceTest() {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(100d).build();
		accountDao.save(account);
		//account with with number 785469 doesn't exist
		transactionService.withDrawal(account.getAccountNumber(), 150d);
	}
	
	
	
	@Before
	public void init() {
		//here we can also inject mock using anonymos class but in my case i want to make real call
		accountDao = new AccountDaoImpl();
		transactionService = new TransactionServiceImpl(accountDao);
	}
}
