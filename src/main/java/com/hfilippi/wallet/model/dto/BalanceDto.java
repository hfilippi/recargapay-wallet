package com.hfilippi.wallet.model.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceDto {

	@JsonProperty("user_email")
	private String userEmail;

	@JsonProperty("balance")
	private BigDecimal balance;

}
