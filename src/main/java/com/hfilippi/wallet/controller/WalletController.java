package com.hfilippi.wallet.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hfilippi.wallet.error.DuplicateWalletException;
import com.hfilippi.wallet.error.InsufficientFundsException;
import com.hfilippi.wallet.error.WalletNotFoundException;
import com.hfilippi.wallet.model.Wallet;
import com.hfilippi.wallet.model.WalletBalanceHistory;
import com.hfilippi.wallet.model.dto.BalanceDto;
import com.hfilippi.wallet.service.WalletService;

/**
 * Rest Controller for de API.
 * 
 * @author hfilippi
 */

@RestController
@RequestMapping(value = "/recargapay/v1/wallet")
public class WalletController {

	private WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	@PostMapping
	public Wallet createWallet(@RequestParam(name = "user_email", required = true) String userEmail)
			throws DuplicateWalletException {
		return this.walletService.createWallet(userEmail);
	}

	@GetMapping("/balance/{userEmail}")
	public BalanceDto getBalance(@PathVariable String userEmail) throws WalletNotFoundException {
		return this.walletService.getBalance(userEmail);
	}

	@PutMapping("/deposit")
	public Wallet deposit(@RequestParam(name = "user_email", required = true) String userEmail,
			@RequestParam(required = true) BigDecimal amount) throws WalletNotFoundException {
		return this.walletService.deposit(userEmail, amount);
	}

	@PutMapping("/withdraw")
	public Wallet withdraw(@RequestParam(name = "user_email", required = true) String userEmail,
			@RequestParam(required = true) BigDecimal amount)
			throws WalletNotFoundException, InsufficientFundsException {
		return this.walletService.withdraw(userEmail, amount);
	}

	@PutMapping("/transfer")
	public Wallet transfer(@RequestParam(name = "user_email_from", required = true) String userEmailFrom,
			@RequestParam(name = "user_email_to", required = true) String userEmailTo,
			@RequestParam(required = true) BigDecimal amount)
			throws WalletNotFoundException, InsufficientFundsException {
		return this.walletService.transfer(userEmailFrom, userEmailTo, amount);
	}

	@GetMapping("/history")
	public List<WalletBalanceHistory> getBalanceHistory(
			@RequestParam(name = "user_email", required = true) String userEmail,
			@RequestParam(name = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom) {
		return this.walletService.getBalanceHistory(userEmail, dateFrom);
	}

}
