package seedu.address.logic.parser.queue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.queue.SwapJobsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobName;

/**
 * Parses input arguments and creates a new SwapJobCommand object
 */
public class SwapJobsCommandParser implements Parser<SwapJobsCommand> {


    private final Logger logger = LogsCenter.getLogger(SwapJobsCommandParser.class);

    /**
     * Parses the given {@code userInput} of arguments in the context
     * of SwapJobsCommandParser and returns a SwapJobCommand object
     *
     * @throws ParseException if the user input does not conform to the expected
     *                        format
     */
    public SwapJobsCommand parse(String userInput) throws ParseException {
        logger.info("User input : " + userInput);
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwapJobsCommand.MESSAGE_USAGE));
        }

        String[] temp = trimmedArgs.split(" ");

        if (temp.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwapJobsCommand.MESSAGE_USAGE));
        }

        try {
            String jobName1 = temp[0];
            String jobName2 = temp[1];
            return new SwapJobsCommand(jobName1, jobName2);
        } catch (IllegalArgumentException ile) {
            throw new ParseException(JobName.MESSAGE_JOBNAME_CONSTRAINTS);
        }
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
