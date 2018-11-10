package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MACHINES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.job.JobName;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.job.exceptions.JobOngoingException;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.exceptions.MachineDisabledException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;

/**
 * Command used to manage jobs (eg: starting a Print)
 */
public class ManageJobCommand extends Command {
    public static final String COMMAND_WORD = "manageJob";
    public static final String OPTION_START = "start";
    public static final String OPTION_RESTART = "restart";
    public static final String OPTION_CANCEL = "cancel";
    public static final String OPTION_DELETE = "delete";
    public static final String OPTION_MOVE = "move";
    public static final String OPTION_SHIFT = "shift";
    public static final String OPTION_SWAP = "swap";

    public static final String SHIFT_OPTION_UP = "up";
    public static final String SHIFT_OPTION_DOWN = "down";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": start/restart/cancel/delete/move/shift/swap a particular job\n" + "Example: " + COMMAND_WORD
            + " IDCP start";
    public static final String MESSAGE_USAGE_MOVE =
        COMMAND_WORD + " move TargetMachineName" + "Example: " + COMMAND_WORD + " IDCP move JJPrinter";
    public static final String MESSAGE_USAGE_SWAP =
        COMMAND_WORD + " move TargetJobName" + "Example: " + COMMAND_WORD + " IDCP swap PrintJob7";
    public static final String MESSAGE_USAGE_SHIFT =
        COMMAND_WORD + " shift up/down" + "Example: " + COMMAND_WORD + " IDCP swap PrintJob7";
    private static final String MESSAGE_STARTED_JOB = "The print has started.";
    private static final String MESSAGE_CANNOT_START_JOB =
        "Cannot Start/Restart Job! This printer already has an ongoing Job!";
    private static final String MESSAGE_CANCELLED_JOB = "The print has been cancelled.";
    private static final String MESSAGE_RESTARTED_JOB = "The print has restarted.";
    private static final String MESSAGE_DELETED_JOB = "The print has been deleted";
    private static final String MESSAGE_NO_SUCH_JOB = "No such print found";
    private static final String MESSAGE_NO_SUCH_OPTION = "No such options. Only use: start, restart, cancel.";
    private static final String MESSAGE_ACCESS_DENIED_1 = "Non admin user is not allowed to ";
    private static final String MESSAGE_ACCESS_DENIED_2 = " Jobs in Maker Manager";
    private static final String MESSAGE_MACHINE_NOT_FOUND = "Machine not found in MakerManager!";
    private static final String MESSAGE_MOVED_JOB = "Job moved to ";
    private static final String MESSAGE_SHIFTED = "Job Shifted!";
    private static final String MESSAGE_SHIFTED_NO_SUCH_OPTION = "Only shift up or shift down allowed";
    private static final String MESSAGE_JOB_ONGOING = "Unable to Modify ongoing Job";
    private static final String MESSAGE_SWAP_SUCCESS = "Jobs swapped: %1$s";
    private static final String MESSAGE_MACHINE_DISABLED = "Unable to Start/Restart! Machine Disabled!";
    private static final String MESSAGE_ACCESS_DENIED = "Non admin user is not allowed to delete jobs in maker manager";
    private static final String MESSAGE_ONLY_TOP_JOB_STARTABLE = "You can only start/restart the first job in queue."
        + " If you have to start another job, please ask the manager to help change the queue.";

    private JobName name;
    private String option;
    private String operand2;

    public ManageJobCommand(JobName name, String option, String operand2) {
        requireNonNull(name);
        this.name = name;
        this.option = option;
        this.operand2 = operand2;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.findJob(this.name) == null) {
            throw new CommandException(MESSAGE_NO_SUCH_JOB);
        }

        switch (option) {
        case OPTION_START:
            try {
                if (!model.isTopJob(name) && !model.isLoggedIn()) {
                    throw new CommandException(MESSAGE_ONLY_TOP_JOB_STARTABLE);
                }
                model.startJob(name);
                model.commitAddressBook();
                model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
                return new CommandResult(MESSAGE_STARTED_JOB);
            } catch (MachineDisabledException mde) {
                throw new CommandException(MESSAGE_MACHINE_DISABLED);
            } catch (JobOngoingException joe) {
                throw new CommandException(MESSAGE_CANNOT_START_JOB);
            }

        case OPTION_RESTART:
            try {
                if (!model.isTopJob(name) && !model.isLoggedIn()) {
                    throw new CommandException(MESSAGE_ONLY_TOP_JOB_STARTABLE);
                }
                model.restartJob(name);
                model.commitAddressBook();
                model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
                return new CommandResult(MESSAGE_RESTARTED_JOB);
            } catch (MachineDisabledException mde) {
                throw new CommandException(MESSAGE_MACHINE_DISABLED);
            }

        case OPTION_CANCEL:
            model.cancelJob(name);
            model.commitAddressBook();
            model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
            return new CommandResult(MESSAGE_CANCELLED_JOB);

        case OPTION_DELETE:
            if (!model.isLoggedIn()) {
                throw new CommandException(MESSAGE_ACCESS_DENIED_1 + OPTION_DELETE + MESSAGE_ACCESS_DENIED_2);
            }
            try {
                model.deleteJob(name);
            } catch (JobOngoingException joe) {
                throw new CommandException(MESSAGE_JOB_ONGOING);
            }
            model.commitAddressBook();
            model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
            return new CommandResult(MESSAGE_DELETED_JOB);

        case OPTION_MOVE:
            if (!model.isLoggedIn()) {
                throw new CommandException(MESSAGE_ACCESS_DENIED_1 + OPTION_MOVE + MESSAGE_ACCESS_DENIED_2);
            }
            //parsing operand2
            try {
                MachineName targetMachineName = ParserUtil.parseMachineName(operand2);
                model.moveJob(name, targetMachineName);
                model.commitAddressBook();
                return new CommandResult(MESSAGE_MOVED_JOB + targetMachineName.toString());

            } catch (ParseException pe) {
                throw new CommandException(pe.getMessage());
            } catch (MachineNotFoundException mfe) {
                throw new CommandException(MESSAGE_MACHINE_NOT_FOUND);
            } catch (JobOngoingException je) {
                throw new CommandException(MESSAGE_JOB_ONGOING);
            }

        case OPTION_SHIFT:
            if (!model.isLoggedIn()) {
                throw new CommandException(MESSAGE_ACCESS_DENIED_1 + OPTION_SHIFT + MESSAGE_ACCESS_DENIED_2);
            }
            //parsing operand2
            try {
                switch (operand2) {
                case SHIFT_OPTION_UP:
                    model.shiftJob(name, 1);
                    model.commitAddressBook();
                    return new CommandResult(MESSAGE_SHIFTED);
                case SHIFT_OPTION_DOWN:
                    model.shiftJob(name, -1);
                    model.commitAddressBook();
                    return new CommandResult(MESSAGE_SHIFTED);

                default:
                    throw new CommandException(MESSAGE_SHIFTED_NO_SUCH_OPTION);
                }
            } catch (JobNotFoundException jfe) {
                throw new CommandException(MESSAGE_NO_SUCH_JOB);
            } catch (JobOngoingException jo) {
                throw new CommandException(MESSAGE_JOB_ONGOING);
            }
        case OPTION_SWAP:
            if (!model.isLoggedIn()) {
                throw new CommandException(MESSAGE_ACCESS_DENIED_1 + OPTION_SHIFT + MESSAGE_ACCESS_DENIED_2);
            }
            try {
                JobName targetJobName = ParserUtil.parseJobName(operand2);
                model.swapJobs(name, targetJobName);
                model.commitAddressBook();
                return new CommandResult(String.format(MESSAGE_SWAP_SUCCESS, name + " and " + targetJobName));
            } catch (ParseException pe) {
                throw new CommandException(pe.getMessage());
            } catch (JobNotFoundException jfe) {
                throw new CommandException(MESSAGE_NO_SUCH_JOB);
            } catch (JobOngoingException joe) {
                throw new CommandException(MESSAGE_JOB_ONGOING);
            }
        default:
            throw new CommandException(MESSAGE_NO_SUCH_OPTION);
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
