package fr.sg.poc.bank.services.statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import fr.sg.poc.bank.builder.AccountBuilder;
import fr.sg.poc.bank.entities.Account;
import fr.sg.poc.bank.exceptions.BankPOCRuntimeException;
import fr.sg.poc.bank.repository.AccountDao;
import fr.sg.poc.bank.repository.AccountDaoImpl;
import fr.sg.poc.bank.services.transaction.TransactionService;
import fr.sg.poc.bank.services.transaction.TransactionServiceImpl;

public class StatementServiceTest {
	/**constants**/
	private static final int ACCOUNT_NUMBER = 12345789;
	
	/**services**/
	private TransactionService transactionService;
	private StatementService statementService;
	
	/**DAOS**/
	private AccountDao accountDao;

	/**
	 * check printed balance : here due to time insuffisance we can improve this test to check all printed string in one assert
	 */
	@Test
	public void printAccountStatementsTest() {
		Account account = new AccountBuilder().withAccountNumber(ACCOUNT_NUMBER).withBalance(0d).build();
		accountDao.save(account);
		
		transactionService.deposit(account.getAccountNumber(), 100d);
		transactionService.deposit(account.getAccountNumber(), 150d);

		transactionService.withDrawal(account.getAccountNumber(), 50d);
		
		var acctuelAccount = accountDao.findByAcountNumber(account.getAccountNumber());
		
		assertEquals(3, acctuelAccount.getTransactions().size());
				
		//check in printed string our transactions
		assertTrue(
				statementService.printAccountStatements(acctuelAccount.getAccountNumber()).contains("[amount=100.0, transactionType=DEPOSIT, statementDate="+LocalDate.now())
				);
		//check in printed string our transactions
		assertTrue(
				statementService.printAccountStatements(acctuelAccount.getAccountNumber()).contains("[amount=150.0, transactionType=DEPOSIT, statementDate="+LocalDate.now())
				);
		//check in printed string our transactions
		assertTrue(
				statementService.printAccountStatements(acctuelAccount.getAccountNumber()).contains("[amount=50.0, transactionType=WITHDRAWAL, statementDate="+LocalDate.now())
				);

	}
	
	
	@Test(expected = BankPOCRuntimeException.class)
	public void depositOfNegativeAmountTest() {
		statementService.printAccountStatements(8798152);
	}
	
	
	@Before
	public void init() {
		//here we can also inject mock using anonymos class but in my case i want to make real call
		accountDao = new AccountDaoImpl();
		transactionService = new TransactionServiceImpl(accountDao);
		statementService = new StatementServiceImpl(accountDao);
	}
}
