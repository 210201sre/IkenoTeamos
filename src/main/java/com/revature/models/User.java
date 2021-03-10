package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="users",schema="project1")
@Data @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode @ToString
public class User {
	
	@Id
	@Column(name = "user_id", nullable = false, unique = true, updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;
	private String name;
	private Role role;
	
//	@JsonBackReference
//	private Ledger ledger;

}
