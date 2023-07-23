package fr.sg.poc.bank.exceptions;

public class BankPOCRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct an instance of AgecapRuntimeException.
	 *
	 * @param msg Message d'erreur
	 */
	public BankPOCRuntimeException(final String msg) {
		super(msg);
	}


}
