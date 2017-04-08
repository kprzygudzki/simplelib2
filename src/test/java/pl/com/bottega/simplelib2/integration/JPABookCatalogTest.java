package pl.com.bottega.simplelib2.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.simplelib2.TestBookCreator;
import pl.com.bottega.simplelib2.application.user.BookDto;
import pl.com.bottega.simplelib2.application.user.BookQuery;
import pl.com.bottega.simplelib2.application.user.BookSearchResults;
import pl.com.bottega.simplelib2.infrastructure.JPABookCatalog;
import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.BookRepository;
import pl.com.bottega.simplelib2.model.InvalidBookIdException;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class JPABookCatalogTest {

	@Autowired
	private BookRepository repository;

	@Autowired
	private JPABookCatalog catalog;

	private BookId bookId;
	private BookId anotherBookId;

	@Before
	public void setUp() {
		String title = "The Importance of Being Earnest";
		Year year = Year.parse("1985");
		String author = "Oskar Wilde";
		bookId = createAndPersistBook(title, year, author);

		String anotherTitle = "Effective Java";
		Year anotherYear = Year.parse("2001");
		String anotherAuthor = "Joshua Bloch";
		anotherBookId = createAndPersistBook(anotherTitle, anotherYear, anotherAuthor);
	}

	@Test
	public void shouldGetBookById() {
		//when
		BookDto bookDto = catalog.get(bookId);
		//then
		assertThat(bookDto.getId()).isEqualTo(bookId.toString());
	}

	@Test
	public void shouldFindBookByTitle() {
		//given
		BookQuery bookQuery = new BookQuery();
		bookQuery.setTitle("The Importance of Being Earnest");
		//when
		BookSearchResults results = catalog.find(bookQuery);
		//then
		assertThat(results.getBookDtos().size()).isEqualTo(1);
		assertThat(results.getBookDtos().get(0).getId()).isEqualTo(bookId.toString());
	}

	@Test
	public void shouldFindBookByYear() {
		//given
		BookQuery bookQuery = new BookQuery();
		bookQuery.setYearMin(Year.parse("2000"));
		bookQuery.setYearMax(Year.parse("2011"));
		//when
		BookSearchResults results = catalog.find(bookQuery);
		//then
		assertThat(results.getBookDtos().size()).isEqualTo(1);
		assertThat(results.getBookDtos().get(0).getId()).isEqualTo(anotherBookId.toString());
	}

	@Test
	public void shouldFindBookByAuthor() {
		//given
		BookQuery bookQuery = new BookQuery();
		bookQuery.setAuthor("Joshua Bloch");
		//when
		BookSearchResults results = catalog.find(bookQuery);
		//then
		assertThat(results.getBookDtos().size()).isEqualTo(1);
		assertThat(results.getBookDtos().get(0).getId()).isEqualTo(anotherBookId.toString());
	}

	@Test
	public void shouldFindBookByTitleYearAndAuthor() {
		//given
		BookQuery bookQuery = new BookQuery();
		bookQuery.setTitle("Effective Java");
		bookQuery.setYearMin(Year.parse("2000"));
		bookQuery.setYearMax(Year.parse("2011"));
		bookQuery.setAuthor("Joshua Bloch");
		//when
		BookSearchResults results = catalog.find(bookQuery);
		//then
		assertThat(results.getBookDtos().size()).isEqualTo(1);
		assertThat(results.getBookDtos().get(0).getId()).isEqualTo(anotherBookId.toString());
	}

	@Test
	public void shouldThrowExceptionWhenProvidedWithInvalidBookId() {
		//given
		BookId invalidBookId = TestBookCreator.getRandomBookId();
		//when
		Throwable thrown = catchThrowable(() -> catalog.get(invalidBookId));
		//then
		assertThat(thrown).isInstanceOf(InvalidBookIdException.class);
		InvalidBookIdException ex = (InvalidBookIdException) thrown;
		assertThat(ex.getBookId()).isEqualTo(invalidBookId);
	}

	@Test
	public void shouldListAllBooks() {
		//when
		BookSearchResults results = catalog.list();
		//then
		assertThat(results.getBookDtos().size()).isEqualTo(2);
	}

	private BookId createAndPersistBook(String title, Year year, String author) {
		Book book = TestBookCreator.createNewBook(title, year, author);
		repository.put(book);
		return book.getId();
	}

}
