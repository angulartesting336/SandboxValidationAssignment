package com.validation.sandbox.api.service.impl;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.validation.sandbox.api.exception.InvalidRequestException;
import com.validation.sandbox.api.model.PaymentInitiationRequest;
import com.validation.sandbox.api.model.PaymentRejectedResponse;

@Service
public class PaymentRequestValidator {

	public void validatePaymentRequest(PaymentInitiationRequest paymentInitiationRequest, String signature,
			String signatureCertificate, String xRequestId) {

		Stream<String> requestObjects = Stream.of(paymentInitiationRequest.getCreditorIBAN(),
				paymentInitiationRequest.getDebtorIBAN(), paymentInitiationRequest.getAmount());

		if (!formatValidator(requestObjects).isEmpty()) {

			throw new InvalidRequestException(
					new PaymentRejectedResponse("Got rejected due to IBAN or Amount validation failed",
							"INVALID_REQUEST", "Rejected"),
					signatureCertificate, signature, xRequestId);

		}

	}

	private List<String> formatValidator(Stream<String> requestObjects) {

		return requestObjects
				.filter(requestObject -> (!requestObject.contains(".")
						&& !Pattern.matches("[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}", requestObject))
						|| (requestObject.contains(".") && !Pattern.matches("-?[0-9]+(.[0-9]{1,3})?", requestObject)))
				.collect(Collectors.toList());

	}

}
