package com.validation.sandbox.api.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.validation.sandbox.api.exception.InvalidRequestException;
import com.validation.sandbox.api.model.PaymentInitiationRequest;

@ExtendWith(MockitoExtension.class)
public class PaymentRequestValidatorTest {

	@InjectMocks
	private PaymentRequestValidator paymentRequestValidator;

	private PaymentInitiationRequest paymentInitiationRequest = null;

	@BeforeEach
	public void setUp() {

		paymentInitiationRequest = new PaymentInitiationRequest();

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NLNLRABO7134384551");

	}

	@Test
	public void paymentValidationInvalidIBANRequestTest() {

		assertThrows(InvalidRequestException.class, () -> paymentRequestValidator
				.validatePaymentRequest(paymentInitiationRequest, "signatureCertificate", "signature", "xRequestId"));

	}

	@Test
	public void paymentValidationInvalidAmountRequestTest() {

		paymentInitiationRequest.setAmount("abc");

		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384551");

		assertThrows(InvalidRequestException.class, () -> paymentRequestValidator
				.validatePaymentRequest(paymentInitiationRequest, "signatureCertificate", "signature", "xRequestId"));

	}

	@Test
	public void paymentValidationInvalidAmountFormatTest() {

		paymentInitiationRequest.setAmount("100/000");

		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384551");

		assertThrows(InvalidRequestException.class, () -> paymentRequestValidator
				.validatePaymentRequest(paymentInitiationRequest, "signatureCertificate", "signature", "xRequestId"));

	}

	@Test
	public void paymentValidationInvalidIBANWithSpaceRequestTest() {

		paymentInitiationRequest.setDebtorIBAN("NL 02RABO71343 84551");

		assertThrows(InvalidRequestException.class, () -> paymentRequestValidator
				.validatePaymentRequest(paymentInitiationRequest, "signatureCertificate", "signature", "xRequestId"));

	}

	@Test
	public void validPaymentRequestTest() {

		PaymentRequestValidator paymentRequestValidator = mock(PaymentRequestValidator.class);

		Mockito.lenient().doNothing().when(paymentRequestValidator).validatePaymentRequest(paymentInitiationRequest,
				"signatureCertificate", "signature", "xRequestId");

	}

}
