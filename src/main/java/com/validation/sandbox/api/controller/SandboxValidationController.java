package com.validation.sandbox.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.validation.sandbox.api.model.PaymentAcceptedResponse;
import com.validation.sandbox.api.model.PaymentInitiationRequest;
import com.validation.sandbox.api.service.SandboxValidationService;

@RestController
@RequestMapping(value = "/paymentValidation")
public class SandboxValidationController {

	@Autowired
	SandboxValidationService sandboxValidationService;

	@PostMapping(value = "/v1.0.0/initiate-payment")
	public ResponseEntity<Object> validatePaymentRequestHeader(@RequestBody PaymentInitiationRequest paymentInitiationRequest,
			@RequestHeader(value = "Signature", required = true) String signature,
			@RequestHeader(value = "SignatureCertificate", required = true) String signatureCertificate,
			@RequestHeader(value = "XRequestId", required = true) String xRequestId)
			throws IOException, GeneralSecurityException {

		PaymentAcceptedResponse response = sandboxValidationService.validatePaymentRequestHeader(paymentInitiationRequest,
				signature, signatureCertificate, xRequestId);
		HttpHeaders headers = new HttpHeaders();

		headers.add("Signature", new String(Base64.encodeBase64(generateSignature(response).sign())));
		headers.add("Signature-Certificate", signatureCertificate);
		headers.add("X-Request-Id", xRequestId);

		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
	}

	private Signature generateSignature(PaymentAcceptedResponse response) throws GeneralSecurityException {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		PrivateKey privateKey = keyPair.getPrivate();
		Signature signature = Signature.getInstance("SHA256withRSA");

		signature.initSign(privateKey);

		JSONObject jsonObject = new JSONObject(response);

		byte[] bytes = jsonObject.toString().getBytes();
		signature.update(bytes);

		return signature;

	}

}
