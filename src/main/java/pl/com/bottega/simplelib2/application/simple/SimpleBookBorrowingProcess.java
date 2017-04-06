package pl.com.bottega.simplelib2.application.simple;

import pl.com.bottega.simplelib2.application.BookBorrowingProcess;
import pl.com.bottega.simplelib2.infrastructure.JPABookRepository;
import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.commands.BorrowCommand;
import pl.com.bottega.simplelib2.model.commands.TurnBackCommand;

public class SimpleBookBorrowingProcess implements BookBorrowingProcess {

	private JPABookRepository bookRepository;

	@Override
	public void borrow(BorrowCommand command) {
		Book book = bookRepository.get(BookId.parse(command.getBookId()));
		book.borrow(command);
	}

	@Override
	public void turnBack(TurnBackCommand command) {
		Book book = bookRepository.get(BookId.parse(command.getBookId()));
		book.turnBack(command);
	}

}
