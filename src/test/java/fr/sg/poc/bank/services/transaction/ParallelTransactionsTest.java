package fr.sg.poc.bank.services.transaction;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import fr.sg.poc.bank.builder.AccountBuilder;
import fr.sg.poc.bank.entities.Account;
import fr.sg.poc.bank.repository.AccountDao;
import fr.sg.poc.bank.repository.AccountDaoImpl;

public class ParallelTransactionsTest {
	
	/**constants**/
	private static final int ACCOUNT_NUMBER = 12345789;
	
	/**services**/
	private TransactionService transactionService;
	
	/**DAOS**/
	private AccountDao accountDao;
	
	/**
	 * check if case parallel deposit in same account
	 * @throws InterruptedException
	 */
	@Test
	public void parellelDepositTest() throws InterruptedException {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(0d).build();
		accountDao.save(account);
		
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 100; i++) {
		    executorService.submit(new RunDepositWithParameters(ACCOUNT_NUMBER, 10d));
		}
		
		Thread.sleep(1000);
		
		var acctuelBalance = accountDao.findByAcountNumber(ACCOUNT_NUMBER).getBalance();
		assertEquals(Double.valueOf(1000), acctuelBalance);
	}
	
	/**
	 * check if case parallel withDrawal in same account
	 * @throws InterruptedException
	 */
	@Test
	public void parellelwithDrawalTest() throws InterruptedException {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(1000d).build();
		accountDao.save(account);
		
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 100; i++) {
		    executorService.submit(new RunwithDrawalWithParameters(ACCOUNT_NUMBER, 10d));
		}
		
		Thread.sleep(1000);
		
		var acctuelBalance = accountDao.findByAcountNumber(ACCOUNT_NUMBER).getBalance();
		assertEquals(Double.valueOf(0), acctuelBalance);
	}
	
	
	
	private class RunDepositWithParameters implements Runnable {
	    private Integer accountNumber;
	    private Double amount;

	    public RunDepositWithParameters(Integer accountNumber, Double amount) {
	        this.accountNumber = accountNumber;
	        this.amount = amount;
	    }
	    public void run() {
	    	transactionService.deposit(accountNumber, amount);
	    }
	}
	
	public class RunwithDrawalWithParameters implements Runnable {
	    private Integer accountNumber;
	    private Double amount;

	    public RunwithDrawalWithParameters(Integer accountNumber, Double amount) {
	        this.accountNumber = accountNumber;
	        this.amount = amount;
	    }
	    public void run() {
	    	transactionService.withDrawal(accountNumber, amount);
	    }
	} 
	
	@Before
	public void init() {
		//here we can also inject mock using anonymos class but in my case i want to make real call
		accountDao = new AccountDaoImpl();
		transactionService = new TransactionServiceImpl(accountDao);
	}

}
