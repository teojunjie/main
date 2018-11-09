package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class JobNoteTest {

    @Test
    public void isValidJobNote() {
        // null note
        Assert.assertThrows(NullPointerException.class, () -> JobNote.isValidJobNote(null));

        // invalid note
        assertFalse(JobNote.isValidJobNote("")); // empty string
        assertFalse(JobNote.isValidJobNote(" ")); // spaces only
        assertFalse(JobNote.isValidJobNote("^")); // only non-alphanumeric characters
        assertFalse(JobNote.isValidJobNote("peter*")); // contains non-alphanumeric characters

        // valid note

        assertTrue(JobNote.isValidJobNote("peter jack")); // alphabets only
        assertTrue(JobNote.isValidJobNote("12345")); // numbers only
        assertTrue(JobNote.isValidJobNote("peter the 2nd")); // alphanumeric characters
        assertTrue(JobNote.isValidJobNote("Capital Tan")); // with capital letters
        assertTrue(JobNote.isValidJobNote("David Roger Jackson Ray Jr 2nd")); // long notes

    }
}
