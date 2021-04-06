package com.shop.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {

	public Cart(String cartName) {
		this.setCartName(cartName);
	}
	
	public Cart() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cartId;
	
	@Column
	private String cartName;

	/***
	 * productList is nothing but the cart
	 */
	@ManyToMany
	@JoinTable(name = "cartProductList",
			   joinColumns = @JoinColumn(name = "cartId"),
			   inverseJoinColumns = @JoinColumn(name = "productId"))
	private List<Product> productList;
}
