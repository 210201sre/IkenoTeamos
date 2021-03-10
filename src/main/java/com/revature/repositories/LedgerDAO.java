package com.revature.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.Ledger;

public interface LedgerDAO extends JpaRepository<Ledger, Integer> {
	
	
	@Query("SELECT SUM(transactionTotal) FROM Ledger")
	public BigDecimal getGrossProfit();
	@Query("SELECT SUM(transactionTotal) FROM Ledger WHERE transactionTotal < 0")
	public BigDecimal getLosses();
	@Query("SELECT SUM(transactionTotal) FROM Ledger WHERE transactionTotal > 0")
	public BigDecimal getSales();
	
	@Query(value = "SELECT ledger.transaction_item_id FROM project1.ledger WHERE transaction_id = ?1 ", nativeQuery = true)
	public int getItemID(int transaction_id);
	
	@Query(value = "SELECT items.sell_price FROM project1.items WHERE item_id =?1 ", nativeQuery = true)
	public BigDecimal getBuyPrice(int item_id);

}
