package pl.com.bottega.simplelib2.application;

import pl.com.bottega.simplelib2.application.user.BookDto;
import pl.com.bottega.simplelib2.application.user.BookQuery;
import pl.com.bottega.simplelib2.application.user.BookSearchResults;
import pl.com.bottega.simplelib2.model.BookId;

public interface BookCatalog {

	BookSearchResults list();

	BookSearchResults find(BookQuery query);

	BookDto get(BookId bookId);

}
