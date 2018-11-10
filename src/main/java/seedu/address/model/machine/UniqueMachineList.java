package seedu.address.model.machine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.JobMachineTuple;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FocusMachineRequestEvent;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.Status;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.job.exceptions.JobOngoingException;
import seedu.address.model.machine.exceptions.DuplicateMachineException;
import seedu.address.model.machine.exceptions.MachineDisabledException;
import seedu.address.model.machine.exceptions.MachineNotFoundException;


/**
 * A list of machines that ensures uniqueness in Machine names
 */
public class UniqueMachineList {
    private static final Logger logger = LogsCenter.getLogger(UniqueMachineList.class);
    private final ObservableList<Machine> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent machine
     */
    public boolean contains(Machine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMachine);
    }

    /**
     * Returns true if the list cotains an equivalent machine name
     */

    public boolean containsSameNameMachine(Machine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameNamedMachine);
    }

    /**
     * Replaces the contents of this list with {@code machines}.
     * {@code machines} must not contain duplicate machines.
     */
    public void setMachines(List<Machine> machines) {
        requireNonNull(machines);
        if (!machinesAreUnique(machines)) {
            throw new DuplicateMachineException();
        }
        List<Machine> temp = FXCollections.observableArrayList();
        for (Machine machine : machines) {
            temp.add(new Machine(machine));
        }
        internalList.setAll(temp);
    }

    /**
     * Replaces the machine {@code target} in the list with {@code editedMachine}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedMachine} must not be the same as another existing person in the list.
     */
    public void setMachine(Machine target, Machine editedMachine) {
        requireAllNonNull(target, editedMachine);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MachineNotFoundException();
        }

        if (!target.isSameMachine(editedMachine) && contains(editedMachine)) {
            throw new DuplicateMachineException();
        }

        internalList.set(index, editedMachine);
    }

    /**
     * Returns the machine, given the machineName
     *
     * @param machineName
     * @return
     */
    public Machine findMachine(MachineName machineName) {
        for (Machine machine : internalList) {
            if (machine.getName().equals(machineName)) {
                return machine;
            }
        }
        return null;
    }

    /**
     * Adds a job the machine {@code target} jobs list
     */
    public void addJobToMachineList(Job job) throws MachineNotFoundException, MachineDisabledException {
        requireAllNonNull(job);

        Machine target = this.findMachine(job.getMachineName());

        if (target == null) {
            throw new MachineNotFoundException();
        }

        if (target.getStatus() == MachineStatus.DISABLED) {
            throw new MachineDisabledException();
        }
        target.addJob(job);
        target.reSortJobList();
        EventsCenter.getInstance().post(new FocusMachineRequestEvent(new JobMachineTuple(job, target)));
    }

    /**
     * Remove a job from the machine {@code target} jobs list
     */
    public void removeJobFromMachineList(JobName job) throws JobNotFoundException {
        requireAllNonNull(job);

        for (Machine m : internalList) {
            Job query = m.findJob(job);
            if (query != null) {
                if (query.getStatus() == Status.ONGOING) {
                    throw new JobOngoingException();
                }
                m.removeJob(query);
                EventsCenter.getInstance().post(new FocusMachineRequestEvent(new JobMachineTuple(query, m)));
                return;
            }
        }
        throw new JobNotFoundException();
    }


    /**
     * Adds the Machine to the list
     * The Machine must not exist in the list
     *
     * @param toAdd
     */
    public void add(Machine toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMachineException();
        }

        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent machine from the list
     * The machine must exist in the list
     */
    public void remove(Machine toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MachineNotFoundException();
        }
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Machine> asUnmodifiableObservableList() {
        ObservableList<Machine> listCopy = FXCollections.observableArrayList();
        for (Machine machine : internalList) {
            //listCopy.add(machine.clone())
        }
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns true if {@code machines} contains only unique machines
     */
    private boolean machinesAreUnique(List<Machine> machines) {
        for (int i = 0; i < machines.size() - 1; i++) {
            for (int j = i + 1; j < machines.size(); j++) {
                if (machines.get(i).isSameMachine(machines.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Machine getMostFreeMachine() {
        return getMostFreeMachine(null);
    }

    public Machine getMostFreeMachine(Machine otherThanMe) {
        long minimumTime = Long.MAX_VALUE;
        Machine mostFreeMachine = null;

        for (Machine machine : internalList) {
            if ((machine.getTotalDuration() < minimumTime) && (machine.getStatus().equals(MachineStatus.ENABLED))
                && !(machine == otherThanMe)) {
                minimumTime = machine.getTotalDuration();
                mostFreeMachine = machine;
            }
        }

        if (mostFreeMachine != null) {
            return mostFreeMachine;
        } else {
            throw new MachineNotFoundException();
        }
    }

    /**
     * Returns true if any machine has job at the front of the queue
     *
     * @param job
     */
    public boolean isTopJob(JobName job) {
        for (Machine m : internalList) {
            if (m.getJobs().isEmpty()) {
                continue;
            }
            if (m.getJobs().get(0).getJobName().equals(job)) {
                return true;
            }
        }
        return false;
    }

}
