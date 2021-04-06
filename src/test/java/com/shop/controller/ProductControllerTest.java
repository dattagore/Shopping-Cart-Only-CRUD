package com.shop.controller;

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
import com.shop.model.Product;
import com.shop.service.ProductService;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations="classpath:test.properties")
public class ProductControllerTest {
	
	@Mock
	private ProductService productService;
	@InjectMocks
	private ProductController productController;
	private Product productReq;
	ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp() throws IOException, JsonParseException, JsonMappingException {
		InputStream productReqFileStream = Files.newInputStream(Paths.get("src/test/resources/productRequest.json"));		
		productReq = mapper.readValue(productReqFileStream, Product.class);
	}
	
	@Test
	public void testProductList() throws Exception {
		List <Product> allProducts = new ArrayList <Product>();
		when(productService.listProducts()).thenReturn(allProducts);
		Object resp = productController.productList();
		assertNotNull(resp);

	}
	
	@Test
	public void testGetProduct() throws Exception {
		Product product = new Product();
		when(productService.getProductById(1L)).thenReturn(Optional.of(product));
		Object resp = productController.getProduct(1L);
		assertNotNull(resp);
	}
	
	@Test
	public void testAddProduct() throws Exception {
		Product product = new Product();
		when(productService.save(productReq)).thenReturn(product);
		Object resp = productController.addProduct(productReq);
		assertNotNull(resp);

	}

	@Test
	public void testUpdateProduct() throws Exception {
		Product product = new Product();
		when(productService.update(productReq)).thenReturn(product);
		Object resp = productController.updateProduct(productReq);
		assertNotNull(resp);
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
		doNothing().when(productService).deleteProduct(1L);
		Object resp = productController.deleteProduct(1L);
		assertNotNull(resp);
	}
	
}
