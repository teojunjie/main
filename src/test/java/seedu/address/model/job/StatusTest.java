package seedu.address.model.job;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.testdata.TypicalJobs.BUMBERBEE;
import static seedu.address.testutil.testdata.TypicalJobs.IDCP;
import static seedu.address.testutil.testdata.TypicalJobs.NEWPROJECT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StatusTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkEnabledJobStatus() {
        assertTrue(IDCP.getStatus() == Status.QUEUED);
        assertTrue(NEWPROJECT.getStatus() == Status.FINISHED);
        assertTrue(BUMBERBEE.getStatus() == Status.CANCELLED);

        assertFalse(IDCP.getStatus() == Status.ONGOING);
        assertFalse(IDCP.getStatus() == Status.FINISHED);
        assertFalse(IDCP.getStatus() == Status.CANCELLED);
        assertFalse(NEWPROJECT.getStatus() == Status.ONGOING);
        assertFalse(NEWPROJECT.getStatus() == Status.QUEUED);
        assertFalse(NEWPROJECT.getStatus() == Status.CANCELLED);
        assertFalse(BUMBERBEE.getStatus() == Status.QUEUED);
        assertFalse(BUMBERBEE.getStatus() == Status.ONGOING);
        assertFalse(BUMBERBEE.getStatus() == Status.FINISHED);

    }
}
