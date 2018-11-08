package seedu.address.logic.parser.machine;

import static seedu.address.commons.core.Messages.MESSAGE_ILLEGAL_MACHINE_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.machine.ManageMachineCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.machine.MachineName;

/**
 * Parses input arguments and creates a new removeMachineCommand object
 */
public class ManageMachineCommandParser implements Parser<ManageMachineCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the removeMachineCommand
     * and returns a removeMachineCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ManageMachineCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ManageMachineCommand.MESSAGE_USAGE));
        }

        String[] temp = trimmedArgs.split(" ");

        MachineName name;
        String option;
        String autoFlush;

        switch (temp.length) {
        case 2:
            try {
                name = new MachineName(temp[0]);
                option = temp[1];
                autoFlush = null;
            } catch (Exception IllegalArgumentException) {
                throw new ParseException(
                    String.format(MESSAGE_ILLEGAL_MACHINE_NAME, ManageMachineCommand.MESSAGE_USAGE));
            }
            break;
        case 3:
            try {
                name = new MachineName(temp[0]);
                option = temp[1];
                autoFlush = temp[2];
            } catch (Exception IllegalArgumentException) {
                throw new ParseException(
                    String.format(MESSAGE_ILLEGAL_MACHINE_NAME, ManageMachineCommand.MESSAGE_USAGE));
            }
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ManageMachineCommand.MESSAGE_USAGE));
        }

        return new ManageMachineCommand(name, option, autoFlush);


    }
}
