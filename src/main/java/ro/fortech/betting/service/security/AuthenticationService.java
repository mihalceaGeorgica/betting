package ro.fortech.betting.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.fortech.betting.dto.security.AuthenticationRequest;
import ro.fortech.betting.dto.security.AuthenticationResponse;
import ro.fortech.betting.dto.security.RegisterRequest;
import ro.fortech.betting.dto.security.Role;
import ro.fortech.betting.dto.security.User;
import ro.fortech.betting.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.ADMIN)
				.build();

		String jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = repository.findByEmail(request.getEmail());
		String jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}