package com.validation.sandbox.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.validation.sandbox.api.model.PaymentInitiationRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class SandboxApiValidationApplicationIT {

	@Autowired
	private TestRestTemplate restTemplate;

	private PaymentInitiationRequest paymentInitiationRequest = null;

	@BeforeEach
	public void before() {

		paymentInitiationRequest = new PaymentInitiationRequest();

	}

	@Test
	public void paymentValidationRequestSuccessfulTest() throws Exception {

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384212");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Signature",
				"AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==");
		headers.set("SignatureCertificate",
				"-----BEGIN CERTIFICATE-----MIIDwjCCAqoCCQDxVbCjIKynQjANBgkqhkiG9w0BAQsFADCBojELMAkGA1UEBhMCTkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoMCFJhYm9iYW5rMRMwEQYDVQQLDApBc3Nlc3NtZW50MSIwIAYDVQQDDBlTYW5kYm94LVRQUDpleGNlbGxlbnQgVFBQMSMwIQYJKoZIhvcNAQkBFhRuby1yZXBseUByYWJvYmFuay5ubDAeFw0yMDAxMzAxMzIyNDlaFw0yMTAxMjkxMzIyNDlaMIGiMQswCQYDVQQGEwJOTDEQMA4GA1UECAwHVXRyZWNodDEQMA4GA1UEBwwHVXRyZWNodDERMA8GA1UECgwIUmFib2JhbmsxEzARBgNVBAsMCkFzc2Vzc21lbnQxIjAgBgNVBAMMGVNhbmRib3gtVFBQOmV4Y2VsbGVudCBUUFAxIzAhBgkqhkiG9w0BCQEWFG5vLXJlcGx5QHJhYm9iYW5rLm5sMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAryLyouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAgMNxCKXiZCbmWHBYqh6lpPh+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt0LeKuxoaVWpInrB5FRzoEY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm32Wwg+FWY/StSKlox3QmEaxEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTqafvEQvND+IyLvZRqf0TSvIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9LkKPItYtSuMTzeSe9o0SmXZFgcEBh5DnETZqIVuQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQASFOkJiKQuL7fSErH6y5Uwj9WmmQLFnit85tjbo20jsqseTuZqLdpwBObiHxnBz7o3M73PJAXdoXkwiMVykZrlUSEy7+FsNZ4iFppoFapHDbfBgM2WMV7VS6NK17e0zXcTGySSRzXsxw0yEQGaOU8RJ3Rry0HWo9M/JmYFrdBPP/3sWAt/+O4th5Jyk8RajN3fHFCAoUz4rXxhUZkf/9u3Q038rRBvqaA+6c0uW58XqF/QyUxuTD4er9veCniUhwIX4XBsDNxIW/rwBRAxOUkG4V+XqrBb75lCyea1o/9HIaq1iIKI4Day0piMOgwPEg1wF383yd0x8hRW4zxyHcER-----END CERTIFICATE-----");
		headers.set("XRequestId", "29318e25-cebd-498c-888a-f77672f66449");

		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(paymentInitiationRequest, headers);

		ResponseEntity<Object> response = restTemplate.exchange("/paymentValidation/v1.0.0/initiate-payment",
				HttpMethod.POST, entity, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void paymentValidationRequestUnknownCertificateTest() throws Exception {

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384551");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Signature",
				"AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==");
		headers.set("SignatureCertificate",
				"-----BEGIN CERTIFICATE-----MIIDBDCCAm2gAwIBAgIJAK8dGINfkSTHMA0GCSqGSIb3DQEBBQUAMGAxCzAJBgNVBAYTAlVTMQswCQYDVQQIEwJDQTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzETMBEGA1UEChMKR29vZ2xlIEluYzEXMBUGA1UEAxMOd3d3Lmdvb2dsZS5jb20wHhcNMDgxMDA4MDEwODMyWhcNMDkxMDA4MDEwODMyWjBgMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0ExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxEzARBgNVBAoTCkdvb2dsZSBJbmMxFzAVBgNVBAMTDnd3dy5nb29nbGUuY29tMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQUV7ukIfIixbokHONGMW9+ed0E9X4m99I8upPQp3iAtqIvWs7XCbAbGqzQH1qX9Y00hrQ5RRQj8OI3tRiQs/KfzGWOdvLpIk5oXpdT58tg4FlYh5fbhIoVoVn4GvtSjKmJFsoM8NRtEJHL1aWd++dXzkQjEsNcBXwQvfDb0YnbQIDAQABo4HFMIHCMB0GA1UdDgQWBBSm/h1pNY91bNfW08ac9riYzs3cxzCBkgYDVR0jBIGKMIGHgBSm/h1pNY91bNfW08ac9riYzs3cx6FkpGIwYDELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAkNBMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRMwEQYDVQQKEwpHb29nbGUgSW5jMRcwFQYDVQQDEw53d3cuZ29vZ2xlLmNvbYIJAK8dGINfkSTHMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEAYpHTr3vQNsHHHUm4MkYcDB20a5KvcFoXgCcYtmdyd8rh/FKeZm2me7eQCXgBfJqQ4dvVLJ4LgIQiU3R5ZDe0WbW7rJ3M9ADQFyQoRJP8OIMYW3BoMi0Z4E730KSLRh6kfLq4rK6vw7lkH9oynaHHWZSJLDAp17cPj+6znWkN9/g=-----END CERTIFICATE-----");
		headers.set("XRequestId", "29318e25-cebd-498c-888a-f77672f66449");

		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(paymentInitiationRequest, headers);

		ResponseEntity<Object> response = restTemplate.exchange("/paymentValidation/v1.0.0/initiate-payment",
				HttpMethod.POST, entity, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	public void paymentValidationRequestSignatureValidationFailedTest() throws Exception {

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384212");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Signature",
				"AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==");
		headers.set("SignatureCertificate",
				"-----BEGIN CERTIFICATE-----MIIDBDCCAm2gAwIBAgIJAK8dGINfkSTHMA0GCSqGSIb3DQEBBQUAMGAxCzAJBgNVBAYTAlVTMQswCQYDVQQIEwJDQTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzETMBEGA1UEChMKR29vZ2xlIEluYzEXMBUGA1UEAxMOd3d3Lmdvb2dsZS5jb20wHhcNMDgxMDA4MDEwODMyWhcNMDkxMDA4MDEwODMyWjBgMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0ExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxEzARBgNVBAoTCkdvb2dsZSBJbmMxFzAVBgNVBAMTDnd3dy5nb29nbGUuY29tMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQUV7ukIfIixbokHONGMW9+ed0E9X4m99I8upPQp3iAtqIvWs7XCbAbGqzQH1qX9Y00hrQ5RRQj8OI3tRiQs/KfzGWOdvLpIk5oXpdT58tg4FlYh5fbhIoVoVn4GvtSjKmJFsoM8NRtEJHL1aWd++dXzkQjEsNcBXwQvfDb0YnbQIDAQABo4HFMIHCMB0GA1UdDgQWBBSm/h1pNY91bNfW08ac9riYzs3cxzCBkgYDVR0jBIGKMIGHgBSm/h1pNY91bNfW08ac9riYzs3cx6FkpGIwYDELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAkNBMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRMwEQYDVQQKEwpHb29nbGUgSW5jMRcwFQYDVQQDEw53d3cuZ29vZ2xlLmNvbYIJAK8dGINfkSTHMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEAYpHTr3vQNsHHHUm4MkYcDB20a5KvcFoXgCcYtmdyd8rh/FKeZm2me7eQCXgBfJqQ4dvVLJ4LgIQiU3R5ZDe0WbW7rJ3M9ADQFyQoRJP8OIMYW3BoMi0Z4E730KSLRh6kfLq4rK6vw7lkH9oynaHHWZSJLDAp17cPj+6znWkN9/g=-----END CERTIFICATE-----");
		headers.set("XRequestId", "29318e25-cebd-498c-888a-f77672f66449");

		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(paymentInitiationRequest, headers);

		ResponseEntity<Object> response = restTemplate.exchange("/paymentValidation/v1.0.0/initiate-payment",
				HttpMethod.POST, entity, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	public void paymentValidationRequestFailedTest() throws Exception {

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NLNL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384551");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Signature",
				"AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==");
		headers.set("SignatureCertificate",
				"-----BEGIN CERTIFICATE-----MIIDwjCCAqoCCQDxVbCjIKynQjANBgkqhkiG9w0BAQsFADCBojELMAkGA1UEBhMCTkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoMCFJhYm9iYW5rMRMwEQYDVQQLDApBc3Nlc3NtZW50MSIwIAYDVQQDDBlTYW5kYm94LVRQUDpleGNlbGxlbnQgVFBQMSMwIQYJKoZIhvcNAQkBFhRuby1yZXBseUByYWJvYmFuay5ubDAeFw0yMDAxMzAxMzIyNDlaFw0yMTAxMjkxMzIyNDlaMIGiMQswCQYDVQQGEwJOTDEQMA4GA1UECAwHVXRyZWNodDEQMA4GA1UEBwwHVXRyZWNodDERMA8GA1UECgwIUmFib2JhbmsxEzARBgNVBAsMCkFzc2Vzc21lbnQxIjAgBgNVBAMMGVNhbmRib3gtVFBQOmV4Y2VsbGVudCBUUFAxIzAhBgkqhkiG9w0BCQEWFG5vLXJlcGx5QHJhYm9iYW5rLm5sMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAryLyouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAgMNxCKXiZCbmWHBYqh6lpPh+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt0LeKuxoaVWpInrB5FRzoEY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm32Wwg+FWY/StSKlox3QmEaxEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTqafvEQvND+IyLvZRqf0TSvIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9LkKPItYtSuMTzeSe9o0SmXZFgcEBh5DnETZqIVuQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQASFOkJiKQuL7fSErH6y5Uwj9WmmQLFnit85tjbo20jsqseTuZqLdpwBObiHxnBz7o3M73PJAXdoXkwiMVykZrlUSEy7+FsNZ4iFppoFapHDbfBgM2WMV7VS6NK17e0zXcTGySSRzXsxw0yEQGaOU8RJ3Rry0HWo9M/JmYFrdBPP/3sWAt/+O4th5Jyk8RajN3fHFCAoUz4rXxhUZkf/9u3Q038rRBvqaA+6c0uW58XqF/QyUxuTD4er9veCniUhwIX4XBsDNxIW/rwBRAxOUkG4V+XqrBb75lCyea1o/9HIaq1iIKI4Day0piMOgwPEg1wF383yd0x8hRW4zxyHcER-----END CERTIFICATE-----");
		headers.set("XRequestId", "29318e25-cebd-498c-888a-f77672f66449");

		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(paymentInitiationRequest, headers);

		ResponseEntity<Object> response = restTemplate.exchange("/paymentValidation/v1.0.0/initiate-payment",
				HttpMethod.POST, entity, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	public void paymentValidationRequestLimitExceededTest() throws Exception {

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384211");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Signature",
				"AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==");
		headers.set("SignatureCertificate",
				"-----BEGIN CERTIFICATE-----MIIDwjCCAqoCCQDxVbCjIKynQjANBgkqhkiG9w0BAQsFADCBojELMAkGA1UEBhMCTkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoMCFJhYm9iYW5rMRMwEQYDVQQLDApBc3Nlc3NtZW50MSIwIAYDVQQDDBlTYW5kYm94LVRQUDpleGNlbGxlbnQgVFBQMSMwIQYJKoZIhvcNAQkBFhRuby1yZXBseUByYWJvYmFuay5ubDAeFw0yMDAxMzAxMzIyNDlaFw0yMTAxMjkxMzIyNDlaMIGiMQswCQYDVQQGEwJOTDEQMA4GA1UECAwHVXRyZWNodDEQMA4GA1UEBwwHVXRyZWNodDERMA8GA1UECgwIUmFib2JhbmsxEzARBgNVBAsMCkFzc2Vzc21lbnQxIjAgBgNVBAMMGVNhbmRib3gtVFBQOmV4Y2VsbGVudCBUUFAxIzAhBgkqhkiG9w0BCQEWFG5vLXJlcGx5QHJhYm9iYW5rLm5sMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAryLyouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAgMNxCKXiZCbmWHBYqh6lpPh+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt0LeKuxoaVWpInrB5FRzoEY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm32Wwg+FWY/StSKlox3QmEaxEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTqafvEQvND+IyLvZRqf0TSvIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9LkKPItYtSuMTzeSe9o0SmXZFgcEBh5DnETZqIVuQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQASFOkJiKQuL7fSErH6y5Uwj9WmmQLFnit85tjbo20jsqseTuZqLdpwBObiHxnBz7o3M73PJAXdoXkwiMVykZrlUSEy7+FsNZ4iFppoFapHDbfBgM2WMV7VS6NK17e0zXcTGySSRzXsxw0yEQGaOU8RJ3Rry0HWo9M/JmYFrdBPP/3sWAt/+O4th5Jyk8RajN3fHFCAoUz4rXxhUZkf/9u3Q038rRBvqaA+6c0uW58XqF/QyUxuTD4er9veCniUhwIX4XBsDNxIW/rwBRAxOUkG4V+XqrBb75lCyea1o/9HIaq1iIKI4Day0piMOgwPEg1wF383yd0x8hRW4zxyHcER-----END CERTIFICATE-----");
		headers.set("XRequestId", "29318e25-cebd-498c-888a-f77672f66449");

		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(paymentInitiationRequest, headers);

		ResponseEntity<Object> response = restTemplate.exchange("/paymentValidation/v1.0.0/initiate-payment",
				HttpMethod.POST, entity, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

	}

	@Test
	public void paymentValidationRequestGeneralErrorTest() throws Exception {

		paymentInitiationRequest.setAmount("1.00");
		paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
		paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384211");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Signature",
				"AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==");
		headers.set("SignatureCertificate",
				"-----BEGIN CERTIFICATE-----MIIDw++98jCCAqoCCQDxVbCjIKynQjANBgkqhkiG9w0BAQsFADCBojELMAkGA1UEBhMCTkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoMCFJhYm9iYW5rMRMwEQYDVQQLDApBc3Nlc3NtZW50MSIwIAYDVQQDDBlTYW5kYm94LVRQUDpleGNlbGxlbnQgVFBQMSMwIQYJKoZIhvcNAQkBFhRuby1yZXBseUByYWJvYmFuay5ubDAeFw0yMDAxMzAxMzIyNDlaFw0yMTAxMjkxMzIyNDlaMIGiMQswCQYDVQQGEwJOTDEQMA4GA1UECAwHVXRyZWNodDEQMA4GA1UEBwwHVXRyZWNodDERMA8GA1UECgwIUmFib2JhbmsxEzARBgNVBAsMCkFzc2Vzc21lbnQxIjAgBgNVBAMMGVNhbmRib3gtVFBQOmV4Y2VsbGVudCBUUFAxIzAhBgkqhkiG9w0BCQEWFG5vLXJlcGx5QHJhYm9iYW5rLm5sMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAryLyouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAgMNxCKXiZCbmWHBYqh6lpPh+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt0LeKuxoaVWpInrB5FRzoEY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm32Wwg+FWY/StSKlox3QmEaxEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTqafvEQvND+IyLvZRqf0TSvIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9LkKPItYtSuMTzeSe9o0SmXZFgcEBh5DnETZqIVuQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQASFOkJiKQuL7fSErH6y5Uwj9WmmQLFnit85tjbo20jsqseTuZqLdpwBObiHxnBz7o3M73PJAXdoXkwiMVykZrlUSEy7+FsNZ4iFppoFapHDbfBgM2WMV7VS6NK17e0zXcTGySSRzXsxw0yEQGaOU8RJ3Rry0HWo9M/JmYFrdBPP/3sWAt/+O4th5Jyk8RajN3fHFCAoUz4rXxhUZkf/9u3Q038rRBvqaA+6c0uW58XqF/QyUxuTD4er9veCniUhwIX4XBsDNxIW/rwBRAxOUkG4V+XqrBb75lCyea1o/9HIaq1iIKI4Day0piMOgwPEg1wF383yd0x8hRW4zxyHcER-----END CERTIFICATE-----");
		headers.set("XRequestId", "29318e25-cebd-498c-888a-f77672f66449");

		HttpEntity<PaymentInitiationRequest> entity = new HttpEntity<>(paymentInitiationRequest, headers);

		ResponseEntity<Object> response = restTemplate.exchange("/paymentValidation/v1.0.0/initiate-payment",
				HttpMethod.POST, entity, Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
