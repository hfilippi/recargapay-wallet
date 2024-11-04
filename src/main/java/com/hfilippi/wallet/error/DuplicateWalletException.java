package com.hfilippi.wallet.error;

public class DuplicateWalletException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateWalletException(String userEmail) {
		super("Wallet for user " + userEmail + " already exists");
	}

	public DuplicateWalletException(String message, Throwable e) {
		super(message, e);
	}

}
