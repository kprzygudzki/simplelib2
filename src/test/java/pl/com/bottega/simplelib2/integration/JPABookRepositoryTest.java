package pl.com.bottega.simplelib2.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.simplelib2.TestBookCreator;
import pl.com.bottega.simplelib2.infrastructure.JPABookRepository;
import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.InvalidBookIdException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.com.bottega.simplelib2.TestBookCreator.assertThatBooksAreIdentical;
import static pl.com.bottega.simplelib2.TestBookCreator.createNewBook;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class JPABookRepositoryTest {

	@Autowired
	private JPABookRepository repository;

	@Test
	public void shouldFindBookById() {
		//given
		Book book = createNewBook();
		BookId bookId = book.getId();
		repository.put(book);
		//when
		Book retrievedBook = repository.get(bookId);
		//then
		assertThat(retrievedBook).isNotNull();
		assertThatBooksAreIdentical(book, retrievedBook);
	}

	@Test
	public void shouldThrowExceptionWhenProvidedWithInvalidBookId() {
		//given
		BookId invalidBookId = TestBookCreator.getRandomBookId();
		//when
		Throwable thrown = catchThrowable(() -> repository.get(invalidBookId));
		//then
		assertThat(thrown).isInstanceOf(InvalidBookIdException.class);
		InvalidBookIdException ex = (InvalidBookIdException) thrown;
		assertThat(ex.getBookId()).isEqualTo(invalidBookId);
	}

}
