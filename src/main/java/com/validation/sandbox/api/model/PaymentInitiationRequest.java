package com.validation.sandbox.api.model;

import lombok.Data;

@Data
public class PaymentInitiationRequest {

	private String debtorIBAN;

	private String creditorIBAN;

	private String amount;

	private String endToEndId;

}
