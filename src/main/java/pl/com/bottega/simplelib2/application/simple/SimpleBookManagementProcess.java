package pl.com.bottega.simplelib2.application.simple;

import pl.com.bottega.simplelib2.application.BookManagementProcess;
import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.BookRepository;
import pl.com.bottega.simplelib2.model.commands.CreateBookCommand;
import pl.com.bottega.simplelib2.model.commands.RemoveBookCommand;

public class SimpleBookManagementProcess implements BookManagementProcess {

	private BookRepository bookRepository;

	public SimpleBookManagementProcess(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public BookId add(CreateBookCommand command) {
		Book book = new Book(command);
		bookRepository.put(book);
		return book.getId();
	}

	public void remove(RemoveBookCommand command) {
		Book book = bookRepository.get(BookId.parse(command.getBookId()));
		book.remove(command);
	}

}
