package pl.com.bottega.simplelib2.model.commands;

public class BorrowCommand {

	private String bookId;
	private String borrower;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getBorrower() {
		return borrower;
	}

}
