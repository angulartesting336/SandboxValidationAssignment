package com.validation.sandbox.api.service.impl;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.validation.sandbox.api.exception.InvalidSignatureException;
import com.validation.sandbox.api.model.PaymentInitiationRequest;
import com.validation.sandbox.api.model.PaymentRejectedResponse;

@Service
public class SignatureValidator {

	@Autowired
	CertificateNameValidator certificateNameValidator;

	public void validateSignature(PaymentInitiationRequest paymentInitiationRequest, String signature,
			String signatureCertificate, String xRequestId) throws CertificateException, GeneralSecurityException {

		X509Certificate certificate = certificateNameValidator.generateCertificate(signatureCertificate);

		Signature signatureObj = Signature.getInstance("SHA256withRSA");

		signatureObj.initVerify(certificate.getPublicKey());
		JSONObject json = new JSONObject(paymentInitiationRequest);

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.update(json.toString().getBytes());

		byte[] data = md.digest();
		signatureObj.update(xRequestId.getBytes());
		signatureObj.update(data);

		if (signatureObj.verify(Base64.decodeBase64(signature))) {

			throw new InvalidSignatureException(
					new PaymentRejectedResponse("Got rejected due to signature  validation failure",
							"INVALID_SIGNATURE", "Rejected"),
					signatureCertificate, signature, xRequestId);

		}

	}

}