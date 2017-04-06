package pl.com.bottega.simplelib2.application;

import pl.com.bottega.simplelib2.application.user.BookDto;
import pl.com.bottega.simplelib2.application.user.BookSearchQuery;
import pl.com.bottega.simplelib2.application.user.BookSearchResults;

import java.util.UUID;

public interface BookCatalog {

	BookSearchResults find(BookSearchQuery query);

	BookDto get(UUID bookId);

}
