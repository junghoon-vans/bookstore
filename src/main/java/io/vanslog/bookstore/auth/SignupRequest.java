package io.vanslog.bookstore.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Request for signup
 *
 * @param username must not be {@literal blank}.
 * @param email must not be {@literal blank}.
 * @param password must not be {@literal blank}.
 * @param role can be {@literal blank}.
 */
// @formatter:off
public record SignupRequest(

			@NotBlank
			String username,

			@NotBlank @Email
			String email,

			@NotBlank
			String password,

			Set<String> role
) {
}
