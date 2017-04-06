package pl.com.bottega.simplelib2.model;

import java.util.UUID;

public class BookId {

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

}
