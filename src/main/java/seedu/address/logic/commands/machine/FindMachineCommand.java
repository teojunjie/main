package seedu.address.logic.commands.machine;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.machine.MachineNameContainsKeywordsPredicate;
import seedu.address.model.machine.MachineNameFuzzyWuzzyPredicate;


/**
 * Finds and lists all machines in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMachineCommand extends Command {

    public static final String COMMAND_WORD = "findMachine";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all machines whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " machine1 machine2 machine3";

    private final MachineNameContainsKeywordsPredicate predicate;
    private final MachineNameFuzzyWuzzyPredicate fuzzyWuzzyPredicate;

    public FindMachineCommand(MachineNameContainsKeywordsPredicate predicate,
                              MachineNameFuzzyWuzzyPredicate fuzzyWuzzyPredicate) {
        this.predicate = predicate;
        this.fuzzyWuzzyPredicate = fuzzyWuzzyPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMachineList(predicate);
        if (model.getFilteredMachineList().size() < predicate.getNumberOfKeywords()) {
            model.updateFilteredMachineList(fuzzyWuzzyPredicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_MACHINE_LISTED_OVERVIEW, model.getFilteredMachineList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMachineCommand // instanceof handles nulls
                && predicate.equals(((FindMachineCommand) other).predicate)); // state check
    }
}
