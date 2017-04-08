package pl.com.bottega.simplelib2.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.simplelib2.application.BookBorrowingProcess;
import pl.com.bottega.simplelib2.application.BookCatalog;
import pl.com.bottega.simplelib2.application.BookManagementProcess;
import pl.com.bottega.simplelib2.application.user.BookDto;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.BookStatusException;
import pl.com.bottega.simplelib2.model.commands.BorrowCommand;
import pl.com.bottega.simplelib2.model.commands.CreateBookCommand;
import pl.com.bottega.simplelib2.model.commands.RemoveBookCommand;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.com.bottega.simplelib2.model.BookStatus.BORROWED;
import static pl.com.bottega.simplelib2.model.BookStatus.REMOVED;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class BookManagementTest {

	@Autowired
	private BookManagementProcess management;

	@Autowired
	private BookBorrowingProcess borrowing;

	@Autowired
	private BookCatalog catalog;

	@Test
	public void shouldBeAbleToAddBook() {
		//given
		CreateBookCommand command = new CreateBookCommand();
		command.setTitle("The Importance of Being Earnest");
		command.setYear(Year.parse("1895"));
		command.setAuthor("Oscar Wilde");
		//when
		BookId bookId = management.add(command);
		BookDto retrievedBookDto = catalog.get(bookId);
		//then
		assertThat(retrievedBookDto.getId()).isEqualTo(bookId.toString());
	}

	@Test
	public void shouldBeAbleToRemoveBookById() {
		//given
		CreateBookCommand createCommand = new CreateBookCommand();
		createCommand.setTitle("The Importance of Being Earnest");
		createCommand.setYear(Year.parse("1895"));
		createCommand.setAuthor("Oscar Wilde");
		BookId bookId = management.add(createCommand);
		RemoveBookCommand removeCommand = new RemoveBookCommand();
		removeCommand.setBookId(bookId.toString());
		//when
		management.remove(removeCommand);
		BookDto retrievedBookDto = catalog.get(bookId);
		//then
		assertThat(retrievedBookDto.getStatus()).isEqualTo(REMOVED.toString());
	}

	@Test
	public void shouldNotBeAbleToRemoveBookWhenBorrowed() {
		//given
		CreateBookCommand createCommand = new CreateBookCommand();
		createCommand.setTitle("The Importance of Being Earnest");
		createCommand.setYear(Year.parse("1895"));
		createCommand.setAuthor("Oscar Wilde");
		BookId bookId = management.add(createCommand);
		BorrowCommand borrowCommand = new BorrowCommand();
		borrowCommand.setBookId(bookId.toString());
		borrowing.borrow(borrowCommand);
		RemoveBookCommand removeCommand = new RemoveBookCommand();
		removeCommand.setBookId(bookId.toString());
		//when
		Throwable thrown = catchThrowable(() -> management.remove(removeCommand));
		//then
		assertThat(thrown).isInstanceOf(BookStatusException.class);
		BookStatusException ex = (BookStatusException) thrown;
		assertThat(ex.getStatus()).isEqualTo(BORROWED);
	}
}
