package com.revature.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.Item;

public interface ItemDAO extends JpaRepository<Item, Integer> {
	
	@Query(value = "SELECT items.sell_price FROM project1.items WHERE item_id =?1 ", nativeQuery = true)
	public BigDecimal getBuyPrice(int item_id);
	
	@Modifying
	@Query(value = "UPDATE project1.items SET quantity = quantity - ?1 WHERE item_id =?2 ", nativeQuery = true)
	public void updateQuantity(int quantity, int item_id);

}
