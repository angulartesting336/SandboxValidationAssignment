package com.validation.sandbox.api.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.validation.sandbox.api.model.PaymentAcceptedResponse;
import com.validation.sandbox.api.model.PaymentInitiationRequest;

public interface SandboxValidationService {

	public PaymentAcceptedResponse validatePaymentRequestHeader(PaymentInitiationRequest paymentInitiationRequest,
			String signature, String signatureCertificate, String xRequestId)
			throws IOException, GeneralSecurityException;

}
