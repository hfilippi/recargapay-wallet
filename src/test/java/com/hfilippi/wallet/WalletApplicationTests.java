package com.hfilippi.wallet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hfilippi.wallet.controller.WalletController;
import com.hfilippi.wallet.service.WalletService;

@SpringBootTest
class WalletApplicationTests {

	@Autowired
	private WalletController walletController;

	@Autowired
	private WalletService walletService;

	@Test
	void contextLoads() {
		assertThat(walletController).isNotNull();
		assertThat(walletService).isNotNull();
	}

}
