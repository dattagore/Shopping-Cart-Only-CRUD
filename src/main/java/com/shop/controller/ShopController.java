package com.shop.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.model.Cart;
import com.shop.service.CartService;
import com.shop.service.ProductService;

@Controller
@RequestMapping("cart")
public class ShopController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartService cartService;
	
	@Value("${payment.service.error.message}")
	private String paymentServiceErrMsg;

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopController.class);
	
	@PostMapping("addToCart/{cartId}/product/{productId}")
	public ResponseEntity<String> addToCart(@PathVariable("productId") Long productId,
			@PathVariable("cartId") Long cartId) {
		
		LOGGER.info("Method addToCart() started");
		LOGGER.info("Product Id="+ productId + " is to be added to Cart with Id = " + cartId);
		Cart cart = cartService.addProductToCart(cartId, productId);
		Double total = productService.findTotalPrice(cart);
		LOGGER.info("Method addToCart() completed");
		return new ResponseEntity<>("Product added, updated total price = $ " + total, HttpStatus.OK);

	}
	
	@DeleteMapping("removeFromCart/{cartId}/product/{productId}")
	public ResponseEntity<String> removeFromCart(@PathVariable("productId") Long productId,
			@PathVariable("cartId") Long cartId) {
		LOGGER.info("Method removeFromCart() started");
		Cart cart = cartService.removeProductFromCart(cartId, productId);
		Double total = productService.findTotalPrice(cart);
		LOGGER.info("total=" + total);
		LOGGER.info("Method removeFromCart() completed");
		return new ResponseEntity<>("Product removed, updated total price = $ " + total, HttpStatus.OK);
	}
	
	@GetMapping("total-price/{cartId}")
	public ResponseEntity<String> getCartTotalPrice(@PathVariable("cartId") Long cartId) {
		LOGGER.info("Method getCartTotalPrice() started");
		Optional<Cart> cart = cartService.findById(cartId);
		Double total = productService.findTotalPrice(cart.get());
		LOGGER.info("total=" + total);
		LOGGER.info("Method getCartTotalPrice() completed");
		return new ResponseEntity<>("Total price = $ " + total, HttpStatus.OK);
	}
	
}
