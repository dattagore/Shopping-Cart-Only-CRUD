package com.shop.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

	public Product(String productName, Double productPrice) {
		this.setProductName(productName);
		this.setProductPrice(productPrice);
	}
	public Product() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	
	@Column
	private String productName;
	
	@Column
	private Double productPrice;
	
	@ManyToMany(mappedBy = "productList")
	@JsonIgnore
	private List<Cart> cartList;
}
