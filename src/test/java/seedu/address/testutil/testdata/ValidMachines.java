package seedu.address.testutil.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineStatus;
import seedu.address.testutil.builders.MachineBuilder;

/**
 * A utility class containing a list of {@code Machine} objects to be used in tests.
 */

public class ValidMachines {

    public static final Machine JJPRINTER =
        new MachineBuilder().withMachineName("JJPrinter").withMachineStatus(MachineStatus.ENABLED).build();

    public static final Machine TYPRINTER =
        new MachineBuilder().withMachineName("TYPrinter").withMachineStatus(MachineStatus.DISABLED).build();

    public static final Machine JIAHUAPRINTER =
        new MachineBuilder().withMachineName("JiaHuaPrinter").withMachineStatus(MachineStatus.ENABLED).build();

    public static final Machine ONGOINGJOBPRINTER =
        new MachineBuilder().withMachineName("ONGOINGJOBPRINTER").withJobs(new ArrayList<Job>())
            .withMachineStatus(MachineStatus.ENABLED).build();

    public static AddressBook getMachinesData() {
        AddressBook makerManagerMachinesData = new AddressBook();
        for (Machine m : getValidMachines()) {
            makerManagerMachinesData.addMachine(m);
        }
        return makerManagerMachinesData;
    }

    public static List<Machine> getValidMachines() {
        return new ArrayList<>(Arrays.asList(JJPRINTER, TYPRINTER, JIAHUAPRINTER));
    }
}
