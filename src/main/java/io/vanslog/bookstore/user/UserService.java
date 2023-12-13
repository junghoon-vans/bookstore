package io.vanslog.bookstore.user;

import io.vanslog.bookstore.auth.LoginRequest;
import io.vanslog.bookstore.auth.SignupRequest;
import io.vanslog.bookstore.global.response.JwtToken;
import io.vanslog.bookstore.role.Role;
import io.vanslog.bookstore.role.RoleName;
import io.vanslog.bookstore.role.RoleRepository;
import io.vanslog.bookstore.security.jwt.JwtUtils;
import java.util.HashSet;
import java.util.Set;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final AuthenticationManager authManager;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtils jwtUtils;

	public UserService(AuthenticationManager authManager, UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {

		this.authManager = authManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}

	public boolean existsByUsername(String username) {

		return userRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {

		return userRepository.existsByEmail(email);
	}

	@Transactional
	public User createUser(SignupRequest signupRequest) {

		User toSave = new User(signupRequest.username(), signupRequest.email(),
				passwordEncoder.encode(signupRequest.password()));
		Set<Role> roles = roleSetFrom(signupRequest.role());

		toSave.setRoles(roles);
		return userRepository.save(toSave);
	}

	public JwtToken authenticate(LoginRequest loginRequest) {

		Authentication authentication = authManager
			.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		return new JwtToken(jwt);
	}

	private Set<Role> roleSetFrom(@Nullable Set<String> strRoles) {

		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			addRole(roles, RoleName.ROLE_USER);
			return roles;
		}

		strRoles.forEach(role -> {
			switch (role) {
				case "admin" -> addRole(roles, RoleName.ROLE_ADMIN);
				case "user" -> addRole(roles, RoleName.ROLE_USER);
				default -> throw new DataAccessResourceFailureException("Role is not found.");
			}
		});

		return roles;
	}

	private void addRole(Set<Role> roles, RoleName roleName) {
		Role userRole = roleRepository.findByName(roleName)
			.orElseThrow(() -> new DataAccessResourceFailureException("Role is not found."));
		roles.add(userRole);
	}

}
