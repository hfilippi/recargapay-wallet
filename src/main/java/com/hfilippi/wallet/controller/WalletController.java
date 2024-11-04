package com.hfilippi.wallet.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;

/**
 * Rest Controller for API.
 * 
 * @author hfilippi
 */

@RestController
@RequestMapping(value = "/recargapay/v1/wallet")
@Validated
public class WalletController {

	private WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	@Operation(description = "Allow the creation of wallets for users.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Wallet created OK."),
			@ApiResponse(responseCode = "409", description = "Wallet already exist") })
	@PostMapping
	public Wallet createWallet(@RequestParam(name = "user_email", required = true) @Email String userEmail)
			throws DuplicateWalletException {
		return this.walletService.createWallet(userEmail);
	}

	@Operation(description = "Retrieve the current balance of a user's wallet.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Balance retrived OK."),
			@ApiResponse(responseCode = "404", description = "Wallet not found") })
	@GetMapping("/balance/{userEmail}")
	public BalanceDto getBalance(@PathVariable @Email String userEmail) throws WalletNotFoundException {
		return this.walletService.getBalance(userEmail);
	}

	@Operation(description = "Enable users to deposit money into their wallets.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deposit OK."),
			@ApiResponse(responseCode = "404", description = "Wallet not found") })
	@PutMapping("/deposit")
	public Wallet deposit(@RequestParam(name = "user_email", required = true) @Email String userEmail,
			@RequestParam(required = true) BigDecimal amount) throws WalletNotFoundException {
		return this.walletService.deposit(userEmail, amount);
	}

	@Operation(description = "Enable users to withdraw money from their wallets.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Withdraw OK."),
			@ApiResponse(responseCode = "404", description = "Wallet not found"),
			@ApiResponse(responseCode = "409", description = "Insufficient funds") })
	@PutMapping("/withdraw")
	public Wallet withdraw(@RequestParam(name = "user_email", required = true) @Email String userEmail,
			@RequestParam(required = true) BigDecimal amount)
			throws WalletNotFoundException, InsufficientFundsException {
		return this.walletService.withdraw(userEmail, amount);
	}

	@Operation(description = "Facilitate the transfer of money between user wallets.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Transfer OK."),
			@ApiResponse(responseCode = "404", description = "Wallet not found"),
			@ApiResponse(responseCode = "409", description = "Insufficient funds") })
	@PutMapping("/transfer")
	public Wallet transfer(@RequestParam(name = "user_email_from", required = true) @Email String userEmailFrom,
			@RequestParam(name = "user_email_to", required = true) @Email String userEmailTo,
			@RequestParam(required = true) BigDecimal amount)
			throws WalletNotFoundException, InsufficientFundsException {
		return this.walletService.transfer(userEmailFrom, userEmailTo, amount);
	}

	@Operation(description = "Retrieve the balance of a user's wallet at a specific point in the past.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Get balance history OK.") })
	@GetMapping("/history")
	public List<WalletBalanceHistory> getBalanceHistory(
			@RequestParam(name = "user_email", required = true) @Email String userEmail,
			@RequestParam(name = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") @PastOrPresent Date dateFrom) {
		return this.walletService.getBalanceHistory(userEmail, dateFrom);
	}

}
