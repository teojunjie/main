package seedu.address.model.job;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.job.Priority.isHigherPriority;
import static seedu.address.model.job.Priority.isValidPriority;
import static seedu.address.testutil.testdata.TypicalJobs.BUMBERBEE;
import static seedu.address.testutil.testdata.TypicalJobs.IDCP;
import static seedu.address.testutil.testdata.TypicalJobs.NEWPROJECT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PriorityTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkIsValidPriority() {
        //test with the typical jobs -> true
        assertTrue(isValidPriority(IDCP.getPriority().toString()));
        assertTrue(isValidPriority(NEWPROJECT.getPriority().toString()));
        assertTrue(isValidPriority(BUMBERBEE.getPriority().toString()));

        //test with underdefined strings -> false
        assertFalse(isValidPriority("LOW"));
        assertFalse(isValidPriority("SUPER"));
        assertFalse(isValidPriority("EMERGENCY"));
        assertFalse(isValidPriority("SUPERHIGH"));

    }

    @Test
    public void checkIsHigherPriority() {
        //URGENT is higher than HIGH -> true
        assertTrue(isHigherPriority(IDCP.getPriority(), NEWPROJECT.getPriority()));

        //HIGH is higher than NORMAL -> true
        assertTrue(isHigherPriority(NEWPROJECT.getPriority(), BUMBERBEE.getPriority()));

        //URGENT is higher than NORMAL -> true
        assertTrue(isHigherPriority(IDCP.getPriority(), BUMBERBEE.getPriority()));

        //HIGH is higher than URGENT -> false
        assertFalse(isHigherPriority(NEWPROJECT.getPriority(), IDCP.getPriority()));

        //NORMAL is higher than URGENT -> false
        assertFalse(isHigherPriority(BUMBERBEE.getPriority(), IDCP.getPriority()));

        //NORMAL is higher than HIGH -> false
        assertFalse(isHigherPriority(BUMBERBEE.getPriority(), NEWPROJECT.getPriority()));
    }
}
