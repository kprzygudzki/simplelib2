package pl.com.bottega.simplelib2.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.com.bottega.simplelib2.application.BookBorrowingProcess;
import pl.com.bottega.simplelib2.application.BookCatalog;
import pl.com.bottega.simplelib2.application.BookManagementProcess;
import pl.com.bottega.simplelib2.application.simple.SimpleBookBorrowingProcess;
import pl.com.bottega.simplelib2.application.simple.SimpleBookManagementProcess;
import pl.com.bottega.simplelib2.model.BookRepository;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public BookManagementProcess bookManagementProcess(BookRepository bookRepository) {
		return new SimpleBookManagementProcess(bookRepository);
	}

	@Bean
	public BookBorrowingProcess bookBorrowingProcess(BookRepository bookRepository) {
		return new SimpleBookBorrowingProcess(bookRepository);
	}

	@Bean
	public BookRepository bookRepository() {
		return new JPABookRepository();
	}

	@Bean
	public BookCatalog bookCatalog() {
		return new JPABookCatalog();
	}

}
