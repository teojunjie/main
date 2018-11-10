package seedu.address.testutil.builders;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.machine.EditMachineCommand.EditMachineDescriptor;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditMachineDescriptor objects.
 */
public class EditMachineDescriptorBuilder {

    private EditMachineDescriptor descriptor;

    public EditMachineDescriptorBuilder() {
        descriptor = new EditMachineDescriptor();
    }

    public EditMachineDescriptorBuilder(EditMachineDescriptor descriptor) {
        this.descriptor = new EditMachineDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMachineDescriptor} with fields containing {@code person}'s details
     */
    public EditMachineDescriptorBuilder(Machine machine) {
        descriptor = new EditMachineDescriptor();
        descriptor.setName(machine.getName());
        descriptor.setStatus(machine.getStatus());
        descriptor.setJobs(machine.getJobs());
        descriptor.setTags(machine.getTags());
    }

    /**
     * Sets the {@code MachineName} of the {@code EditMachineDescriptor} that we are building.
     */
    public EditMachineDescriptorBuilder withName(String name) {
        descriptor.setName(new MachineName(name));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditMachineDescriptor} that we are building.
     */
    public EditMachineDescriptorBuilder withStatus(MachineStatus status) {
        descriptor.setStatus(status);
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMachineDescriptor}
     * that we are building.
     */
    public EditMachineDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditMachineDescriptor build() {
        return descriptor;
    }
}
