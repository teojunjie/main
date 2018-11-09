package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_ILLEGAL_JOB_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.job.ManageJobCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobName;

/**
 * Parses input arguments and creates a new ManageJobCommand object
 */
public class ManageJobCommandParser implements Parser<ManageJobCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ManageJobCommand
     * and returns a ManageJobCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ManageJobCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ManageJobCommand.MESSAGE_USAGE));
        }

        String[] temp = trimmedArgs.split(" ");

        if (temp.length < 2 || temp.length > 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ManageJobCommand.MESSAGE_USAGE));
        }

        try {
            JobName name = new JobName(temp[0]);
            String option = temp[1];
            if (temp.length == 3) {
                String operand2 = temp[2];
                return new ManageJobCommand(name, option, operand2);
            } else {
                String operand2 = "";
                return new ManageJobCommand(name, option, operand2);
            }
        } catch (Exception IllegalArgumentException) {
            throw new ParseException(String.format(MESSAGE_ILLEGAL_JOB_NAME, ManageJobCommand.MESSAGE_USAGE));
        }
    }
}
