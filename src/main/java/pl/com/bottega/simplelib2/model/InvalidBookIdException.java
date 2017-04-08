package pl.com.bottega.simplelib2.model;

public class InvalidBookIdException extends RuntimeException {

	private final BookId bookId;

	public InvalidBookIdException(BookId bookId) {
		super();
		this.bookId = bookId;
	}

	public BookId getBookId() {
		return bookId;
	}

}
