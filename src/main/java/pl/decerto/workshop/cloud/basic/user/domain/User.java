package pl.decerto.workshop.cloud.basic.user.domain;

import java.util.UUID;
import lombok.Data;

@Data
public class User {

	private String id;
	private String name;

	public User(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}
}
