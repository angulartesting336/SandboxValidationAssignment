package com.validation.sandbox.api.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.validation.sandbox.api.exception.LimitExceededException;
import com.validation.sandbox.api.model.PaymentRejectedResponse;

@Service
public class LimitExceededValidator {

	public void checkLimitExceeded(String amount, String debtorIban, String signature, String signatureCertificate,
			String xRequestId) {

		if ((Double.parseDouble(amount) > 0)
				&& (Arrays.stream(debtorIban.replaceAll("[^0-9]", "").replaceAll("\\B", " ").split(" "))
						.mapToInt(Integer::parseInt).sum() % debtorIban.length() == 0)) {

			throw new LimitExceededException(new PaymentRejectedResponse("Got rejected due to Amount limit exceeded",
					"LIMIT_EXCEEDED", "Rejected"), signatureCertificate, signature, xRequestId);

		}

	}

}
