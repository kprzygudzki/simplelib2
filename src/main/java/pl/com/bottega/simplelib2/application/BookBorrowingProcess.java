package pl.com.bottega.simplelib2.application;

import pl.com.bottega.simplelib2.model.commands.BorrowCommand;
import pl.com.bottega.simplelib2.model.commands.TurnBackCommand;

public interface BookBorrowingProcess {

	void borrow(BorrowCommand command);

	void turnBack(TurnBackCommand command);

}
