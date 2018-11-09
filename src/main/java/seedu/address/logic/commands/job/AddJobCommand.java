package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MACHINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.exceptions.MachineDisabledException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;

/**
 * Adds a job to the address book.
 */
public class AddJobCommand extends Command {

    public static final String COMMAND_WORD = "addJob";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Adds a job to the address book. " + "Parameters: " + PREFIX_NAME + "JOB NAME "
            + PREFIX_MACHINE + "MACHINE NAME " + PREFIX_JOB_OWNER + "JOB OWNER NAME " + PREFIX_JOB_PRIORITY
            + "JOB PRIORITY " + PREFIX_JOB_DURATION + "JOB DURATION " + PREFIX_JOB_NOTE + "JOB NOTE " + "[" + PREFIX_TAG
            + "TAG]...\n" + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "iDCP " + PREFIX_MACHINE + "TYPrinter "
            + PREFIX_JOB_OWNER + "TIAN YUAN " + PREFIX_JOB_PRIORITY + "HIGH " + PREFIX_JOB_DURATION + "1.5 "
            + PREFIX_JOB_NOTE + "This is for the iDCP project " + PREFIX_TAG + "iDCP";


    public static final String MESSAGE_SUCCESS = "New job added: %1$s";
    public static final String MESSAGE_FAILURE = "New job NOT added: %1$s";
    public static final String MESSAGE_DUPLICATE_JOB =
        "Failed to add Job. This job already exists! Job Names must be unique";
    public static final String MESSAGE_NO_MACHINE = "Failed to add Job. Machine specified does not exist";
    public static final String MESSAGE_DISBLED_MACHINE = "Failed to add Job. Machine specified is Disabled";
    public static final String MESSAGE_NO_MACHINE_AUTO =
        "Failed to add Job. No Available/Operational Machines to add Job";

    private final Job jobToAdd;

    /**
     * Creates an AddJobCommand to add the specified {@code Job}
     */
    public AddJobCommand(Job job) {
        requireNonNull(job);
        jobToAdd = job;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasJob(jobToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        }

        if (isAutoAdd()) {
            try {
                jobToAdd.setMachine(model.getMostFreeMachine().getName());
            } catch (MachineNotFoundException mfe) {
                throw new CommandException(MESSAGE_NO_MACHINE_AUTO);
            }
        }

        try {
            model.addJob(jobToAdd);
        } catch (MachineNotFoundException mfe) {
            throw new CommandException(MESSAGE_NO_MACHINE);
        } catch (MachineDisabledException mde) {
            throw new CommandException(MESSAGE_DISBLED_MACHINE);
        }

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, jobToAdd.getJobName()));
    }

    private boolean isAutoAdd() {
        return jobToAdd.getMachineName().equals(new MachineName("AUTO"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof seedu.address.logic.commands.job.AddJobCommand // instanceof handles nulls
            && jobToAdd.equals(((seedu.address.logic.commands.job.AddJobCommand) other).jobToAdd));
    }
}
