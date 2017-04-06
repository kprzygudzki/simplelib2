package pl.com.bottega.simplelib2.model;

public interface BookRepository {

	void put(Book book);

	Book get(BookId bookId);

}
