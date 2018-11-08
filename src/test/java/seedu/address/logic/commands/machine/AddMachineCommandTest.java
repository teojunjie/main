package seedu.address.logic.commands.machine;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Username;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.person.Person;
import seedu.address.testutil.MachineBuilder;

public class AddMachineCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullMachine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMachineCommand(null);
    }

    @Test
    public void execute_machineAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMachineAdded modelStub = new ModelStubAcceptingMachineAdded();
        Machine validMachine = new MachineBuilder().build();

        CommandResult commandResult = new AddMachineCommand(validMachine).execute(modelStub, commandHistory);

        assertEquals(String.format(AddMachineCommand.MESSAGE_SUCCESS, validMachine), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validMachine), modelStub.machinesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateMachine_throwsCommandException() throws Exception {
        Machine validMachine = new MachineBuilder().build();
        AddMachineCommand addMachineCommand = new AddMachineCommand(validMachine);
        ModelStub modelStub = new AddMachineCommandTest.ModelStubWithMachine(validMachine);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMachineCommand.MESSAGE_DUPLICATE_MACHINE);
        addMachineCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Machine m1 = new MachineBuilder().withName("Machine1").build();
        Machine m2 = new MachineBuilder().withName("Machine2").build();
        AddMachineCommand addm1Command = new AddMachineCommand(m1);
        AddMachineCommand addm2Command = new AddMachineCommand(m2);

        // same object -> returns true
        assertTrue(addm1Command.equals(addm1Command));

        // same values -> returns true
        AddMachineCommand addm1CommandCopy = new AddMachineCommand(m1);
        assertTrue(addm1Command.equals(addm1CommandCopy));

        // different types -> returns false
        assertFalse(addm1Command.equals(1));

        // null -> returns false
        assertFalse(addm1Command.equals(null));

        // different person -> returns false
        assertFalse(addm1Command.equals(addm2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteJob(JobName job) {

        }

        public void startJob(JobName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cancelJob(JobName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restartJob(JobName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void swapJobs(JobName jobname1, JobName jobName2) {
            return;
        }

        @Override
        public void finishJob(Job job) {

        }

        @Override
        public void requestDeletion(JobName jobName) {

        }

        @Override
        public int getTotalNumberOfJobsDisplayed() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void moveJobToMachine(Job job, Machine targetMachine) {

        }

        @Override
        public void autoMoveJobs(Machine currentMachine, Machine targetMachine) {

        }

        @Override
        public Machine findMachine(MachineName machinename) {
            return null;
        }

        @Override
        public void updateJob(Job oldJob, Job updatedJob) {

        }

        @Override
        public Job findJob(JobName name) {
            return null;
        }

        @Override
        public void addMachine(Machine machine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateMachine(Machine target, Machine editedMachine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Machine getMostFreeMachine() {
            return null;
        }

        @Override
        public void removeMachine(Machine machine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasMachine(Machine machine) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasSameMachineName(Machine machine) {
            return false;
        }

        @Override
        public void flushMachine(Machine toFlushMachine) {

        }

        @Override
        public void cleanMachine(Machine toCleanMachine) {

        }

        @Override
        public void addAdmin(Admin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAdmin(Admin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAdmin(Admin admin, Admin updatedAdmin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLogin(Admin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearLogin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Admin findAdmin(Username username) {
            return null;
        }

        @Override
        public int numAdmins() {
            return 0;
        }


        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Admin> getFilteredAdminList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAdminList(Predicate<Admin> predicate) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Machine> getFilteredMachineList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMachineList(Predicate<Machine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredJobListInAllMachines(Predicate<Job> predicate) {
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void adminLoginCommitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void adminLogoutCommitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isRedoLogin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isUndoLogout() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Admin currentlyLoggedIn() {
            return null;
        }

        @Override
        public boolean isUndoLogin() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Machine.
     */
    private class ModelStubWithMachine extends ModelStub {
        private final Machine machine;

        ModelStubWithMachine(Machine machine) {
            requireNonNull(machine);
            this.machine = machine;
        }

        @Override
        public boolean hasMachine(Machine machine) {
            requireNonNull(machine);
            return this.machine.isSameMachine(machine);
        }

        @Override
        public Machine findMachine(MachineName machineName) {
            requireNonNull(machineName);
            if (this.machine.getName().equals(machineName)) {
                return machine;
            }
            return null;
        }

        @Override
        public boolean isLoggedIn() {
            return true;
        }
    }

    /**
     * A Model stub that is logged in.
     */
    private class ModelStubNonAdmin extends ModelStub {
        ModelStubNonAdmin() {
        }

        @Override
        public boolean isLoggedIn() {
            return true;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingMachineAdded extends ModelStub {
        final ArrayList<Machine> machinesAdded = new ArrayList<>();

        @Override
        public boolean hasMachine(Machine machine) {
            requireNonNull(machine);
            return machinesAdded.stream().anyMatch(machine::isSameMachine);
        }

        @Override
        public Machine findMachine(MachineName machineName) {
            requireNonNull(machineName);
            for (Machine machine : machinesAdded) {
                if (machine.getName().equals(machineName)) {
                    return machine;
                }
            }
            return null;
        }

        @Override
        public void addMachine(Machine machine) {
            requireNonNull(machine);
            machinesAdded.add(machine);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean isLoggedIn() {
            return true;
        }
    }

}
