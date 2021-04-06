package com.shop.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.dao.CartRepository;
import com.shop.dao.ProductRepository;
import com.shop.model.Cart;
import com.shop.model.Product;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class CartServiceImplTest {
	
	@InjectMocks
	private CartServiceImpl cartServiceImpl;
	private Cart cartReq, cartResp, cartWithProducts;
	private Product productResp;
	
	@Mock
	private CartRepository cartRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductService productService;
	
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp() throws IOException, JsonParseException, JsonMappingException {
		InputStream cartReqFileStream = Files.newInputStream(Paths.get("src/test/resources/cartRequest.json"));		
		cartReq = mapper.readValue(cartReqFileStream, Cart.class);

		InputStream cartRespFileStream = Files.newInputStream(Paths.get("src/test/resources/cartResponse.json"));		
		cartResp = mapper.readValue(cartRespFileStream, Cart.class);

		InputStream cartFileStream = Files.newInputStream(Paths.get("src/test/resources/sampleCart.json"));		
		cartWithProducts = mapper.readValue(cartFileStream, Cart.class);

		InputStream productRespFileStream = Files.newInputStream(Paths.get("src/test/resources/productResponse.json"));		
		productResp = mapper.readValue(productRespFileStream, Product.class);
	}
	
	@Test
	public void testSave() throws Exception {
		when(cartRepository.save(cartReq)).thenReturn(cartResp);
		cartServiceImpl.save(cartReq);
	}
	
	@Test
	public void testFindAllCarts() throws Exception {
		List<Cart> listOfCarts = new ArrayList<Cart>();
		listOfCarts.add(cartResp);
		when(cartRepository.findAll()).thenReturn(listOfCarts);
		Object resp = cartServiceImpl.findAllCarts();
		assertNotNull(resp);
	}

	@Test
	public void testGetCartById() throws Exception {
		Optional<Cart> cart = Optional.of(cartResp);
		when(cartRepository.findById(1L)).thenReturn(cart);
		Object resp = cartServiceImpl.findById(1L);
		assertNotNull(resp);
	}
	
	@Test
	public void testDeleteCart() throws Exception {
		doNothing().when(cartRepository).deleteById(1L);
		cartServiceImpl.deleteCart(1L);
	}

	@Test
	public void testUpdate() throws Exception {
		Optional<Cart> foundCart = Optional.of(cartResp);
		when(cartRepository.findById(1L)).thenReturn(foundCart);
		cartReq.setCartId(1L);
		Object resp = cartServiceImpl.update(cartReq);
		assertNotNull(resp);
	}
	
	@Test
	public void testAddProductToCart() {
		Optional<Cart> foundCart = Optional.of(cartResp);
		when(cartRepository.findById(1L)).thenReturn(foundCart);
		cartReq.setCartId(1L);

		Optional<Product> foundProduct = Optional.of(productResp);
		when(productService.getProductById(1L)).thenReturn(foundProduct);
		cartReq.setCartId(1L);

		Object resp = cartServiceImpl.addProductToCart(1L, 1L);
		assertNotNull(resp);
	}
	
	@Test
	public void testRemoveProductFromCart() {
		Optional<Cart> foundCart = Optional.of(cartWithProducts);
		when(cartRepository.findById(1L)).thenReturn(foundCart);
		cartReq.setCartId(1L);

		Optional<Product> foundProduct = Optional.of(productResp);
		when(productService.getProductById(1L)).thenReturn(foundProduct);
		cartReq.setCartId(1L);

		Object resp = cartServiceImpl.removeProductFromCart(1L, 1L);
		assertNotNull(resp);
	}
	
}
