package pl.decerto.workshop.cloud.basic.user.domain;

import java.util.Optional;

public interface UserRepository {

	void save(User user);

	Optional<User> getById(String id);
}
