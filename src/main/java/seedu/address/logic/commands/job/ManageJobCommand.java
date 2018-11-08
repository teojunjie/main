package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MACHINES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.JobName;

/**
 * Command used to manage jobs (eg: starting a Print)
 */
public class ManageJobCommand extends Command {
    public static final String COMMAND_WORD = "manageJob";
    public static final String OPTION_START = "start";
    public static final String OPTION_RESTART = "restart";
    public static final String OPTION_CANCEL = "cancel";
    public static final String OPTION_DELETE = "delete";


    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": starts/restarts/cancels/deletes a particular job\n" + "Example: " + COMMAND_WORD
            + " IDCP start";
    private static final String MESSAGE_STARTED_JOB = "The print has started.";
    private static final String MESSAGE_CANCELLED_JOB = "The print has been cancelled.";
    private static final String MESSAGE_RESTARTED_JOB = "The print has restarted.";
    private static final String MESSAGE_DELETED_JOB = "The print has been deleted";
    private static final String MESSAGE_NO_SUCH_JOB = "No such print found";
    private static final String MESSAGE_NO_SUCH_OPTION = "No such options. Only use: start, restart, cancel.";
    private static final String MESSAGE_ACCESS_DENIED = "Non admin user is not allowed to delete jobs in maker manager";

    private JobName name;
    private String option;

    public ManageJobCommand(JobName name, String option) {
        requireNonNull(name);
        this.name = name;
        this.option = option;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        //TODO: Currently findJob is happening twice. Fix.
        if (model.findJob(this.name) == null) {
            throw new CommandException(MESSAGE_NO_SUCH_JOB);
        }

        if (option.equals(OPTION_START)) {
            model.startJob(name);
            model.commitAddressBook();
            model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
            return new CommandResult(MESSAGE_STARTED_JOB);
        } else if (option.equals(OPTION_RESTART)) {
            model.restartJob(name);
            model.commitAddressBook();
            model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
            return new CommandResult(MESSAGE_RESTARTED_JOB);
        } else if (option.equals(OPTION_CANCEL)) {
            model.cancelJob(name);
            model.commitAddressBook();
            model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
            return new CommandResult(MESSAGE_CANCELLED_JOB);
        } else if (option.equals(OPTION_DELETE)) {
            if (!model.isLoggedIn()) {
                throw new CommandException(MESSAGE_ACCESS_DENIED);
            }
            model.deleteJob(name);
            model.commitAddressBook();
            model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
            return new CommandResult(MESSAGE_DELETED_JOB);

        } else {
            return new CommandResult(MESSAGE_NO_SUCH_OPTION);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ManageJobCommand // instanceof handles nulls
            && name.equals(((ManageJobCommand) other).name)) && option
            .equals(((ManageJobCommand) other).option); // state check
    }

}
