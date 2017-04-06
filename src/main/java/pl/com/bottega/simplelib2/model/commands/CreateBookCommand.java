package pl.com.bottega.simplelib2.model.commands;

import java.time.Year;

public class CreateBookCommand {

	private String title;
	private Year year;
	private String author;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Year getYear() {
		return year;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

}
