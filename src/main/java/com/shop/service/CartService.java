package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.model.Cart;

public interface CartService {
	
	public Optional<Cart> findById(long id);
	
	public Cart save(Cart cart);
	
	public Cart update(Cart cart);
	
	public List<Cart> findAllCarts();
	
	public void deleteCart(long cartId);

	public Cart addProductToCart(Long cartId, Long productId);

	public Cart removeProductFromCart(long cartIdInput, long productIdInput);

}
