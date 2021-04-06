package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.shop.dao.CartRepository;
import com.shop.dao.ProductRepository;
import com.shop.model.Cart;
import com.shop.model.Product;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ShoppingCartApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(
			ShoppingCartApplication.class, args);

		CartRepository cartRepo = context.getBean(CartRepository.class);
		Cart cart1 = new Cart("Cart-1");
		Cart cart2 = new Cart("Cart-2");
		cartRepo.save(cart1);
		cartRepo.save(cart2);
		
		ProductRepository productRepo = context.getBean(ProductRepository.class);
		Product product1 = new Product("Pr-1", 5d);
		Product product2 = new Product("Pr-2", 10d);
		Product product3 = new Product("Pr-3", 40d);
		Product product4 = new Product("Pr-4", 100d);
		productRepo.save(product1);
		productRepo.save(product2);
		productRepo.save(product3);
		productRepo.save(product4);
	}
	
	/**
	 * Bean for Swagger. 
	 * @return
	 */
	
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.paths(PathSelectors.any()).apis(RequestHandlerSelectors.basePackage("com.shop"))
				.build();
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	

}
