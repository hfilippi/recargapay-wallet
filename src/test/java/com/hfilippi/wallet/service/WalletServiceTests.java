package com.hfilippi.wallet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hfilippi.wallet.error.DuplicateWalletException;
import com.hfilippi.wallet.model.Wallet;
import com.hfilippi.wallet.model.WalletBalanceHistory;
import com.hfilippi.wallet.repository.WalletBalanceHistoryRepository;
import com.hfilippi.wallet.repository.WalletRepository;
import com.hfilippi.wallet.service.impl.WalletServiceImpl;

@ExtendWith(MockitoExtension.class)
class WalletServiceTests {

	@InjectMocks
	WalletServiceImpl walletService;

	@Mock
	WalletRepository walletRepository;

	@Mock
	WalletBalanceHistoryRepository walletBalanceHistoryRepository;

	@Test
	@DisplayName("Create a new wallet test")
	void testCreateWallet() throws DuplicateWalletException {
		BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.UP);
		Wallet wallet = Wallet.builder().userEmail("johndoe@mail.com").balance(balance).build();

		when(this.walletRepository.save(any())).thenReturn(wallet);

		WalletBalanceHistory walletBalanceHistory = WalletBalanceHistory.builder().walletId(wallet.getId())
				.date(new Date()).balance(wallet.getBalance()).build();

		when(this.walletBalanceHistoryRepository.save(any())).thenReturn(walletBalanceHistory);

		Wallet createdWallet = this.walletService.createWallet(anyString());

		assertThat(createdWallet).isNotNull();
		assertThat(createdWallet.getUserEmail()).isEqualTo("johndoe@mail.com");
	}

	@Test
	@DisplayName("Create existing wallet test that throws DuplicateWalletException")
	void testCreateExistingWalletThrowsDuplicateWalletException() {
		BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.UP);
		Wallet existingWallet = Wallet.builder().userEmail("johndoe@mail.com").balance(balance).build();

		when(this.walletRepository.findWallet(anyString())).thenReturn(existingWallet);

		Throwable throwable = catchThrowable(() -> {
			this.walletService.createWallet(existingWallet.getUserEmail());
		});

		assertThat(throwable).isInstanceOf(DuplicateWalletException.class);
	}

}
