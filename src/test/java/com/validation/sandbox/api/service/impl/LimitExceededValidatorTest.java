package com.validation.sandbox.api.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.validation.sandbox.api.exception.LimitExceededException;

@ExtendWith(MockitoExtension.class)
public class LimitExceededValidatorTest {

	@InjectMocks
	private LimitExceededValidator limitExceededValidator;

	private String amount = null;

	private String debtorIban = null;

	@BeforeEach
	public void setUp() {

		amount = "100.00";

		debtorIban = "NL02RABO7134384112";

	}

	@Test
	public void paymentValidationLimitExceededTest() {

		assertThrows(LimitExceededException.class, () -> limitExceededValidator.checkLimitExceeded(amount, debtorIban,
				"signatureCertificate", "signature", "xRequestId"));

	}

	@Test
	public void paymentValidationLimitNotExceededTest() {

		debtorIban = "NL02RABO7134384113";

		LimitExceededValidator limitExceededValidator = mock(LimitExceededValidator.class);

		Mockito.lenient().doNothing().when(limitExceededValidator).checkLimitExceeded(amount, debtorIban,
				"signatureCertificate", "signature", "xRequestId");

	}

}
