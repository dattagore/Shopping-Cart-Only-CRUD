package com.shop.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.model.Product;
import com.shop.service.ProductService;

@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@GetMapping("all")
	public ResponseEntity<List <Product>> productList() {
		LOGGER.info("Method productList() started");
		List<Product> allProductsList = productService.listProducts();
		LOGGER.info("Products list size = " + allProductsList.size());
		LOGGER.info("Method productList() completed");
		return new ResponseEntity<>(allProductsList, HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
		LOGGER.info("Method getProduct() started");
		LOGGER.info("productId = " + productId);
		Product productFound = productService.getProductById(productId).get();
		LOGGER.info("Method getProduct() completed");
		return new ResponseEntity<>(productFound, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		LOGGER.info("Method addProduct() started");
		Product productAdded = productService.save(product);
		LOGGER.info("Product saved");
		LOGGER.info("Method addProduct() completed");
		return new ResponseEntity<>(productAdded, HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		LOGGER.info("Method updateProduct() started");
		Product productUpdated = productService.update(product);
		LOGGER.info("Method updateProduct() updated");
		LOGGER.info("Method updateProduct() completed");
		return new ResponseEntity<>(productUpdated, HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
		LOGGER.info("Method deleteProduct() started");
		LOGGER.info("productId = " + productId);
		productService.deleteProduct(productId);
		LOGGER.info("Method deleteProduct() completed");
		return new ResponseEntity<>("Product with Id=" + productId + " deleted", HttpStatus.OK);
	}
	
}
