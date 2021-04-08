package com.shop.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.dao.CartRepository;
import com.shop.model.Cart;
import com.shop.model.Product;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductService productService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);
	
	@Value("${payment.service.url}")
	private String paymentServiceURL;
	
	@Value("${payment.service.unavailable.message}")
	private String paymentServiceUnavailableMsg;

	@Autowired
	private RestTemplate template;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public Optional<Cart> findById(long cartId) {
		LOGGER.info("Method save() started");
		LOGGER.info("Cart Id = " + cartId);
		Optional<Cart> cartFound = cartRepository.findById(cartId);
		LOGGER.info("Method save() completed");
		return cartFound;
	}

	@Override
	public Cart save(Cart cart) {
		LOGGER.info("Method save() started");
		cartRepository.save(cart);
		LOGGER.info("Cart saved");
		LOGGER.info("Method save() completed");
		return cart;
	}

	@Override
	public Cart update(Cart cart) {
		LOGGER.info("Method update() started");
		Optional<Cart> foundCart = cartRepository.findById(cart.getCartId());
		Cart foundCartObj = foundCart.get();
		foundCartObj.setCartName(cart.getCartName());
		LOGGER.info("Cart updated");
		LOGGER.info("Method update() completed");
		return cart;
	}

	@Override
	public List<Cart> findAllCarts() {
		LOGGER.info("Method findAllCarts() started");
		List<Cart> allCartsList = cartRepository.findAll();
		LOGGER.info("Total no. of carts = " + allCartsList.size());
		LOGGER.info("Method findAllCarts() completed");
		return allCartsList;
	}

	@Override
	public void deleteCart(long cartId) {
		LOGGER.info("Method deleteCart() started");
		LOGGER.info("Cart to be deleted with cart Id = " + cartId);
		cartRepository.deleteById(cartId);
		LOGGER.info("Method deleteCart() completed");
	}

	@Override
	public Cart addProductToCart(Long cartId, Long productId) {
		LOGGER.info("Method addProductToCart() started");
		Cart cart = cartRepository.findById(cartId).get();
		Product product = productService.getProductById(productId).get();
		List<Product> prodList = cart.getProductList();
		if(null != prodList) {
			prodList.add(product);
		}
		cart.setProductList(prodList);
		update(cart);
		LOGGER.info("Method addProductToCart() completed");
		return cart;
	}

	@Override
	public Cart removeProductFromCart(long cartIdInput, long productIdInput) {
		LOGGER.info("Method removeProductFromCart() started");
		Product product = productService.getProductById(productIdInput).get();
		Cart cart = findById(cartIdInput).get();
		List<Product> productList = cart.getProductList();
		productList.remove(product);
		cart.setProductList(productList);

		update(cart);
		LOGGER.info("Method removeProductFromCart() completed");
		return cart;

	}

}
