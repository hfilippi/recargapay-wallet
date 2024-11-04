package com.hfilippi.wallet.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet_balance_history")
@Builder
public class WalletBalanceHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long id;

	@Column(name = "wallet_id")
	@JsonProperty("wallet_id")
	private Long walletId;

	@Column(name = "date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	@Column(name = "balance")
	private BigDecimal balance;

}
