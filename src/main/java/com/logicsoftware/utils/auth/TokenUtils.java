package com.logicsoftware.utils.auth;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

public class TokenUtils {
	
	public static String getToken(String username, Set<String> roles, Long duration, String issuer) {
		try {
			String privateKeyLocation = "/privatekey.pem";
			PrivateKey privateKey = readPrivateKey(privateKeyLocation);

			JwtClaimsBuilder claimsBuilder = Jwt.claims();
			long currentTimeInSecs = currentTimeInSecs();

			Set<String> groups = new HashSet<>();
			for (String role : roles)
				groups.add(role);

			claimsBuilder.issuer(issuer);
			claimsBuilder.subject(username);
			claimsBuilder.issuedAt(currentTimeInSecs);
			claimsBuilder.expiresAt(currentTimeInSecs + duration);
			claimsBuilder.groups(groups);


			return claimsBuilder.jws().keyId(privateKeyLocation).sign(privateKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getRefreshToken(String username, Long duration, String issuer) {
		try {
			String privateKeyLocation = "/privatekey.pem";
			PrivateKey privateKey = readPrivateKey(privateKeyLocation);

			JwtClaimsBuilder claimsBuilder = Jwt.claims();
			long currentTimeInSecs = currentTimeInSecs();

			claimsBuilder.issuer(issuer);
			claimsBuilder.subject(username);
			claimsBuilder.issuedAt(currentTimeInSecs);
			claimsBuilder.expiresAt(currentTimeInSecs + duration);

			return claimsBuilder.jws().keyId(privateKeyLocation).sign(privateKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static PrivateKey readPrivateKey(final String pemResName) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException  {
		try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)) {
			byte[] tmp = new byte[4096];
			int length = contentIS.read(tmp);
			return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
		}
	}

	private static PublicKey readPublicKey(final String pemResName) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException  {
		try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)) {
			byte[] tmp = new byte[4096];
			int length = contentIS.read(tmp);
			return decodePublicKey(new String(tmp, 0, length, "UTF-8"));
		}
	}

	private static PrivateKey decodePrivateKey(final String pemEncoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] encodedBytes = toEncodedBytes(pemEncoded);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(keySpec);
	}

	private static PublicKey decodePublicKey(final String pem) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] encodedBytes = toEncodedBytes(pem);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(keySpec);
	}

	private static byte[] toEncodedBytes(final String pemEncoded) {
		final String normalizedPem = removeBeginEnd(pemEncoded);
		return Base64.getDecoder().decode(normalizedPem);
	}

	private static String removeBeginEnd(String pem) {
		pem = pem.replaceAll("-----BEGIN (.*)-----", "");
		pem = pem.replaceAll("-----END (.*)----", "");
		pem = pem.replaceAll("\r\n", "");
		pem = pem.replaceAll("\n", "");
		return pem.trim();
	}

	private static int currentTimeInSecs() {
		long currentTimeMS = System.currentTimeMillis();
		return (int) (currentTimeMS / 1000);
	}

	public static String getSubject(String refreshToken) {
		try {
			String publicKeyLocation = "/publickey.pem";
			PublicKey publicKey = readPublicKey(publicKeyLocation);

			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			.setVerificationKey(publicKey)
			.setRequireExpirationTime()
			.setAllowedClockSkewInSeconds(30)
			.setRequireSubject()
			.setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256)
			.build();

			JwtClaims jwtClaims = jwtConsumer.processToClaims(refreshToken);

			return jwtClaims.getSubject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
