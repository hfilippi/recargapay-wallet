package com.hfilippi.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hfilippi.wallet.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	@Query("SELECT w FROM Wallet w WHERE w.userEmail = :userEmail")
	public Wallet findWallet(String userEmail);

}
