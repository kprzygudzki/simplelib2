package pl.com.bottega.simplelib2.application.user;

import pl.com.bottega.simplelib2.model.BookExporter;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.BookStatus;

import java.time.Year;

public class BookDtoBuilder implements BookExporter {

	BookDto bookDto = new BookDto();

	@Override
	public void addId(BookId id) {
		bookDto.setId(id.toString());
	}

	@Override
	public void addStatus(BookStatus status) {
		bookDto.setStatus(status.toString());
	}

	@Override
	public void addTitle(String title) {
		bookDto.setTitle(title);
	}

	@Override
	public void addYear(Year year) {
		bookDto.setYear(year);
	}

	@Override
	public void addAuthor(String author) {
		bookDto.setAuthor(author);
	}

	@Override
	public void addBorrower(String borrower) {
		bookDto.setBorrower(borrower);
	}

	public BookDto build() {
		return bookDto;
	}

}
