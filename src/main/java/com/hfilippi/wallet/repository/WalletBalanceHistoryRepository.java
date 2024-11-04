package com.hfilippi.wallet.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hfilippi.wallet.model.WalletBalanceHistory;

@Repository
public interface WalletBalanceHistoryRepository extends JpaRepository<WalletBalanceHistory, Long> {

	@Query(value = "SELECT wbh.* FROM WALLET_BALANCE_HISTORY wbh INNER JOIN WALLET w ON w.id = wbh.wallet_id WHERE w.user_email = :userEmail AND TRUNC(wbh.date) = TO_CHAR(:date, 'yyyy-MM-dd') ORDER BY wbh.date DESC", nativeQuery = true)
	List<WalletBalanceHistory> findBalanceHistory(String userEmail, Date date);

}
