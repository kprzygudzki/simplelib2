package pl.com.bottega.simplelib2.model;

import java.time.Year;

public interface BookExporter {

	void addId(BookId id);

	void addTitle(String title);

	void addYear(Year year);

	void addAuthor(String author);

	void addStatus(BookStatus status);

	void addBorrower(String borrower);

}
