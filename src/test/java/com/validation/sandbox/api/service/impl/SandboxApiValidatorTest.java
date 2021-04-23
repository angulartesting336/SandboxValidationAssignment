package com.validation.sandbox.api.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.validation.sandbox.api.model.PaymentAcceptedResponse;
import com.validation.sandbox.api.model.PaymentInitiationRequest;

@ExtendWith(MockitoExtension.class)
public class SandboxApiValidatorTest {

	@InjectMocks
	private SandboxApiValidator sandboxApiValidationServiceImpl;

	@Mock
	CertificateNameValidator certificateNameValidator;

	@Mock
	SignatureValidator signatureValidator;

	@Mock
	PaymentRequestValidator paymentRequestValidator;

	@Mock
	LimitExceededValidator limitExceededValidator;

	private PaymentInitiationRequest paymentInitiationRequest = null;

	private PaymentAcceptedResponse paymentAcceptedResponse = null;

	private String signature = "AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==";

	private String signatureCertificate = "-----BEGIN CERTIFICATE-----MIIDwjCCAqoCCQDxVbCjIKynQjANBgkqhkiG9w0BAQsFADCBojELMAkGA1UEBhMCTkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoMCFJhYm9iYW5rMRMwEQYDVQQLDApBc3Nlc3NtZW50MSIwIAYDVQQDDBlTYW5kYm94LVRQUDpleGNlbGxlbnQgVFBQMSMwIQYJKoZIhvcNAQkBFhRuby1yZXBseUByYWJvYmFuay5ubDAeFw0yMDAxMzAxMzIyNDlaFw0yMTAxMjkxMzIyNDlaMIGiMQswCQYDVQQGEwJOTDEQMA4GA1UECAwHVXRyZWNodDEQMA4GA1UEBwwHVXRyZWNodDERMA8GA1UECgwIUmFib2JhbmsxEzARBgNVBAsMCkFzc2Vzc21lbnQxIjAgBgNVBAMMGVNhbmRib3gtVFBQOmV4Y2VsbGVudCBUUFAxIzAhBgkqhkiG9w0BCQEWFG5vLXJlcGx5QHJhYm9iYW5rLm5sMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAryLyouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAgMNxCKXiZCbmWHBYqh6lpPh+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt0LeKuxoaVWpInrB5FRzoEY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm32Wwg+FWY/StSKlox3QmEaxEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTqafvEQvND+IyLvZRqf0TSvIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9LkKPItYtSuMTzeSe9o0SmXZFgcEBh5DnETZqIVuQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQASFOkJiKQuL7fSErH6y5Uwj9WmmQLFnit85tjbo20jsqseTuZqLdpwBObiHxnBz7o3M73PJAXdoXkwiMVykZrlUSEy7+FsNZ4iFppoFapHDbfBgM2WMV7VS6NK17e0zXcTGySSRzXsxw0yEQGaOU8RJ3Rry0HWo9M/JmYFrdBPP/3sWAt/+O4th5Jyk8RajN3fHFCAoUz4rXxhUZkf/9u3Q038rRBvqaA+6c0uW58XqF/QyUxuTD4er9veCniUhwIX4XBsDNxIW/rwBRAxOUkG4V+XqrBb75lCyea1o/9HIaq1iIKI4Day0piMOgwPEg1wF383yd0x8hRW4zxyHcER-----END CERTIFICATE-----";

	private String xRequestId = "29318e25-cebd-498c-888a-f77672f66449";

	@BeforeEach
	public void setUp() {

		paymentInitiationRequest = new PaymentInitiationRequest();

		paymentAcceptedResponse = new PaymentAcceptedResponse();

		paymentAcceptedResponse.setPaymentId("29318e25-cebd-498c-888a-f77672f66449");
		paymentAcceptedResponse.setStatus("Accepted");

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384551");
	}

	@Test
	public void paymentRequestValidationSuccessfulTest() throws Exception {

		PaymentAcceptedResponse expected = new PaymentAcceptedResponse();

		expected.setStatus("Accepted");

		Mockito.lenient().doNothing().when(certificateNameValidator).validateCertificateName(signature,
				signatureCertificate, xRequestId);

		Mockito.lenient().doNothing().when(signatureValidator).validateSignature(paymentInitiationRequest, signature,
				signatureCertificate, xRequestId);

		Mockito.lenient().doNothing().when(paymentRequestValidator).validatePaymentRequest(paymentInitiationRequest,
				signature, signatureCertificate, xRequestId);

		Mockito.lenient().doNothing().when(limitExceededValidator).checkLimitExceeded(
				paymentInitiationRequest.getAmount(), paymentInitiationRequest.getDebtorIBAN(), signature,
				signatureCertificate, xRequestId);

		PaymentAcceptedResponse actual = sandboxApiValidationServiceImpl
				.validatePaymentRequestHeader(paymentInitiationRequest, signature, signatureCertificate, xRequestId);

		assertEquals(expected.getStatus(), actual.getStatus());

	}

}
