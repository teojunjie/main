package seedu.address.model.machine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.UniqueJobList;
import seedu.address.model.machine.exceptions.InvalidMachineStatusException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Machine in the lab. Morphed from Persons.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Machine {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_NAME_CONSTRAINTS =
        "Names should only contain alphanumeric characters and spaces, " + "and it should not be blank";
    public static final String MESSAGE_WRONG_STATUS =
        "Status can only contain 'ENABLED' or 'DISABLED'" + "and should not be blank";
    // Identity fields
    private MachineName machineName;
    //TODO make status be more diverse, like enum
    private MachineStatus status;

    // Data fields
    //Name is a placeholder. To be replaced by Job class in the future
    private final UniqueJobList jobs = new UniqueJobList();
    private final Set<Tag> tags = new HashSet<>();

    //Display fields
    private final FilteredList<Job> filteredJobs;

    /**
     * Every field must be present and not null.
     */
    public Machine(MachineName name, List<Job> jobs, Set<Tag> tags, MachineStatus status) {
        requireAllNonNull(name, jobs, tags);
        this.machineName = name;
        this.jobs.setJobs(jobs);
        this.tags.addAll(tags);
        this.status = status;
        this.filteredJobs = new FilteredList<>(this.jobs.asUnmodifiableObservableList());
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMachine(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public MachineName getName() {
        return machineName;
    }

    public MachineStatus getStatus() {
        return status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public long getTotalDuration() {
        return jobs.getTotalTime();
    }

    /**
     * Returns true if both machines of the same name.
     * This defines a weakest notion of equality between two machines.
     */
    public boolean isSameNamedMachine(Machine otherMachine) {
        return otherMachine.getName().equals(getName());
    }

    /**
     * Returns true if the other machine has the same parameters
     * 1) name
     * 2) status
     */

    public boolean hasSameMachineParameters(Machine otherMachine) {
        return otherMachine.getName().equals(getName())
            && otherMachine.getStatus().equals(getStatus());
    }

    /**
     * Returns true if both machines of the same name and same list of Jobs.
     * This defines a weaker notion of equality between two machines.
     */
    public boolean isSameMachine(Machine otherMachine) {
        return otherMachine != null
            && otherMachine.getName().equals(getName())
            && otherMachine.getJobs().equals(getJobs());
    }

    public void setMachineStatus(MachineStatus machineStatus) throws InvalidMachineStatusException {
        if (MachineStatus.isValidMachineStatus(machineStatus)) {
            this.status = machineStatus;
        }

        throw new InvalidMachineStatusException();

    }

    /**
     * Returns true if both machines have the same identity and data fields.
     * This defines a stronger notion of equality between two machines.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Machine)) {
            return false;
        }

        Machine otherMachine = (Machine) other;
        return otherMachine.getName().equals(getName())
            && otherMachine.getStatus().equals(getStatus())
            && otherMachine.getJobs().equals(getJobs())
            && otherMachine.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(machineName, jobs, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" Tags: ");
        getTags().forEach(builder::append);

        builder.append(" Jobs: ");
        getJobs().forEach(builder::append);

        builder.append(" Status: ");
        builder.append(getStatus().toString());
        return builder.toString();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    //======================== job list methods ================================//

    /**
     * Returns an immutable Job List, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Job> getJobs() {
        return Collections.unmodifiableList(jobs.asUnmodifiableObservableList());
    }

    public Job findJob(JobName jobName) {
        return jobs.findJob(jobName);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<Job> getJobsAsFilteredObservableList() {
        return this.filteredJobs;
    }

    /**
     * Returns true if the machine still have jobs
     */

    public boolean hasJobs() {
        return jobs.size() != 0;
    }

    /**
     * Returns true if the machine still have cleanable jobs
     */

    public boolean hasCleanableJobs() {
        return jobs.hasCleanableJobs();
    }
    /**
     * Returns true if the machine contains
     * {@code job} in its list;
     */
    public boolean hasJob(Job job) {
        return jobs.contains(job);
    }

    /**
     * Adds a job to the machine job list
     */
    public void addJob(Job job) {
        jobs.add(job);
    }

    /**
     * Removes a job from the machine job list
     */
    public void removeJob(Job job) {
        jobs.remove(job);
    }

    /**
     * updates the FilteredJobList with a predicate
     */
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    public Predicate<Job> getFilteredJobListPredicate() {
        if (filteredJobs.getPredicate() == null) {
            return PREDICATE_SHOW_ALL_JOBS;
        }
        return (Predicate<Job>) filteredJobs.getPredicate();
    }

    public void replaceJob(Job jobToBeReplaced, Job replaceWith) {
        jobs.replaceJob(jobToBeReplaced, replaceWith);
    }

    /**
     * Flushes all jobs from machine
     */
    public void flushMachine() {
        jobs.clearJobs();
    }

    /**
     * Cleans all jobs in machine that satisfy the cleanJobPredicate in uniqueJobList
     */

    public void cleanMachine() {
        jobs.cleanJobs();
    }

}
