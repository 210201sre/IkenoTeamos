package com.revature.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ledger", schema = "project1")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Ledger {

	@Id
	@Column(name = "transaction_id", nullable = false, unique = true, updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionID;
	
	@ManyToOne(cascade = CascadeType.REFRESH,  targetEntity = Item.class)
	@JoinColumn(name = "transaction_item_id", referencedColumnName = "item_id")
	private Item item;

	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = User.class)
	@JoinColumn(name = "transaction_user_id", referencedColumnName = "user_id")
	private User user;
	
	private int transactionQuantity;
	private BigDecimal transactionTotal;
	private LocalDateTime transactionTime;

}
