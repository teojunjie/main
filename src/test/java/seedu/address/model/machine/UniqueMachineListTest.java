package seedu.address.model.machine;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.machine.exceptions.DuplicateMachineException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;
import seedu.address.testutil.MachineBuilder;
import seedu.address.testutil.testdata.ValidJobs;
import seedu.address.testutil.testdata.ValidMachines;

public class UniqueMachineListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMachineList uniqueMachineList = new UniqueMachineList();

    @Test
    public void contains_sameNameMachine() {
        uniqueMachineList.add(ValidMachines.JJPRINTER);
        assertTrue(uniqueMachineList.contains(ValidMachines.JJPRINTER));
    }

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

    @Test
    public void findMachineTest() {
        uniqueMachineList.add(ValidMachines.JJPRINTER);
        assertNotNull(uniqueMachineList.findMachine(new MachineName("JJPrinter")));
    }

    @Test
    public void containsSameNameMachine() {
        uniqueMachineList.add(ValidMachines.JJPRINTER);
        Machine testMachine = new MachineBuilder()
            .withName("JJPrinter")
            .withStatus(MachineStatus.ENABLED)
            .build();
        assertTrue(uniqueMachineList.containsSameNameMachine(testMachine));
    }

    @Test
    public void replaceTargetMachineWithEditedMachine() {
        uniqueMachineList.add(ValidMachines.JJPRINTER);
        uniqueMachineList.setMachine(ValidMachines.JJPRINTER, ValidMachines.TYPRINTER);
        assertNotNull(uniqueMachineList.findMachine(new MachineName("TYPrinter")));
    }

    @Test
    public void findMachineReturnsNotNull() {
        uniqueMachineList.add(ValidMachines.JJPRINTER);
        assertNotNull(uniqueMachineList.findMachine(new MachineName("JJPrinter")));
    }

    @Test
    public void findMachineReturnsNull() {
        assertNull(uniqueMachineList.findMachine(new MachineName("JJPrinter")));
    }

    @Test
    public void addJobToMachineList_returnsMachineNotFoundException() {
        thrown.expect(MachineNotFoundException.class);
        uniqueMachineList.add(ValidMachines.TYPRINTER);
        uniqueMachineList.addJobToMachineList(ValidJobs.IDCP);
    }




}
