package pl.com.bottega.simplelib2.model;

public class BookStatusException extends RuntimeException {

	private final BookStatus status;

	public BookStatusException(BookStatus status) {
		super();
		this.status = status;
	}

}
