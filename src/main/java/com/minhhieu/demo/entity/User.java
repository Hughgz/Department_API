package com.minhhieu.demo.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "db_user") // map to table SQL
@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private int age;
	
	private String avatarUrl;
	@Column(unique = true)
	private String username;
	
	@Column(unique = true)
	private String password;

	@ManyToOne // bắt buộc
	private Department department; // bắt buộc là 1 entity khác
	
}