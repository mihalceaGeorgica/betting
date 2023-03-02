package ro.fortech.betting.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ro.fortech.betting.dto.security.Token;

@Component
public class TokenRepository {
	List<Token> findAllValidTokenByUser(Integer id) {
		return new ArrayList<>();
	}

	public Token findByToken(String jwt) {
 
		return new Token();
	}
}
