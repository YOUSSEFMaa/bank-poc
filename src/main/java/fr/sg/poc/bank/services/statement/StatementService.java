package fr.sg.poc.bank.services.statement;

public interface StatementService {
	
	/**
	 * 
	 * @param accountNumber
	 * @return
	 */
	public String printAccountStatements(Integer accountNumber);
}
