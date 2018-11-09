package seedu.address.model.machine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MachineNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MachineName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MachineName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> MachineName.isValidName(null));

        // invalid name
        assertFalse(MachineName.isValidName("")); // empty string
        assertFalse(MachineName.isValidName(" ")); // spaces only
        assertFalse(MachineName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(MachineName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(MachineName.isValidName("peter jack")); // alphabets only
        assertTrue(MachineName.isValidName("12345")); // numbers only
        assertTrue(MachineName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(MachineName.isValidName("Capital Tan")); // with capital letters
        assertTrue(MachineName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
