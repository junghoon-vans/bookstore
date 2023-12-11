package io.vanslog.bookstore.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for generating and validating JWT tokens.
 */
@Component
public class JwtUtils {

	private static final Log log = LogFactory.getLog(JwtUtils.class);

	@Value("${bookstore.app.jwt.secret}")
	private String jwtSecret;

	@Value("${bookstore.app.jwt.expiration-in-ms}")
	private int jwtExpirationMs;

	/**
	 * Generate JWT token from authentication object.
	 * @param authentication Authentication object
	 * @return JWT token
	 */
	public String generateJwtToken(Authentication authentication) {

		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Date currentDate = new Date();

		return Jwts.builder()
			.subject((userPrincipal.getUsername()))
			.issuedAt(currentDate)
			.expiration(getExpirationDate(currentDate, jwtExpirationMs))
			.signWith(createSecretKey(jwtSecret), Jwts.SIG.HS256)
			.compact();
	}

	/**
	 * Get JWS(JSON Web Signature) from JWT token.
	 * @param jwtToken JWT token
	 * @return JWS
	 */
	@Nullable
	public Jws<Claims> getJwsFrom(String jwtToken) {
		try {
			return Jwts.parser().verifyWith(createSecretKey(jwtSecret)).build().parseSignedClaims(jwtToken);
		}
		catch (MalformedJwtException e) {
			log.error("Invalid JWT token: %s".formatted(e.getMessage()));
		}
		catch (ExpiredJwtException e) {
			log.error("JWT token is expired: %s".formatted(e.getMessage()));
		}
		catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: %s".formatted(e.getMessage()));
		}
		catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: %s".formatted(e.getMessage()));
		}

		return null;
	}

	private Date getExpirationDate(Date base, int expirationInMs) {
		return new Date(base.getTime() + expirationInMs);
	}

	private SecretKey createSecretKey(String secret) {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

}
