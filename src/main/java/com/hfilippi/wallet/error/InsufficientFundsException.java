package com.hfilippi.wallet.error;

import java.math.BigDecimal;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(BigDecimal balance) {
		super("Insufficient funds: " + balance);
	}

	public InsufficientFundsException(String message, Throwable e) {
		super(message, e);
	}

}
