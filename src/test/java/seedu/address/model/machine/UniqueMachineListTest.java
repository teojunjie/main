package seedu.address.model.machine;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.machine.exceptions.DuplicateMachineException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;
import seedu.address.testutil.testdata.ValidMachines;

public class UniqueMachineListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMachineList uniqueMachineList = new UniqueMachineList();

    @Test
    public void contains_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMachineList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueMachineList.contains(ValidMachines.TYPRINTER));
    }

    @Test
    public void contains_machineInList_returnsTrue() {
        uniqueMachineList.add(ValidMachines.JJPRINTER);
        assertTrue(uniqueMachineList.contains(ValidMachines.JJPRINTER));
    }

    @Test
    public void add_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMachineList.add(null);
    }

    @Test
    public void add_duplicateMachine_throwsDuplicateMachineException() {
        uniqueMachineList.add(ValidMachines.JJPRINTER);
        thrown.expect(DuplicateMachineException.class);
        uniqueMachineList.add(ValidMachines.JJPRINTER);
    }

    @Test
    public void setMachine_nullTargetMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMachineList.setMachine(null, ValidMachines.JJPRINTER);
    }

    @Test
    public void setMachine_nullEditedMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMachineList.setMachine(ValidMachines.JJPRINTER, null);
    }

    @Test
    public void remove_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMachineList.remove(null);
    }

    @Test
    public void remove_machineDoesNotExist_throwsMachineNotFoundException() {
        thrown.expect(MachineNotFoundException.class);
        uniqueMachineList.remove(ValidMachines.JJPRINTER);
    }
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperation() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMachineList.asUnmodifiableObservableList().remove(0);
    }




}
