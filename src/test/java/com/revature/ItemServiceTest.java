package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Item;
import com.revature.models.ItemType;
import com.revature.models.Ledger;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.ItemDAO;
import com.revature.repositories.LedgerDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.LedgerService;
import com.revature.services.UserService;
import com.revature.services.ItemService;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
	
	@InjectMocks
	ItemService itemService;
	
	@Mock
	ItemDAO itemDAO;
	
	@Mock
	LedgerDAO ledgerDAO;
	
	Item testItem = new Item();
	
	@BeforeAll
	public static void init() {
		
	}
	
	@Test
	void addItemTest() {
		Item i = new Item(1, "name", ItemType.Other, BigDecimal.ONE, BigDecimal.TEN, 1, LocalDateTime.now());
		itemService.addItem(i);
		verify(itemDAO, times(2)).save(i);
		//More test functionality needed because of Ledger stuff in ItemService.addItem()
		
	}
	
	@Test
	void findAllItemsTest() {
		List<Item> list = new ArrayList<Item>();
		
		Item i = new Item();
		Item i2 = new Item();
		Item i3 = new Item();
		list.add(i);
		list.add(i2);
		list.add(i3);
		
		when(itemService.findAll()).thenReturn(list);
		List<Item> itemList = itemService.findAll();
		assertEquals(3, itemList.size());
		
		verify(itemDAO, times(1)).findAll();
	}
	
	@Test
	void findSingleItemTest() {
		Optional<Item> i = Optional.of(new Item(1, "name", ItemType.Other, BigDecimal.ONE, BigDecimal.TEN, 1, LocalDateTime.now()));
		when(itemDAO.findById(1)).thenReturn(i);
		assertEquals(i, itemService.findSingleItem(1));
//		Item i2 = new Item(1, "name", ItemType.Other, BigDecimal.ONE, BigDecimal.TEN, 1, LocalDateTime.now());
//		itemService.addItem(i);
//		itemService.addItem(i2);
//		itemService.findSingleItem(2);
//		verify(itemDAO, times(1)).findSingleItem(2);
	}
	
	@Test
	void deleteItemTest() {
		Item i = new Item(1, "name", ItemType.Other, BigDecimal.ONE, BigDecimal.TEN, 1, LocalDateTime.now());
		itemService.addItem(i);
		itemService.deleteItem(i);
		verify(itemDAO, times(1)).delete(i);
	}
	
	@Test
	void updateTest() {
		//should we be grabbing an item already existing and updating it? prob not
		Item i = new Item(1, "name", ItemType.Other, BigDecimal.ONE, BigDecimal.TEN, 1, LocalDateTime.now());
		itemService.update(i);
		verify(itemDAO, times(1)).save(i);
	}

}
