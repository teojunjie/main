package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;

/**
 * A list of Jobs whose elements are not repeated
 */
public class UniqueJobList {

    private static final Logger logger = LogsCenter.getLogger(UniqueJobList.class);
    private final ObservableList<Job> internalList = FXCollections.observableArrayList();
    private Predicate<Job> cleanJobPredicate = job -> job.isCompleted() || job.isCancelled() || job.isDeleting();

    /**
     * Returns true if the list contains an equivalent job as the given argument.
     */
    public boolean contains(Job toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasSameName);

    }

    /**
     * sorts the internal list
     */
    public void reSortList() {
        FXCollections.sort(internalList, new JobComparator());
    }

    /**
     * Adds a job to the list.
     * The jpb must not already exist in the list.
     */
    public void add(Job toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateJobException();
        }
        internalList.add(toAdd);
    }

    /**
     * Shifts Jobs according to index offset
     */
    public void shift(Job toShift, int shiftBy) {
        requireNonNull(toShift);
        int currentIndex = internalList.indexOf(toShift);
        //removing job
        internalList.remove(currentIndex);

        //re-addition
        if (currentIndex - shiftBy > size()) {
            internalList.add(toShift);
        } else if (currentIndex - shiftBy < 0) {
            internalList.add(0, toShift);
        } else {
            internalList.add(currentIndex - shiftBy, toShift);
        }
        FXCollections.sort(internalList, new JobComparator());
    }

    /**
     * Removes the equivalent job from the list.
     * The job must exist in the list.
     */
    public void remove(Job toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new JobNotFoundException();
        }
        internalList.remove(toRemove);
    }

    public void setJobs(UniqueJobList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        FXCollections.sort(internalList, new JobComparator());
    }

    /**
     * Replaces the contents of this list with {@code jobs}.
     * {@code jobs} must not contain duplicate jobs.
     */
    public void setJobs(List<Job> jobs) {
        requireAllNonNull(jobs);
        if (!jobsAreUnique(jobs)) {
            throw new DuplicateJobException();
        }

        internalList.setAll(jobs);
        FXCollections.sort(internalList, new JobComparator());
    }

    /**
     * Replaces the job {@code target} in the list with {@code editedJob}.
     * {@code target} must exist in the list.
     * The job identity of {@code editedJob} must not be the same as another existing job in the list.
     */
    public void setJob(Job target, Job editedJob) {
        requireAllNonNull(target, editedJob);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new JobNotFoundException();
        }

        if (!target.isSameJob(editedJob) && contains(editedJob)) {
            throw new DuplicateJobException();
        }

        internalList.set(index, editedJob);
    }

    /**
     * Returns a job by name
     */
    public Job get(String jobName) {
        requireNonNull(jobName);
        logger.info("Jobs size : " + Integer.toString(internalList.size()));
        logger.info("Doing for loop for jobs");
        for (Job j : internalList) {
            logger.info(j.getJobName().fullName);
            if (j.getJobName().fullName.equals(jobName)) {
                logger.info("Job name matches!!");
                Job changedJob = new Job(j);
                return changedJob;
            }
        }
        return null;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Job> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns a sorted list based on custom comparator
     */
    public ObservableList<Job> asUnmodifiableObservableSortedList() {
        FXCollections.sort(internalList, new JobComparator());
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     *
     * checks whether the given object is equal to the UniqueJobList
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueJobList // instanceof handles nulls
            && internalList.equals(((UniqueJobList) other).internalList));
    }

    /**
     * Returns true if the list has no repetition
     */
    public boolean jobsAreUnique(List<Job> jobs) {
        for (int i = 0; i < jobs.size() - 1; i++) {
            for (int j = i + 1; j < jobs.size(); j++) {
                if (jobs.get(i).isSameJob(jobs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the number of distinct elements in the list
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns the job, given the jobName
     *
     * @param jobName
     * @return
     */
    public Job findJob(JobName jobName) {
        for (Job job : internalList) {
            if (job.getJobName().equals(jobName)) {
                return job;
            }
        }
        return null;
    }

    public long getTotalTime() {
        return internalList.stream()
            .filter(job -> (job.getStatus() == Status.ONGOING) || (job.getStatus() == Status.QUEUED))
            .mapToLong(job -> job.getDuration()).sum();
    }

    /**
     * Replaces the job {@code target} in the list with {@code editedJob}.
     * {@code target} must exist in the list.
     * The job identity of {@code editedJob} must not be the same as another existing job in the list.
     */
    public void updateJob(Job target, Job editedJob) {
        requireAllNonNull(target, editedJob);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new JobNotFoundException();
        }

        if (target.isSameJob(editedJob) && contains(editedJob)) {
            throw new DuplicateJobException();
        }

        internalList.set(index, editedJob);
    }

    /**
     * Starts the Job
     */
    public void startJob(JobName name) {
        requireAllNonNull();
        findJob(name).startJob();
    }

    /**
     * Cancels the Job
     */
    public void cancelJob(JobName name) {
        requireAllNonNull();
        findJob(name).cancelJob();
    }

    /**
     * Restarts the Job
     */
    public void restartJob(JobName name) {
        requireAllNonNull();
        findJob(name).restartJob();
    }

    /**
     * Clears the entire jobs list
     */
    public void clearJobs() {
        internalList.clear();
    }

    /**
     * Clears jobs that are of status
     * 1) FINISHED
     * 2) CANCELLED
     * 3) DELETING
     */

    public void cleanJobs() {
        internalList.removeIf(cleanJobPredicate);
    }

    /**
     * Returns true if internal list still have cleanable jobs
     */

    public boolean hasCleanableJobs() {
        return internalList.filtered(cleanJobPredicate).size() != 0;
    }


    public void requestDeletion(JobName name) {
        findJob(name).setStatus(Status.DELETING);
    }


    //============================= swap queue number operations =======================================//

    /**
     * Swaps job @param jobToBeReplaced with @param replaceWith
     * in the queue and updates it
     */
    public void replaceJob(Job jobToBeReplaced, Job replaceWith) {

        int targetIndex = internalList.indexOf(jobToBeReplaced);

        if (targetIndex == -1 || targetIndex > size()) {
            throw new JobNotFoundException();
        }
        internalList.set(targetIndex, new Job(replaceWith));
    }

    /**
     * sets a jobStatus to finish.
     */
    public void finishJob(Job job) {
        job.finishJob();
    }


    //============================= queue operations =======================================//

    /**
     * Queue comparator for job
     */
    class JobComparator implements Comparator<Job> {

        @Override
        public int compare(Job j1, Job j2) {
            return j2.hasHigherDisplayPriority(j1);
        }
    }

}
