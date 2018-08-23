package pl.decerto.workshop.cloud.basic.user.api;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.decerto.workshop.cloud.basic.user.domain.User;
import pl.decerto.workshop.cloud.basic.user.domain.UserRepository;

@RestController
@RequestMapping("/user")
class UserEndpoint {

	private final UserRepository userRepository;

	UserEndpoint(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping()
	ResponseEntity addUser(@RequestBody NewUserDto newUserDto) {
		User newUser = new User(newUserDto.getName());
		userRepository.save(newUser);

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(newUser.getId())
			.toUri();

		return ResponseEntity.created(location).build();

	}

	@GetMapping(path = "/{id}")
	ResponseEntity<User> getUser(@PathVariable String id) {
		return userRepository.getById(id)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
