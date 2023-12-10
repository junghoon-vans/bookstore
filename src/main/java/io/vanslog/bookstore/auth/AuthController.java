package io.vanslog.bookstore.auth;

import io.vanslog.bookstore.auth.request.LoginRequest;
import io.vanslog.bookstore.auth.request.SignupRequest;
import io.vanslog.bookstore.global.response.JwtToken;
import io.vanslog.bookstore.global.response.Message;
import io.vanslog.bookstore.user.User;
import io.vanslog.bookstore.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<Message> signup(@RequestBody @Valid SignupRequest request) {

		if (userService.existsByUsername(request.username())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Username is already taken!"));
		}

		if (userService.existsByEmail(request.email())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Email is already in use!"));
		}

		User savedUser = userService.createUser(request);

		if (savedUser == null) {
			return ResponseEntity.internalServerError().body(new Message("Unknown error occurred. Please try again!"));
		}

		return ResponseEntity.ok(new Message("User registered successfully!"));
	}

	@PostMapping("/login")
	public ResponseEntity<JwtToken> login(@RequestBody @Valid LoginRequest request) {

		JwtToken jwtToken = userService.authenticate(request);
		return ResponseEntity.ok(jwtToken);
	}

}