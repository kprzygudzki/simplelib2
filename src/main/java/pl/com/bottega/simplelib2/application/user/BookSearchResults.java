package pl.com.bottega.simplelib2.application.user;

import java.util.List;

public class BookSearchResults {

	private List<BookDto> bookDtos;

	public void setBookDtos(List<BookDto> bookDtos) {
		this.bookDtos = bookDtos;
	}

	public List<BookDto> getBookDtos() {
		return bookDtos;
	}

}
