package com.validation.sandbox.api.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.validation.sandbox.api.model.PaymentAcceptedResponse;
import com.validation.sandbox.api.model.PaymentInitiationRequest;
import com.validation.sandbox.api.service.SandboxValidationService;

@Service
public class SandboxApiValidator implements SandboxValidationService {

	@Autowired
	CertificateNameValidator certificateNameValidator;

	@Autowired
	SignatureValidator signatureValidator;

	@Autowired
	PaymentRequestValidator paymentRequestValidator;

	@Autowired
	LimitExceededValidator  limitExceededValidator ;

	@Override
	public PaymentAcceptedResponse validatePaymentRequestHeader(PaymentInitiationRequest paymentInitiationRequest,
			String signature, String signatureCertificate, String xRequestId)
			throws IOException, GeneralSecurityException {

		certificateNameValidator.validateCertificateName(signature, signatureCertificate, xRequestId);

		signatureValidator.validateSignature(paymentInitiationRequest,signature,
				signatureCertificate, xRequestId);

		paymentRequestValidator.validatePaymentRequest(paymentInitiationRequest,signature,
				signatureCertificate, xRequestId);

		limitExceededValidator.checkLimitExceeded(paymentInitiationRequest.getAmount(),
				paymentInitiationRequest.getDebtorIBAN(),signature,
				signatureCertificate, xRequestId);

		PaymentAcceptedResponse paymentAcceptedResponse = new PaymentAcceptedResponse();
		paymentAcceptedResponse.setPaymentId((UUID.randomUUID()).toString());
		paymentAcceptedResponse.setStatus("Accepted");

		return paymentAcceptedResponse;

	}

}
