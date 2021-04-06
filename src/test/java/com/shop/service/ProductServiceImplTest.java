package com.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.shop.dao.ProductRepository;
import com.shop.model.Cart;
import com.shop.model.Product;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations="classpath:test.properties")
public class ProductServiceImplTest {
	
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	private Product productReq, productResp;
	private Cart sampleCart;
	
	@Mock
	private ProductRepository productRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp() throws IOException, JsonParseException, JsonMappingException {
		InputStream productReqFileStream = Files.newInputStream(Paths.get("src/test/resources/productRequest.json"));		
		productReq = mapper.readValue(productReqFileStream, Product.class);

		InputStream productRespFileStream = Files.newInputStream(Paths.get("src/test/resources/productResponse.json"));		
		productResp = mapper.readValue(productRespFileStream, Product.class);
		
		InputStream sampleCartFileStream = Files.newInputStream(Paths.get("src/test/resources/sampleCart.json"));		
		sampleCart = mapper.readValue(sampleCartFileStream, Cart.class);

	}
	
	@Test
	public void testSave() throws Exception {
		when(productRepository.save(productReq)).thenReturn(productResp);
		productServiceImpl.save(productReq);
	}
	
	@Test
	public void testListProducts() throws Exception {
		List<Product> listOfProducts = new ArrayList<Product>();
		listOfProducts.add(productResp);
		when(productRepository.findAll()).thenReturn(listOfProducts);
		Object resp = productServiceImpl.listProducts();
		assertNotNull(resp);
	}

	@Test
	public void testGetProductById() throws Exception {
		Optional<Product> product = Optional.of(productResp);
		when(productRepository.findById(1L)).thenReturn(product);
		Object resp = productServiceImpl.getProductById(1L);
		assertNotNull(resp);
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
		doNothing().when(productRepository).deleteById(1L);
		productServiceImpl.deleteProduct(1L);
	}

	@Test
	public void testUpdate() throws Exception {
		when(productRepository.save(productReq)).thenReturn(productResp);
		Object resp = productServiceImpl.update(productReq);
		assertNotNull(resp);
	}
	
	@Test
	public void testFindTotalPrice() {
		Double totalPrice = productServiceImpl.findTotalPrice(sampleCart);
		System.out.println("totalPrice=" + totalPrice);
		assertEquals(115d, totalPrice);
	}

}
