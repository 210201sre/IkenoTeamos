package com.revature;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.revature.services.LedgerService;

@ExtendWith(MockitoExtension.class)
class LedgerServiceTest {

	@InjectMocks
	LedgerService ledgerService;

	@Mock
	LedgerDAO ledgerDAO;
	
	@Mock
	ItemDAO itemDAO;
	
	@Mock
	Ledger l;

	Item testItem = new Item(1, "Steak", ItemType.Meat, BigDecimal.valueOf(3.99), BigDecimal.valueOf(9.99), 10,
			LocalDateTime.now());
	User testUser = new User(1, "John Doe", Role.Customer);

	@BeforeAll
	public static void init() {
	}
	
//	@Test
//	void makeTransactionTest() {
//		when(ledgerDAO.save(l)).thenReturn(l);
//		Ledger testTransaction = null;
//		
//		assertSame(l, ledgerService.makeTransaction(l));
//		assertSame(testTransaction, ledgerService.makeTransaction(testTransaction));
//		
//		verify(ledgerDAO, times(1)).save(l);		
//	}

	@Test
	void getAllTransactionsTest() {
		List<Ledger> list = new ArrayList<Ledger>();
		Ledger transactionOne = new Ledger(1, testItem, testUser, 20, BigDecimal.valueOf(37.20), LocalDateTime.now());
		Ledger transactionTwo = new Ledger(2, testItem, testUser, 17, BigDecimal.valueOf(3.20), LocalDateTime.now());
		Ledger transactionThree = new Ledger(3, testItem, testUser, 8, BigDecimal.valueOf(42.20), LocalDateTime.now());

		list.add(transactionOne);
		list.add(transactionTwo);
		list.add(transactionThree);

		when(ledgerDAO.findAll()).thenReturn(list);

		List<Ledger> transactions = ledgerService.findAll();

		assertEquals(3, transactions.size());
		verify(ledgerDAO, times(1)).findAll();
	}
	
//	@Test
//	void deleteTransactionTest() {
//		
//		List<Ledger> list = new ArrayList<Ledger>();
//		Ledger transactionOne = new Ledger(1, testItem, testUser, 20, BigDecimal.valueOf(37.20), LocalDateTime.now());
//		Ledger transactionTwo = new Ledger(2, testItem, testUser, 17, BigDecimal.valueOf(3.20), LocalDateTime.now());
//		Ledger transactionThree = new Ledger(3, testItem, testUser, 8, BigDecimal.valueOf(42.20), LocalDateTime.now());
//		
//		//ledgerService.deleteTransaction(transactionThree);
//		System.out.println(transactionThree);
//
//		list.add(transactionOne);
//		list.add(transactionTwo);
//		list.add(transactionThree);
//		
//		
//		when(ledgerDAO.findAll()).thenReturn(list);
//
//		List<Ledger> transactions = ledgerService.findAll();
//		ledgerDAO.delete(transactionThree);
//
//
//		assertEquals(2, transactions.size());
//		verify(ledgerDAO, times(1)).findAll();
//		
//	}
	
	@Test
	void getSalesTest() {
		Ledger transactionOne = new Ledger(1, testItem, testUser, 20, BigDecimal.valueOf(37.20), LocalDateTime.now());
		when(ledgerService.getSales()).thenReturn(transactionOne.getTransactionTotal());
		assertEquals(BigDecimal.valueOf(37.20), ledgerDAO.getSales());
		verify(ledgerDAO, times(2)).getSales();		
	}
	
	@Test
	void getLossesTest() {
		Ledger transactionOne = new Ledger(1, testItem, testUser, 20, BigDecimal.valueOf(-37.20), LocalDateTime.now());
		when(ledgerService.getLosses()).thenReturn(transactionOne.getTransactionTotal());
		assertEquals(BigDecimal.valueOf(-37.20), ledgerDAO.getLosses());
		verify(ledgerDAO, times(1)).getLosses();		
	}
	
	@Test
	void getGrossProfitTest() {
		Ledger transactionOne = new Ledger(1, testItem, testUser, 20, BigDecimal.valueOf(37.20), LocalDateTime.now());
		when(ledgerService.getGrossProfit()).thenReturn(transactionOne.getTransactionTotal());
		assertEquals(BigDecimal.valueOf(37.20), ledgerDAO.getGrossProfit());
		verify(ledgerDAO, times(2)).getGrossProfit();	
	}
	
	
}
