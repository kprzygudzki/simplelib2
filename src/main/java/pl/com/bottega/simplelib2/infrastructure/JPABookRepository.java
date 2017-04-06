package pl.com.bottega.simplelib2.infrastructure;

import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.BookRepository;

public class JPABookRepository implements BookRepository {

	@Override
	public void put(Book book) {
	}

	@Override
	public Book get(BookId bookId) {
		return null;
	}

}
