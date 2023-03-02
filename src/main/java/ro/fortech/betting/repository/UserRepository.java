package ro.fortech.betting.repository;

import org.springframework.stereotype.Component;

import ro.fortech.betting.dto.security.Role;
import ro.fortech.betting.dto.security.User;

@Component
public class UserRepository {

	 public User findByEmail(String email) {
		return User.builder().email("test@fortech.ro").password("$2a$12$A123.Gy4qDNaBOkqRkxktuBip2063fQk6lkvtf4F1W.VGALL4ojfO").role(Role.ADMIN).build();
		 
	 }
}
