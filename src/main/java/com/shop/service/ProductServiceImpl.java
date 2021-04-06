package com.shop.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dao.ProductRepository;
import com.shop.model.Cart;
import com.shop.model.Product;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Override
	public List<Product> listProducts() {
		LOGGER.info("Method listProducts() started");
		List<Product> allProducts = productRepository.findAll();
		LOGGER.info("Total no. of products = " + allProducts.size());
		LOGGER.info("Method listProducts() completed");
		return allProducts;
	}

	@Override
	public Optional<Product> getProductById(long productId) {
		LOGGER.info("Method getProductById() started");
		LOGGER.info("productId = " + productId);
		Optional<Product> product = productRepository.findById(productId);
		LOGGER.info("Method getProductById() completed");
		return product;
	}

	@Override
	public void deleteProduct(long productId) {
		LOGGER.info("Method deleteProduct() started");
		LOGGER.info("Product to be deleted with productId=" + productId);
		productRepository.deleteById(productId);
		LOGGER.info("Product deleted");
		LOGGER.info("Method deleteProduct() completed");
	}
	
	@Override
	public Product save(Product product) {
		LOGGER.info("Method save() started");
		productRepository.save(product);
		LOGGER.info("Product saved");
		LOGGER.info("Method save() completed");
		return product;
	}

	@Override
	public Product update(Product product) {
		LOGGER.info("Method update() started");
		productRepository.save(product);
		LOGGER.info("Product updated");
		LOGGER.info("Method update() completed");
		return product;
	}

	@Override
	public Double findTotalPrice(Cart cart) {
		LOGGER.info("Method findTotalPrice() started");
		List<Product> list = cart.getProductList();
		Double totalPrice = list.stream().mapToDouble(p -> p.getProductPrice()).sum();
		LOGGER.info("totalPrice = " + totalPrice); 
		LOGGER.info("Method findTotalPrice() completed");
		return totalPrice;
	}

}
