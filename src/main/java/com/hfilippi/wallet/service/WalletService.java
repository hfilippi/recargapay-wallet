package com.hfilippi.wallet.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hfilippi.wallet.error.DuplicateWalletException;
import com.hfilippi.wallet.error.InsufficientFundsException;
import com.hfilippi.wallet.error.WalletNotFoundException;
import com.hfilippi.wallet.model.Wallet;
import com.hfilippi.wallet.model.WalletBalanceHistory;
import com.hfilippi.wallet.model.dto.BalanceDto;

public interface WalletService {

	Wallet createWallet(String userEmail) throws DuplicateWalletException;

	BalanceDto getBalance(String userEmail) throws WalletNotFoundException;

	Wallet deposit(String userEmail, BigDecimal amount) throws WalletNotFoundException;

	Wallet withdraw(String userEmail, BigDecimal amount) throws WalletNotFoundException, InsufficientFundsException;

	Wallet transfer(String userEmailFrom, String userEmailTo, BigDecimal amount)
			throws WalletNotFoundException, InsufficientFundsException;

	List<WalletBalanceHistory> getBalanceHistory(String userEmail, Date dateFrom);

}
