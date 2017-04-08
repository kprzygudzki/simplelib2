package pl.com.bottega.simplelib2.infrastructure;

import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.BookRepository;
import pl.com.bottega.simplelib2.model.InvalidBookIdException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPABookRepository implements BookRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void put(Book book) {
		entityManager.persist(book);
	}

	@Override
	public Book get(BookId bookId) {
		Book book = entityManager.find(Book.class, bookId);
		if (book == null)
			throw new InvalidBookIdException(bookId);
		return book;
	}

}
