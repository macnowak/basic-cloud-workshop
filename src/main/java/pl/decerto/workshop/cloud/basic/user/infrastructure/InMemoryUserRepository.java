package pl.decerto.workshop.cloud.basic.user.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import pl.decerto.workshop.cloud.basic.user.domain.User;
import pl.decerto.workshop.cloud.basic.user.domain.UserRepository;

public class InMemoryUserRepository implements UserRepository {

	private final Map<String, User> db = new HashMap<>();

	@Override
	public void save(User user) {
		db.put(user.getId(), user);
	}

	@Override
	public Optional<User> getById(String id) {
		return Optional.ofNullable(db.get(id));
	}
}
