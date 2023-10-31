package connexion.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import connexion.commons.core.index.Index;
import connexion.commons.util.ToStringBuilder;
import connexion.logic.Messages;
import connexion.logic.commands.exceptions.CommandException;
import connexion.model.Model;
import connexion.model.person.Person;
import connexion.ui.UiManager;

/**
 * Details a person identified using it's displayed index from the address book.
 */
public class DetailCommand extends Command {
    public static final String COMMAND_WORD = "detail";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows details of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DETAIL_SUCCESS = "Display Details of Person: %1$s";

    private final Index targetIndex;

    public DetailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDetail = lastShownList.get(targetIndex.getZeroBased());
        //UiManager.updatePersonView(personToDetail);
        model.updateSinglePersonList(personToDetail);
        return new CommandResult(String.format(MESSAGE_DETAIL_SUCCESS, Messages.format(personToDetail)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailCommand)) {
            return false;
        }

        DetailCommand otherDetailCommand = (DetailCommand) other;
        return targetIndex.equals(otherDetailCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
