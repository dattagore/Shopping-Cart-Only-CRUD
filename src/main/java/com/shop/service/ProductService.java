package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.model.Product;
import com.shop.model.Cart;

public interface ProductService {

	public List<Product> listProducts();
	
	public Optional<Product> getProductById(long productId);
	
	public void deleteProduct(long productId);
	
	public Product save(Product product);
	
	public Product update(Product product);
	
	public Double findTotalPrice(Cart cart);

}
