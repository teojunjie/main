package seedu.address.model.job;


/**
 * Indicates different types of job priority
 */
public enum Priority {

    URGENT,
    HIGH,
    NORMAL;

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Job priority should only be specified as URGENT, HIGH or NORMAL";

    /**
     *checks if the input priority is a acceptable priority for the job
     */
    public static boolean isValidPriority(String priority) {
        return priority.equals("URGENT") || priority.equals("HIGH") || priority.equals("NORMAL");
    }


    /**
     * Checks if {@code priority1} is of higher priority than {@code priority2}
     * if it return true
     * else return false
     */
    public static boolean isHigherPriority(Priority priority1, Priority priority2) {
        Integer priority1value = priority1 == URGENT ? 2 : priority1 == HIGH ? 1 : priority1 == NORMAL ? 0 : -1;
        Integer priority2value = priority2 == URGENT ? 2 : priority2 == HIGH ? 1 : priority2 == NORMAL ? 0 : -1;

        return priority1value > priority2value;
    }

}
