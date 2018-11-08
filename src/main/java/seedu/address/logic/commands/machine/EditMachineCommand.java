package seedu.address.logic.commands.machine;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MACHINE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MACHINES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing machine in the address book.
 */
public class EditMachineCommand extends Command {

    public static final String COMMAND_WORD = "editMachine";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Machine identified "
        + "by machine name. "
        + "Existing values will be overwritten by the input values.\n" + "Parameters: MACHINE_NAME " + "[" + PREFIX_NAME
        + "NAME] " + "[" + PREFIX_MACHINE_STATUS + "MACHINE_STATUS] " + "[" + PREFIX_TAG + "TAG]...\n" + "Example: "
        + COMMAND_WORD + " MyPrinter " + PREFIX_NAME + "YourPrinter " + PREFIX_MACHINE_STATUS + "ENABLED";

    public static final String MESSAGE_EDIT_MACHINE_SUCCESS = "Edited Machine: %1$s";
    public static final String MESSAGE_MACHINE_NOT_FOUND = "Machine does not exist";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MACHINE = "This Machine already exists in the address book.";
    public static final String MESSAGE_DUPLCIATE_MACHINE_NAME =
        "This machine name is already being used in the address book.";
    public static final String MESSAGE_NO_CHANGES_DETECTED = "No changes detected";
    private static final String MESSAGE_ACCESS_DENIED =
        "Non-admin user is not allowed to edit a machine in maker manager";
    private final MachineName machineName;
    private final EditMachineDescriptor editMachineDescriptor;


    /**
     * @param machineName           of the machine in the filtered machine list to edit
     * @param editMachineDescriptor details to edit the machine with
     */
    public EditMachineCommand(MachineName machineName, EditMachineDescriptor editMachineDescriptor) {
        requireNonNull(machineName);
        requireNonNull(editMachineDescriptor);

        this.machineName = machineName;
        this.editMachineDescriptor = new EditMachineDescriptor(editMachineDescriptor);
    }

    @Override public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isLoggedIn()) {
            throw new CommandException(MESSAGE_ACCESS_DENIED);
        }

        Machine machineToEdit = model.findMachine(machineName);

        if (machineToEdit == null) {
            throw new CommandException(MESSAGE_MACHINE_NOT_FOUND);
        }

        Machine editedMachine = createEditedMachine(machineToEdit, editMachineDescriptor);

        if (model.hasSameMachineName(editedMachine)) {
            if (editedMachine.isSameNamedMachine(machineToEdit)) {
                if (editedMachine.hasSameMachineParameters(machineToEdit)) {
                    throw new CommandException(MESSAGE_NO_CHANGES_DETECTED);
                }
            } else {
                throw new CommandException(MESSAGE_DUPLCIATE_MACHINE_NAME);
            }
        }

        model.updateMachine(machineToEdit, editedMachine);
        model.updateFilteredMachineList(PREDICATE_SHOW_ALL_MACHINES);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_MACHINE_SUCCESS, editedMachine));
    }

    /**
     * Creates and returns a {@code Machine} with the details of {@code machineToEdit}
     * edited with {@code editMachineDescriptor}.
     */
    private static Machine createEditedMachine(Machine machineToEdit, EditMachineDescriptor editMachineDescriptor) {
        assert machineToEdit != null;

        MachineName updatedName = editMachineDescriptor.getName().orElse(machineToEdit.getName());
        List<Job> updatedJobs = editMachineDescriptor.getJobs().orElse(machineToEdit.getJobs());
        Set<Tag> updatedTags = editMachineDescriptor.getTags().orElse(machineToEdit.getTags());
        MachineStatus updatedStatus = editMachineDescriptor.getStatus().orElse(machineToEdit.getStatus());

        return new Machine(updatedName, updatedJobs, updatedTags, updatedStatus);
    }

    @Override public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMachineCommand)) {
            return false;
        }

        // state check
        EditMachineCommand e = (EditMachineCommand) other;
        return machineName.equals(e.machineName) && editMachineDescriptor.equals(e.editMachineDescriptor);
    }

    /**
     * Stores the details to edit the machine with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditMachineDescriptor {
        private MachineName name;
        private List<Job> jobs;
        private Set<Tag> tags;
        private MachineStatus status;

        public EditMachineDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMachineDescriptor(EditMachineDescriptor toCopy) {
            setName(toCopy.name);
            setJobs(toCopy.jobs);
            setTags(toCopy.tags);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, jobs, tags, status);
        }

        public void setName(MachineName name) {
            this.name = name;
        }

        public Optional<MachineName> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code jobs} to this object's {@code jobs}.
         * A defensive copy of {@code jobs} is used internally.
         */
        public void setJobs(List<Job> jobs) {
            this.jobs = (jobs != null) ? new ArrayList<>(jobs) : null;
        }

        /**
         * Returns an unmodifiable jobs list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code jobs} is null.
         */

        public Optional<List<Job>> getJobs() {
            return (jobs != null) ? Optional.of(Collections.unmodifiableList(jobs)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setStatus(MachineStatus status) {
            this.status = status;
        }

        public Optional<MachineStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMachineDescriptor)) {
                return false;
            }

            // state check
            EditMachineDescriptor e = (EditMachineDescriptor) other;

            return getName().equals(e.getName()) && getJobs().equals(e.getJobs()) && getTags().equals(e.getTags())
                && getStatus().equals(e.getStatus());
        }
    }
}
