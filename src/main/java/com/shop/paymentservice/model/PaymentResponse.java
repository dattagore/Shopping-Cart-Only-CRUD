package com.shop.paymentservice.model;

import lombok.Data;

/**
 * Payment Response POJO
 *
 */

@Data
public class PaymentResponse {

	private long paymentId;
	private Double amount;
	private long cartId;
	private String status;

}
