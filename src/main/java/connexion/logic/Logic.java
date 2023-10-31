package connexion.logic;

import java.nio.file.Path;
import java.time.Clock;

import connexion.commons.core.GuiSettings;
import connexion.logic.commands.CommandResult;
import connexion.logic.commands.exceptions.CommandException;
import connexion.logic.parser.exceptions.ParseException;
import connexion.model.ReadOnlyAddressBook;
import connexion.model.person.Person;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see connexion.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    ObservableList<Person> getDisplayPerson();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Set the clock the app is to run on
     */
    void setClock(Clock clock);

    /**
     * Get the clock the app is running on
     */
    Clock getClock();
}
