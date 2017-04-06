package pl.com.bottega.simplelib2.application;

import pl.com.bottega.simplelib2.model.BookId;
import pl.com.bottega.simplelib2.model.commands.CreateBookCommand;
import pl.com.bottega.simplelib2.model.commands.RemoveBookCommand;

public interface BookManagementProcess {

	BookId add(CreateBookCommand command);

	void remove(RemoveBookCommand command);

}
