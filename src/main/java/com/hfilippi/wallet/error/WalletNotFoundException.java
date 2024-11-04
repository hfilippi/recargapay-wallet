package com.hfilippi.wallet.error;

public class WalletNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public WalletNotFoundException(String userEmail) {
		super("Cannot get Wallet for user " + userEmail);
	}

	public WalletNotFoundException(String message, Throwable e) {
		super(message, e);
	}

}
