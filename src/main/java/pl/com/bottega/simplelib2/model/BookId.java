package pl.com.bottega.simplelib2.model;

import java.io.Serializable;
import java.util.UUID;

public class BookId implements Serializable {

	private UUID id;

	BookId() {
	}

	private BookId(UUID id) {
		this.id = id;
	}

	static BookId generate() {
		return new BookId(UUID.randomUUID());
	}

	void setId(UUID id) {
		this.id = id;
	}
	public static BookId parse(String id) {
		return new BookId(UUID.fromString(id));
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
