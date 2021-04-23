package com.validation.sandbox.api.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.validation.sandbox.api.exception.InvalidCertificateException;
import com.validation.sandbox.api.model.PaymentRejectedResponse;

@Service
public class CertificateNameValidator {

	public void validateCertificateName(String signature, String signatureCertificate, String xRequestId)
			throws IOException, GeneralSecurityException {

		X509Certificate certificate = generateCertificate(signatureCertificate);

		if (!getCommonName(certificate).startsWith("Sandbox-TPP")) {

			throw new InvalidCertificateException(
					new PaymentRejectedResponse("Got rejected due to Unknown Certificate CN", "UNKNOWN_CERTIFICATE",
							"Rejected"),
					signatureCertificate, signature, xRequestId);
		}

	}

	public X509Certificate generateCertificate(String signatureCertificate) throws CertificateException {

		signatureCertificate = signatureCertificate.replaceAll("-----BEGIN CERTIFICATE-----", "");
		signatureCertificate = signatureCertificate.replaceAll("-----END CERTIFICATE-----", "");

		CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");

		byte[] bytes = Base64.decodeBase64(signatureCertificate);

		return (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(bytes));

	}

	private String getCommonName(X509Certificate certificate) {
		String name = certificate.getSubjectX500Principal().getName();
		int start = name.indexOf("CN=");
		int end = name.indexOf(",", start);
		if (end == -1) {
			end = name.length();
		}
		return name.substring(start + 3, end);
	}

}
