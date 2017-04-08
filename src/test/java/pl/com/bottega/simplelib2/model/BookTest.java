package pl.com.bottega.simplelib2.model;

import org.junit.Test;
import pl.com.bottega.simplelib2.application.user.BookDto;
import pl.com.bottega.simplelib2.model.commands.BorrowCommand;
import pl.com.bottega.simplelib2.model.commands.CreateBookCommand;
import pl.com.bottega.simplelib2.model.commands.RemoveBookCommand;
import pl.com.bottega.simplelib2.model.commands.TurnBackCommand;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.com.bottega.simplelib2.TestBookCreator.*;
import static pl.com.bottega.simplelib2.model.BookStatus.*;

public class BookTest {

	@Test
	public void shouldGenerateBookId() {
		//given
		CreateBookCommand command = new CreateBookCommand();
		//when
		Book book = new Book(command);
		//then
		BookDto bookDto = exportBookDto(book);
		assertThat(bookDto.getId()).isNotNull();
	}

	@Test
	public void shouldGenerateUniqueBookIds() {
		//given
		CreateBookCommand command = new CreateBookCommand();
		//when
		Book firstBook = new Book(command);
		Book secondBook = new Book(command);
		BookDto firstBookDto = exportBookDto(firstBook);
		BookDto secondBookDto = exportBookDto(secondBook);
		//then
		assertThat(firstBookDto.getId()).isNotEqualTo(secondBookDto.getId());
	}

	@Test
	public void shouldRememberTitle() {
		//given
		CreateBookCommand command = new CreateBookCommand();
		String title = "The Importance of Being Earnest";
		command.setTitle(title);
		//when
		Book book = new Book(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getTitle()).isEqualTo(title);
	}

	@Test
	public void shouldRememberPublicationYear() {
		//given
		CreateBookCommand command = new CreateBookCommand();
		Year year = Year.parse("1895");
		command.setYear(year);
		//when
		Book book = new Book(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getYear()).isEqualTo(year);
	}

	@Test
	public void shouldRememberAuthor() {
		//given
		CreateBookCommand command = new CreateBookCommand();
		String author = "Oscar Wilde";
		command.setAuthor(author);
		//when
		Book book = new Book(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getAuthor()).isEqualTo(author);
	}

	@Test
	public void shouldSetStatusToAvailableOnCreation() {
		//given
		CreateBookCommand command = new CreateBookCommand();
		//when
		Book book = new Book(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getStatus()).isEqualTo(AVAILABLE.toString());
	}

	@Test
	public void shouldSetStatusToRemovedOnRemoval() {
		//given
		Book book = createNewBook();
		RemoveBookCommand command = new RemoveBookCommand();
		//when
		book.remove(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getStatus()).isEqualTo(REMOVED.toString());
	}

	@Test
	public void shouldSetStatusToBorrowedOnBorrowing() {
		//given
		Book book = createNewBook();
		BorrowCommand command = new BorrowCommand();
		//when
		book.borrow(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getStatus()).isEqualTo(BORROWED.toString());
	}

	@Test
	public void shouldRememberTheNameOfLastBorrower() {
		//given
		Book book = createNewBook();
		BorrowCommand command = new BorrowCommand();
		String borrower = "Pan Kracy";
		command.setBorrower(borrower);
		//when
		book.borrow(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getBorrower()).isEqualTo(borrower);
	}

	@Test
	public void shouldSetStatusToAvailableOnTurningBack() {
		//given
		Book book = createBorrowedBook();
		TurnBackCommand command = new TurnBackCommand();
		//when
		book.turnBack(command);
		BookDto bookDto = exportBookDto(book);
		//then
		assertThat(bookDto.getStatus()).isEqualTo(AVAILABLE.toString());
	}

	@Test
	public void shouldNotAllowToRemoveWhileNotAvailable() {
		//given
		Book book = createRemovedBook();
		BookDto bookDto = exportBookDto(book);
		assertThat(bookDto.getStatus()).isNotEqualTo(AVAILABLE.toString());
		RemoveBookCommand command = new RemoveBookCommand();
		//when
		Throwable thrown = catchThrowable(() -> book.remove(command) );
		//then
		assertThat(thrown).isInstanceOf(BookStatusException.class);
	}

	@Test
	public void shouldNotAllowToBorrowWhenNotAvailable() {
		//given
		Book book = createRemovedBook();
		BookDto bookDto = exportBookDto(book);
		assertThat(bookDto.getStatus()).isNotEqualTo(AVAILABLE.toString());
		BorrowCommand command = new BorrowCommand();
		//when
		Throwable thrown = catchThrowable(() -> book.borrow(command) );
		//then
		assertThat(thrown).isInstanceOf(BookStatusException.class);
	}

	@Test
	public void shouldNotAllowToTurnBackWhenNotBorrowed() {
		//given
		Book book = createNewBook();
		BookDto bookDto = exportBookDto(book);
		assertThat(bookDto.getStatus()).isNotEqualTo(BORROWED.toString());
		TurnBackCommand command = new TurnBackCommand();
		//when
		Throwable thrown = catchThrowable(() -> book.turnBack(command) );
		//then
		assertThat(thrown).isInstanceOf(BookStatusException.class);
	}

}
