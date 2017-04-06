package pl.com.bottega.simplelib2.model;

import pl.com.bottega.simplelib2.model.commands.BorrowCommand;
import pl.com.bottega.simplelib2.model.commands.CreateBookCommand;
import pl.com.bottega.simplelib2.model.commands.RemoveBookCommand;
import pl.com.bottega.simplelib2.model.commands.TurnBackCommand;

import java.time.Year;

import static pl.com.bottega.simplelib2.model.BookStatus.AVAILABLE;
import static pl.com.bottega.simplelib2.model.BookStatus.BORROWED;
import static pl.com.bottega.simplelib2.model.BookStatus.REMOVED;

public class Book {

	private BookId id;
	private BookStatus status;
	private String title;
	private Year year;
	private String author;
	private String borrower;

	Book() {
	}

	public Book(CreateBookCommand command) {
		this.id = BookId.generate();
		this.status = AVAILABLE;
		this.title = command.getTitle();
		this.year = command.getYear();
		this.author = command.getAuthor();
	}

	public void remove(RemoveBookCommand command) {
		status.remove(this, command);
	}

	public void borrow(BorrowCommand command) {
		status.borrow(this, command);
	}

	public void turnBack(TurnBackCommand command) {
		status.turnBack(this, command);
	}

	public void export(BookExporter exporter) {
		exporter.addId(id);
		exporter.addStatus(status);
		exporter.addTitle(title);
		exporter.addYear(year);
		exporter.addAuthor(author);
		exporter.addBorrower(borrower);
	}

	void setId(BookId id) {
		this.id = id;
	}

	public BookId getId() {
		return id;
	}

	void setStatus(BookStatus status) {
		this.status = status;
	}

	void setBorrower(String borrower) {
		this.borrower = borrower;
	}

}
