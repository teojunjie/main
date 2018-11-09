package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.testdata.TypicalJobs.BUMBERBEE;
import static seedu.address.testutil.testdata.TypicalJobs.IDCP;
import static seedu.address.testutil.testdata.TypicalJobs.NEWPROJECT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;

public class UniqueJobListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueJobList uniqueJobList = new UniqueJobList();

    @Test
    public void contains_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.contains(null);
    }

    @Test
    public void contains_jobNotInList_returnsFalse() {
        assertFalse(uniqueJobList.contains(IDCP));
    }

    @Test
    public void contains_jobInList_returnsTrue() {
        uniqueJobList.add(IDCP);
        assertTrue(uniqueJobList.contains(IDCP));
    }

    @Test
    public void add_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.add(null);
    }

    @Test
    public void add_duplicateJob_throwsDuplicateJobException() {
        uniqueJobList.add(IDCP);
        thrown.expect(DuplicateJobException.class);
        uniqueJobList.add(IDCP);
    }

    @Test
    public void setJob_nullTargetJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.setJob(null, IDCP);
    }

    @Test
    public void setJob_nullEditedJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.setJob(IDCP, null);
    }

    @Test
    public void setJob_targetJobNotInList_throwsJobNotFoundException() {
        thrown.expect(JobNotFoundException.class);
        uniqueJobList.setJob(IDCP, IDCP);
    }

    @Test
    public void setJob_editedJobIsSameJob_success() {
        uniqueJobList.add(IDCP);
        uniqueJobList.setJob(IDCP, IDCP);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        expectedUniqueJobList.add(IDCP);
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void setJob_editedJobHasDifferentIdentity_success() {
        uniqueJobList.add(IDCP);
        uniqueJobList.setJob(IDCP, NEWPROJECT);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        expectedUniqueJobList.add(NEWPROJECT);
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void setJob_editedJobHasNonUniqueIdentity_throwsDuplicateJobException() {
        uniqueJobList.add(IDCP);
        uniqueJobList.add(NEWPROJECT);
        thrown.expect(DuplicateJobException.class);
        uniqueJobList.setJob(IDCP, NEWPROJECT);
    }

    @Test
    public void remove_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.remove(null);
    }

    @Test
    public void remove_jobDoesNotExist_throwsJobNotFoundException() {
        thrown.expect(JobNotFoundException.class);
        uniqueJobList.remove(IDCP);
        uniqueJobList.remove(BUMBERBEE);
    }

    @Test
    public void remove_existingJob_removesJob() {
        uniqueJobList.add(IDCP);
        uniqueJobList.remove(IDCP);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void setJobs_nullUniqueJobList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.setJobs((UniqueJobList) null);
    }

    @Test
    public void setJobs_uniqueJobList_replacesOwnListWithProvidedUniqueJobList() {
        uniqueJobList.add(IDCP);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        expectedUniqueJobList.add(NEWPROJECT);
        uniqueJobList.setJobs(expectedUniqueJobList);
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void setJobs_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.setJobs((List<Job>) null);
    }

    @Test
    public void setJobs_list_replacesOwnListWithProvidedList() {
        uniqueJobList.add(IDCP);
        List<Job> jobList = Collections.singletonList(NEWPROJECT);
        uniqueJobList.setJobs(jobList);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        expectedUniqueJobList.add(NEWPROJECT);
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void setJobs_listWithDuplicateJobs_throwsDuplicateJobException() {
        List<Job> listWithDuplicateJobs = Arrays.asList(IDCP, IDCP);
        thrown.expect(DuplicateJobException.class);
        uniqueJobList.setJobs(listWithDuplicateJobs);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueJobList.asUnmodifiableObservableList().remove(0);
    }
}
