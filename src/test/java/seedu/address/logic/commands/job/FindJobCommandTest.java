package seedu.address.logic.commands.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_JOBS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.testdata.TypicalJobs.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.JobNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindJobCommand}.
 */
public class FindJobCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        JobNameContainsKeywordsPredicate firstPredicate =
            new JobNameContainsKeywordsPredicate(Collections.singletonList("first"));
        JobNameContainsKeywordsPredicate secondPredicate =
            new JobNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindJobCommand findFirstCommand = new FindJobCommand(firstPredicate);
        FindJobCommand findSecondCommand = new FindJobCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindJobCommand findFirstCommandCopy = new FindJobCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different job -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noJobFound() {
        String expectedMessage = String.format(MESSAGE_JOBS_LISTED_OVERVIEW, 0);
        JobNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindJobCommand command = new FindJobCommand(predicate);
        expectedModel.updateFilteredJobListInAllMachines(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code JobNameContainsKeywordsPredicate}.
     */
    private JobNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new JobNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
