package com.validation.sandbox.api.exception;

import com.validation.sandbox.api.model.PaymentRejectedResponse;

public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private PaymentRejectedResponse paymentRejectedResponse;

	private String signatureCertificate;

	private String signature;

	private String xRequestId;

	public InvalidRequestException(PaymentRejectedResponse paymentRejectedResponse, String signatureCertificate,
			String signature, String xRequestId) {

		super();
		this.paymentRejectedResponse = paymentRejectedResponse;
		this.signature = signature;
		this.signatureCertificate = signatureCertificate;
		this.xRequestId = xRequestId;
	}

	public PaymentRejectedResponse getPaymentRejectedResponse() {
		return paymentRejectedResponse;
	}

	public String getSignatureCertificate() {
		return signatureCertificate;
	}

	public String getSignature() {
		return signature;
	}

	public String getXRequestId() {
		return xRequestId;
	}

}
