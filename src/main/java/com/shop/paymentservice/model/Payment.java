package com.shop.paymentservice.model;

import lombok.Data;

/**
 * Payment Request POJO
 *
 */

@Data
public class Payment {
	
	private long cartId;
	private Double amount;

}
