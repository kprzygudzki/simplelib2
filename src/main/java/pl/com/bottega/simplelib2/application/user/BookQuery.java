package pl.com.bottega.simplelib2.application.user;

import java.time.Year;

public class BookQuery {

	private String title;
	private String author;
	private Year yearMin;
	private Year yearMax;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setYearMin(Year yearMin) {
		this.yearMin = yearMin;
	}

	public Year getYearMin() {
		return yearMin;
	}

	public void setYearMax(Year yearMax) {
		this.yearMax = yearMax;
	}

	public Year getYearMax() {
		return yearMax;
	}
}
