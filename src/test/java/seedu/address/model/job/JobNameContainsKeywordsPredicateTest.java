package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.builders.JobBuilder;

public class JobNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        JobNameContainsKeywordsPredicate firstPredicate = new JobNameContainsKeywordsPredicate(
            firstPredicateKeywordList);
        JobNameContainsKeywordsPredicate secondPredicate = new JobNameContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobNameContainsKeywordsPredicate firstPredicateCopy = new JobNameContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobJobNameContainsKeywords_returnsTrue() {
        // One keyword
        JobNameContainsKeywordsPredicate predicate = new JobNameContainsKeywordsPredicate(
            Collections.singletonList("IDCP"));
        assertTrue(predicate.test(new JobBuilder().withName("IDCP").build()));

        // Multiple keywords
        predicate = new JobNameContainsKeywordsPredicate(Arrays.asList("IDCP", "Orbital"));
        assertTrue(predicate.test(new JobBuilder().withName("IDCP").build()));

    }

    @Test
    public void test_jobJobNameDoesNotContainKeywords_returnsFalse() {
        // Job name can only be one word without space
        // Zero keywords
        JobNameContainsKeywordsPredicate predicate = new JobNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new JobBuilder().withName("IDCP").build()));

        // Non-matching keyword
        predicate = new JobNameContainsKeywordsPredicate(Arrays.asList("IDCP"));
        assertFalse(predicate.test(new JobBuilder().withName("InnoventureOrbital").build()));

        // Keywords match phone, email and address, but does not match jobJobName
        predicate = new JobNameContainsKeywordsPredicate(Arrays.asList("JJ", "JJPrinter", "URGENT", "ONGOING"));
        assertFalse(predicate.test(new JobBuilder().withName("IDCP").build()));

        // Only one matching keyword
        predicate = new JobNameContainsKeywordsPredicate(Arrays.asList("Innoventure", "Orbital"));
        assertFalse(predicate.test(new JobBuilder().withName("IDCPOrbital").build()));

        // Mixed-case keywords
        predicate = new JobNameContainsKeywordsPredicate(Arrays.asList("IDCP", "Orbital"));
        assertFalse(predicate.test(new JobBuilder().withName("iDcPoBiTAl").build()));
    }
}
