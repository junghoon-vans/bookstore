package io.vanslog.bookstore.auth.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request for login
 *
 * @param username must not be {@literal blank}.
 * @param password must not be {@literal blank}.
 */
// @formatter:off
public record LoginRequest(

		@NotBlank
		String username,

		@NotBlank
		String password
) {
}
