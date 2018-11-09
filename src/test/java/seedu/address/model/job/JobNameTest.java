package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class JobNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobName(null));
    }

    @Test
    public void constructor_invalidJobName_throwsIllegalArgumentException() {
        String invalidJobName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JobName(invalidJobName));
    }

    @Test
    public void isValidJobName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> JobName.isValidJobName(null));

        // invalid name
        assertFalse(JobName.isValidJobName("")); // empty string
        assertFalse(JobName.isValidJobName(" ")); // spaces only
        assertFalse(JobName.isValidJobName("^")); // only non-alphanumeric characters
        assertFalse(JobName.isValidJobName("innoventure*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(JobName.isValidJobName("innoventure")); // alphabets only
        assertTrue(JobName.isValidJobName("12345")); // numbers only
        assertTrue(JobName.isValidJobName("innoventure2018")); // alphanumeric characters
        assertTrue(JobName.isValidJobName("iDCP2018")); // with capital letters
    }
}
