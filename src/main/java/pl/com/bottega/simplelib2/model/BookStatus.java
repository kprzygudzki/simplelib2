package pl.com.bottega.simplelib2.model;

import pl.com.bottega.simplelib2.model.commands.BorrowCommand;
import pl.com.bottega.simplelib2.model.commands.RemoveBookCommand;
import pl.com.bottega.simplelib2.model.commands.TurnBackCommand;

public enum BookStatus {

	AVAILABLE {
		@Override
		void remove(Book book, RemoveBookCommand command) {
			book.setStatus(REMOVED);
		}

		@Override
		void borrow(Book book, BorrowCommand command) {
			book.setStatus(BORROWED);
			book.setBorrower(command.getBorrower());
		}

	},

	BORROWED {
		@Override
		void turnBack(Book book, TurnBackCommand command) {
			book.setStatus(AVAILABLE);
		}

	},

	REMOVED;

	void remove(Book book, RemoveBookCommand command) {
		throw new BookStatusException(this);
	}

	void borrow(Book book, BorrowCommand command) {
		throw new BookStatusException(this);
	}

	void turnBack(Book book, TurnBackCommand command) {
		throw new BookStatusException(this);
	}

}
