package pl.com.bottega.simplelib2;

import pl.com.bottega.simplelib2.application.user.BookDto;
import pl.com.bottega.simplelib2.application.user.BookDtoBuilder;
import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.commands.BorrowCommand;
import pl.com.bottega.simplelib2.model.commands.CreateBookCommand;
import pl.com.bottega.simplelib2.model.commands.RemoveBookCommand;

import java.time.Year;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBookCreator {

	public static Book createNewBook() {
		return createNewBook("The Importance of Being Earnest", Year.parse("1895"), "Oscar Wilde");
	}

	public static Book createNewBook(String title, Year year, String author) {
		CreateBookCommand command = new CreateBookCommand();
		command.setTitle(title);
		command.setYear(year);
		command.setAuthor(author);
		return new Book(command);
	}

	public static Book createBorrowedBook() {
		Book book = createNewBook();
		BorrowCommand command = new BorrowCommand();
		book.borrow(command);
		return book;
	}

	public static Book createRemovedBook() {
		Book book = createNewBook();
		RemoveBookCommand command = new RemoveBookCommand();
		book.remove(command);
		return book;
	}

	public static BookId getRandomBookId() {
		String id = UUID.randomUUID().toString();
		return BookId.parse(id);
	}

	public static void assertThatBooksAreIdentical(Book book, Book anotherBook) {
		BookDto bookDto = exportBookDto(book);
		BookDto anotherBookDto = exportBookDto(anotherBook);
		assertThatBooksAreIdentical(bookDto, anotherBookDto);
	}

	public static void assertThatBooksAreIdentical(BookDto bookdto, BookDto anotherBookDto) {
		assertThat(bookdto.getId()).isEqualTo(anotherBookDto.getId());
		assertThat(bookdto.getStatus()).isEqualTo(anotherBookDto.getStatus());
		assertThat(bookdto.getTitle()).isEqualTo(anotherBookDto.getTitle());
		assertThat(bookdto.getYear()).isEqualTo(anotherBookDto.getYear());
		assertThat(bookdto.getAuthor()).isEqualTo(anotherBookDto.getAuthor());
		assertThat(bookdto.getBorrower()).isEqualTo(anotherBookDto.getBorrower());
	}

	public static BookDto exportBookDto(Book book) {
		BookDtoBuilder firstBuilder = new BookDtoBuilder();
		book.export(firstBuilder);
		return firstBuilder.build();
	}

}
