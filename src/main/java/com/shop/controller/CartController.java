
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

import com.shop.model.Cart;
import com.shop.service.CartService;

@Controller
@RequestMapping("cart")
public class CartController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("all")
	public ResponseEntity<List <Cart>> cartList() {
		LOGGER.info("Method cartList() started");
		List<Cart> allCarts = cartService.findAllCarts();
		LOGGER.info("Total no. of carts = " + allCarts.size());
		LOGGER.info("Method cartList() Completed");
		return new ResponseEntity<>(allCarts, HttpStatus.OK);
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<Cart> getCart(@PathVariable("cartId") Long cartId) {
		LOGGER.info("Method getCart() started");
		LOGGER.info("Cart Id=" + cartId);
		Cart cartFound = cartService.findById(cartId).get();
		LOGGER.info("Method getCart() completed");
		return new ResponseEntity<>(cartFound, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Cart> addCart(@RequestBody Cart createCartRequest) {
		LOGGER.info("Method addCart() started");
		Cart cartAdded = cartService.save(createCartRequest);
		LOGGER.info("Cart saved");
		LOGGER.info("Method addCart() completed");
		return new ResponseEntity<>(cartAdded, HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
		LOGGER.info("Method updateCart() started");
		Cart cartUpdated = cartService.update(cart);
		LOGGER.info("Cart updated");
		LOGGER.info("Method updateCart() Completed");
		return new ResponseEntity<>(cartUpdated, HttpStatus.OK);
	}
	
	@DeleteMapping("/{cartId}")
	public ResponseEntity<String> deleteCart(@PathVariable("cartId") Long cartId) {
		LOGGER.info("Method deleteCart() started");
		LOGGER.info("Cart to be deleted with cart id = " + cartId);
		cartService.deleteCart(cartId);
		LOGGER.info("Method deleteCart() started");
		return new ResponseEntity<>("Cart with Id=" + cartId + " deleted", HttpStatus.OK);
	}
	
}
