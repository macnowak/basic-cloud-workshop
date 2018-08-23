package pl.decerto.workshop.cloud.basic.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.decerto.workshop.cloud.basic.user.domain.UserRepository;
import pl.decerto.workshop.cloud.basic.user.infrastructure.InMemoryUserRepository;

@Configuration
class UserModule {

	@Bean
	public UserRepository userRepository() {
		return new InMemoryUserRepository();
	}

}
