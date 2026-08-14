package com.streamdeck.streamdeck.security;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

	private final RSAPrivateKey privateKey;
	private final RSAPublicKey publicKey;
	private final long expirationMs;

	public JwtService(
			@Value("${jwt.private-key-path}") Resource privateKeyResource,
			@Value("${jwt.public-key-path}") Resource publicKeyResource,
			@Value("${jwt.expiration-ms}") long expirationMs) {
		this.privateKey = loadPrivateKey(privateKeyResource);
		this.publicKey = loadPublicKey(publicKeyResource);
		this.expirationMs = expirationMs;
	}

	public String generateToken(Usuario usuario) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMs);

		return Jwts.builder()
				.subject(usuario.getCcorreo())
				.issuedAt(now)
				.expiration(expiry)
				.signWith(privateKey, Jwts.SIG.RS256)
				.compact();
	}

	public String extractCorreo(String token) {
		return parseClaims(token).getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			Claims claims = parseClaims(token);
			return claims.getSubject() != null && claims.getExpiration().after(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(publicKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private RSAPrivateKey loadPrivateKey(Resource resource) {
		try {
			String pem = readPem(resource);
			pem = pem
					.replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "")
					.replaceAll("\\s", "");
			byte[] decoded = Base64.getDecoder().decode(pem);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new IllegalStateException("No se pudo cargar la clave privada JWT", e);
		}
	}

	private RSAPublicKey loadPublicKey(Resource resource) {
		try {
			String pem = readPem(resource);
			pem = pem
					.replace("-----BEGIN PUBLIC KEY-----", "")
					.replace("-----END PUBLIC KEY-----", "")
					.replaceAll("\\s", "");
			byte[] decoded = Base64.getDecoder().decode(pem);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new IllegalStateException("No se pudo cargar la clave pública JWT", e);
		}
	}

	private String readPem(Resource resource) throws Exception {
		try (InputStream inputStream = resource.getInputStream()) {
			return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}
}
