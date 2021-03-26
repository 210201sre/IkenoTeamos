package com.revature.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Ledger;
import com.revature.repositories.ItemDAO;
import com.revature.repositories.LedgerDAO;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Service
public class LedgerService {

	@Autowired
	private LedgerDAO ledgerDAO;

	@Autowired
	private ItemDAO itemDAO;

	private static final Logger log = LoggerFactory.getLogger(LedgerService.class);

	Timer timer;
	private MeterRegistry meterRegistry;


	@Autowired
	public LedgerService(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		if (meterRegistry == null) {
			timer = null;
		} else {
			timer = meterRegistry.timer("transaction.timer");
		}
	}

	public Ledger makeTransaction(Ledger l) {

		MDC.put("event", "making transaction");

		log.info("Creating new Transaction");

		LocalDateTime time = LocalDateTime.now();
		l.setTransactionTime(time);
		ledgerDAO.save(l);
		int transactionQuantity = l.getTransactionQuantity();
		BigDecimal buyPrice = itemDAO.getBuyPrice(ledgerDAO.getItemID(l.getTransactionID()));
		if (buyPrice == null) {
			buyPrice = BigDecimal.valueOf(0);
		} else {
			buyPrice = itemDAO.getBuyPrice(ledgerDAO.getItemID(l.getTransactionID()));
		}

		BigDecimal quantity = BigDecimal.valueOf(transactionQuantity);
		l.setTransactionTotal(buyPrice.multiply(quantity));
		itemDAO.updateQuantity(transactionQuantity, ledgerDAO.getItemID(l.getTransactionID()));
		MDC.put("userId", "TransactionID = " + Integer.toString(l.getTransactionID()));
		log.info("Successfully created transaction");
		
		if (meterRegistry == null) {
			return ledgerDAO.saveAndFlush(l);
		}
		else {
			return timer.record(() -> {
				return ledgerDAO.saveAndFlush(l);				
			});
		}
	}

	public List<Ledger> findAll() {

		MDC.put("event", "Getting list of all Transactions in database");
		log.info("Found list of all transactions");

		if (meterRegistry == null) {
			return ledgerDAO.findAll();
		} else {
			return timer.record(() -> {
				return ledgerDAO.findAll();
			});
		}
	}

	public void deleteTransaction(Ledger l) {
		MDC.put("event", "Deleting Item from database with ID = " + Integer.toString(l.getTransactionID()));
		log.info("Succesfully deleted Item with ID = " + Integer.toString(l.getTransactionID()));
		ledgerDAO.delete(l);
	}

	public BigDecimal getGrossProfit() {
		BigDecimal profit;
		MDC.put("event", "Obtaining gross profit");
		if (ledgerDAO.getGrossProfit() == null) {
			profit = BigDecimal.valueOf(0);
		} else {
			profit = ledgerDAO.getGrossProfit();
		}
		log.info("Obtained Gross Profit: $" + profit.toString());
		return ledgerDAO.getGrossProfit();
	}

	public BigDecimal getLosses() {
		BigDecimal losses;
		MDC.put("event", "Obtaining losses");
		if (ledgerDAO.getSales() == null) {
			losses = BigDecimal.valueOf(0);
		} else {
			losses = ledgerDAO.getLosses();
		}

		log.info("Obtained Losses: $" + losses.toString());
		return ledgerDAO.getLosses();
	}

	public BigDecimal getSales() {
		BigDecimal sales;
		MDC.put("event", "Obtaining sales");
		if (ledgerDAO.getSales() == null) {
			sales = BigDecimal.valueOf(0);
		} else {
			sales = ledgerDAO.getSales();
		}

		log.info("Obtained sales: $" + sales.toString());
		return ledgerDAO.getSales();
	}

}
