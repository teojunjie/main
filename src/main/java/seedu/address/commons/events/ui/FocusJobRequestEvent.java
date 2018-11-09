package seedu.address.commons.events.ui;

import seedu.address.commons.core.JobMachineTuple;
import seedu.address.commons.events.BaseEvent;

/**
 * An event for indicating ad job has finished
 */
public class FocusJobRequestEvent extends BaseEvent {

    private final JobMachineTuple machineAndJobToFocus;

    public FocusJobRequestEvent(JobMachineTuple focusTargets) {
        machineAndJobToFocus = focusTargets;
    }

    public JobMachineTuple getMachineAndJobToFocus() {
        return machineAndJobToFocus;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
