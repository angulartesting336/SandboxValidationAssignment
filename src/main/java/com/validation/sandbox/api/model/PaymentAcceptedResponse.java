package com.validation.sandbox.api.model;

import lombok.Data;

@Data
public class PaymentAcceptedResponse {

	private String paymentId;

	private String status;
}
