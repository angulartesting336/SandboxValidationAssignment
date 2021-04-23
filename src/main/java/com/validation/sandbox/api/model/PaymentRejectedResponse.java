package com.validation.sandbox.api.model;

import lombok.Data;

@Data
public class PaymentRejectedResponse {

	private String status;

	private String reason;

	private String reasonCode;

	public PaymentRejectedResponse() {

	}

	public PaymentRejectedResponse(String status, String reason, String reasonCode) {

		this.status = status;
		this.reason = reason;
		this.reasonCode = reasonCode;

	}

}
