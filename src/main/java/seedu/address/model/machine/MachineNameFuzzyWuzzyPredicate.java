package seedu.address.model.machine;

import java.util.List;
import java.util.function.Predicate;

import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Tests that a {@code Machines}'s {@code Name} fufills the fuzzywuzzy minimum ration.
 */
public class MachineNameFuzzyWuzzyPredicate implements Predicate<Machine> {

    private final List<String> keywords;

    public MachineNameFuzzyWuzzyPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Machine machine) {
        return keywords.stream()
            .anyMatch(keyword -> FuzzySearch.partialRatio(machine.getName().fullName, keyword) > 50);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MachineNameFuzzyWuzzyPredicate // instanceof handles nulls
            && keywords.equals(((MachineNameFuzzyWuzzyPredicate) other).keywords)); // state check
    }
}
