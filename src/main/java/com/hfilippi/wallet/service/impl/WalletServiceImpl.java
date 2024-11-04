package com.hfilippi.wallet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.hfilippi.wallet.error.DuplicateWalletException;
import com.hfilippi.wallet.error.InsufficientFundsException;
import com.hfilippi.wallet.error.WalletNotFoundException;
import com.hfilippi.wallet.model.Wallet;
import com.hfilippi.wallet.model.WalletBalanceHistory;
import com.hfilippi.wallet.model.dto.BalanceDto;
import com.hfilippi.wallet.repository.WalletBalanceHistoryRepository;
import com.hfilippi.wallet.repository.WalletRepository;
import com.hfilippi.wallet.service.WalletService;

/**
 * Wallet Service implementation with all the methods to operate with wallets.
 * 
 * @author hfilippi
 */
@Service
public class WalletServiceImpl implements WalletService {

	private WalletRepository walletRepository;
	private WalletBalanceHistoryRepository walletBalanceHistoryRepository;

	private static final Integer BALANCE_SCALE = 2;

	/**
	 * Constructor.
	 * 
	 * @param walletRepository
	 * @param walletBalanceHistoryRepository
	 */
	public WalletServiceImpl(WalletRepository walletRepository,
			WalletBalanceHistoryRepository walletBalanceHistoryRepository) {
		this.walletRepository = walletRepository;
		this.walletBalanceHistoryRepository = walletBalanceHistoryRepository;
	}

	/**
	 * Create new wallet.
	 * 
	 * @param userEmail The user of the wallet.
	 * @throws DuplicateWalletException If already exists a wallet for userEmail.
	 */
	@Override
	public Wallet createWallet(String userEmail) throws DuplicateWalletException {
		// Validate already existing wallet with userEmail
		Wallet existingWallet = this.walletRepository.findWallet(userEmail);
		if (!Objects.isNull(existingWallet)) {
			throw new DuplicateWalletException(existingWallet.getUserEmail());
		}

		// Create zero balance on wallet creation
		BigDecimal balance = BigDecimal.ZERO.setScale(BALANCE_SCALE, RoundingMode.UP);
		// Build Wallet object
		Wallet wallet = Wallet.builder().userEmail(userEmail).balance(balance).build();

		// Save created wallet to DB
		Wallet createdWallet = this.walletRepository.save(wallet);

		// Save wallet balance history to DB
		this.saveWalletBalanceHistory(createdWallet);

		return createdWallet;
	}

	/**
	 * Get Balance from wallet by user.
	 * 
	 * @param userEmail The user of the wallet.
	 * @throws WalletNotFoundException If there's any wallet for userEmail.
	 */
	@Override
	public BalanceDto getBalance(String userEmail) throws WalletNotFoundException {
		// Get balance from DB
		Wallet wallet = this.walletRepository.findWallet(userEmail);

		// Validate wallet existence
		if (Objects.isNull(wallet)) {
			throw new WalletNotFoundException(userEmail);
		}

		return BalanceDto.builder().userEmail(userEmail).balance(wallet.getBalance()).build();
	}

	/**
	 * Make a deposit to the wallet of userEmail.
	 * 
	 * @param userEmail The user of the wallet.
	 * @param amount    The amount to be deposited.
	 * @throws WalletNotFoundException If there's any wallet for userEmail.
	 */
	@Override
	public Wallet deposit(String userEmail, BigDecimal amount) throws WalletNotFoundException {
		// Validate wallet existence
		Wallet wallet = this.walletRepository.findWallet(userEmail);
		if (Objects.isNull(wallet)) {
			throw new WalletNotFoundException(userEmail);
		}

		// Add amount to balance and update wallet
		BigDecimal balance = wallet.getBalance().add(amount);
		wallet.setBalance(balance);
		Wallet updatedWallet = this.walletRepository.save(wallet);

		// Save wallet balance history to DB
		this.saveWalletBalanceHistory(updatedWallet);

		return updatedWallet;
	}

	/**
	 * Make a withdraw from the wallet of userEmail.
	 * 
	 * @param userEmail The user of the wallet.
	 * @param amount    The amount to withdraw.
	 * @throws WalletNotFoundException    If there's any wallet for userEmail.
	 * @throws InsufficientFundsException If the origin wallet has not enough funds.
	 */
	@Override
	public Wallet withdraw(String userEmail, BigDecimal amount)
			throws WalletNotFoundException, InsufficientFundsException {
		// Validate wallet existence
		Wallet wallet = this.walletRepository.findWallet(userEmail);
		if (Objects.isNull(wallet)) {
			throw new WalletNotFoundException(userEmail);
		}

		// Validate funds
		this.validateInsufficientFunds(wallet, amount);

		// Subtract amount from balance and update wallet
		BigDecimal balance = wallet.getBalance().subtract(amount);
		wallet.setBalance(balance);
		Wallet updatedWallet = this.walletRepository.save(wallet);

		// Save wallet balance history to DB
		this.saveWalletBalanceHistory(updatedWallet);

		return updatedWallet;
	}

	/**
	 * Transfer the amount from one wallet to another.
	 * 
	 * @param userEmailFrom The user of origin wallet.
	 * @param userEmailTo   The user of destination wallet.
	 * @throws WalletNotFoundException    If there's any wallet for userEmail.
	 * @throws InsufficientFundsException If the origin wallet has not enough funds.
	 */
	@Override
	public Wallet transfer(String userEmailFrom, String userEmailTo, BigDecimal amount)
			throws WalletNotFoundException, InsufficientFundsException {
		// Validate wallet FROM existence
		Wallet walletFrom = this.walletRepository.findWallet(userEmailFrom);
		if (Objects.isNull(walletFrom)) {
			throw new WalletNotFoundException(userEmailFrom);
		}

		// Validate wallet TO existence
		Wallet walletTo = this.walletRepository.findWallet(userEmailTo);
		if (Objects.isNull(walletTo)) {
			throw new WalletNotFoundException(userEmailTo);
		}

		// Validate funds
		this.validateInsufficientFunds(walletFrom, amount);

		// Wallet FROM balance subtraction
		BigDecimal balanceFrom = walletFrom.getBalance().subtract(amount);
		walletFrom.setBalance(balanceFrom);
		Wallet updatedWalletFrom = this.walletRepository.save(walletFrom);

		// Save balance history for wallet FROM to DB
		this.saveWalletBalanceHistory(updatedWalletFrom);

		// Wallet TO balance addition
		BigDecimal balanceTo = walletTo.getBalance().add(amount);
		walletTo.setBalance(balanceTo);
		Wallet updatedWalletTo = this.walletRepository.save(walletTo);

		// Save balance history for wallet TO to DB
		this.saveWalletBalanceHistory(updatedWalletTo);

		return updatedWalletFrom;
	}

	/**
	 * Get the balance history of the wallet from DB.
	 * 
	 * @param userEmail The user of the wallet.
	 * @param date      The date filter.
	 */
	@Override
	public List<WalletBalanceHistory> getBalanceHistory(String userEmail, Date date) {
		return this.walletBalanceHistoryRepository.findBalanceHistory(userEmail, date);
	}

	// Validate if the wallet has sufficient funds or throw
	// InsufficientFundsException
	private void validateInsufficientFunds(Wallet wallet, BigDecimal amount) throws InsufficientFundsException {
		if (wallet.getBalance().compareTo(amount) < 0) {
			throw new InsufficientFundsException(wallet.getBalance());
		}
	}

	// Save wallet balance history to DB
	private void saveWalletBalanceHistory(Wallet wallet) {
		WalletBalanceHistory walletBalanceHistory = WalletBalanceHistory.builder().walletId(wallet.getId())
				.date(new Date()).balance(wallet.getBalance()).build();

		this.walletBalanceHistoryRepository.save(walletBalanceHistory);
	}

}
