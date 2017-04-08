package pl.com.bottega.simplelib2.infrastructure;

import pl.com.bottega.simplelib2.application.BookCatalog;
import pl.com.bottega.simplelib2.application.user.BookDto;
import pl.com.bottega.simplelib2.application.user.BookDtoBuilder;
import pl.com.bottega.simplelib2.application.user.BookQuery;
import pl.com.bottega.simplelib2.application.user.BookSearchResults;
import pl.com.bottega.simplelib2.model.Book;
import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.InvalidBookIdException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JPABookCatalog implements BookCatalog {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public BookSearchResults list() {
		List<Book> books = retrieveAll();
		return generateBookSearchResults(books);
	}

	@Override
	public BookSearchResults find(BookQuery query) {
		List<Book> books = retrieveFor(query);
		return generateBookSearchResults(books);
	}

	@Override
	public BookDto get(BookId bookId) {
		List<Book> result = retrieveFor(bookId);
		return getSingleBookDto(result, bookId);
	}

	private List<Book> retrieveFor(BookId bookId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
		Root<Book> root = criteria.from(Book.class);
		criteria.where(builder.equal(root.get("id"), bookId));
		return entityManager.createQuery(criteria).getResultList();
	}

	private List<Book> retrieveFor(BookQuery query) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
		Root<Book> root = criteria.from(Book.class);
		Predicate[] predicates = createPredicates(query, builder, root);
		criteria.where(predicates);
		return entityManager.createQuery(criteria).getResultList();
	}

	private List<Book> retrieveAll() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
		Root<Book> root = criteria.from(Book.class);
		return entityManager.createQuery(criteria).getResultList();
	}

	private Predicate[] createPredicates(BookQuery query, CriteriaBuilder builder, Root<Book> root) {
		Set<Predicate> predicates = new HashSet<>();
		if (query.getTitle() != null)
			predicates.add(builder.equal(root.get("title"), query.getTitle()));
		if (query.getYearMin() != null)
			predicates.add(builder.greaterThanOrEqualTo(root.get("year"), query.getYearMin()));
		if (query.getYearMax() != null)
			predicates.add(builder.lessThanOrEqualTo(root.get("year"), query.getYearMax()));
		if (query.getAuthor() != null)
			predicates.add(builder.equal(root.get("author"), query.getAuthor()));
		return predicates.toArray(new Predicate[]{});
	}

	private BookSearchResults generateBookSearchResults(List<Book> books) {
		List<BookDto> bookDtos = exportBookDtos(books);
		BookSearchResults results = new BookSearchResults();
		results.setBookDtos(bookDtos);
		return results;
	}

	private List<BookDto> exportBookDtos(List<Book> books) {
		List<BookDto> bookDtos = new LinkedList<>();
		for (Book book : books)
			bookDtos.add(createBookDto(book));
		return bookDtos;
	}

	private BookDto getSingleBookDto(List<Book> result, BookId bookId) {
		if (result.isEmpty())
			throw new InvalidBookIdException(bookId);
		Book book = result.get(0);
		return createBookDto(book);
	}

	private BookDto createBookDto(Book book) {
		BookDtoBuilder builder = new BookDtoBuilder();
		book.export(builder);
		return builder.build();
	}

}
