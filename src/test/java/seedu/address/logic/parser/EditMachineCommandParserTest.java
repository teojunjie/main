package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MACHINENAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MACHINESTATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MACHINESTATUS_DESC_DISABLED;
import static seedu.address.logic.commands.CommandTestUtil.MACHINESTATUS_DESC_ENABLED;
import static seedu.address.logic.commands.CommandTestUtil.MACHINE_NAME_DESC_ENDER;
import static seedu.address.logic.commands.CommandTestUtil.MACHINE_NAME_DESC_ULTIMAKER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MACHINE_NAME_ENDER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MACHINE_NAME_ULTIMAKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.machine.EditMachineCommand;
import seedu.address.logic.parser.machine.EditMachineCommandParser;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.machine.MachineStatus;
import seedu.address.testutil.builders.EditMachineDescriptorBuilder;

public class EditMachineCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMachineCommand.MESSAGE_USAGE);

    private EditMachineCommandParser parser = new EditMachineCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, MACHINE_NAME_DESC_ULTIMAKER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_MACHINE_NAME_ENDER, EditMachineCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid name reserved Keyword
        assertParseFailure(parser, "AUTO" + NAME_DESC_AMY, Machine.MESSAGE_NAME_CONSTRAINTS);

        // invalidMachineNamewithspaces
        assertParseFailure(parser, "MachineName! dasd", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "MachineName i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, VALID_MACHINE_NAME_ENDER + INVALID_MACHINENAME_DESC,
            Machine.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_MACHINE_NAME_ENDER + INVALID_MACHINESTATUS_DESC,
            Machine.MESSAGE_WRONG_STATUS); // invalid status

        // invalid name followed by valid status
        assertParseFailure(parser, VALID_MACHINE_NAME_ENDER + INVALID_MACHINENAME_DESC + MACHINESTATUS_DESC_ENABLED,
            Machine.MESSAGE_NAME_CONSTRAINTS);

        // valid name followed by invalid status
        assertParseFailure(parser, VALID_MACHINE_NAME_ENDER + MACHINE_NAME_DESC_ULTIMAKER + INVALID_MACHINESTATUS_DESC,
            Machine.MESSAGE_WRONG_STATUS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_MACHINE_NAME_ENDER + INVALID_MACHINENAME_DESC + INVALID_MACHINESTATUS_DESC,
            Machine.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_MACHINE_NAME_ENDER + MACHINE_NAME_DESC_ULTIMAKER + MACHINESTATUS_DESC_ENABLED;

        EditMachineCommand.EditMachineDescriptor descriptor =
            new EditMachineDescriptorBuilder().withName(VALID_MACHINE_NAME_ULTIMAKER).withStatus(MachineStatus.ENABLED)
                .build();
        EditMachineCommand expectedCommand =
            new EditMachineCommand(new MachineName(VALID_MACHINE_NAME_ENDER), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_statusFieldsSpecified_success() {
        String userInput = VALID_MACHINE_NAME_ENDER + MACHINESTATUS_DESC_DISABLED;

        EditMachineCommand.EditMachineDescriptor descriptor =
            new EditMachineDescriptorBuilder().withStatus(MachineStatus.DISABLED).build();
        EditMachineCommand expectedCommand =
            new EditMachineCommand(new MachineName(VALID_MACHINE_NAME_ENDER), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameFieldsSpecified_success() {
        String userInput = VALID_MACHINE_NAME_ENDER + MACHINE_NAME_DESC_ULTIMAKER;

        EditMachineCommand.EditMachineDescriptor descriptor =
            new EditMachineDescriptorBuilder().withName(VALID_MACHINE_NAME_ULTIMAKER).build();
        EditMachineCommand expectedCommand =
            new EditMachineCommand(new MachineName(VALID_MACHINE_NAME_ENDER), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = VALID_MACHINE_NAME_ENDER + MACHINE_NAME_DESC_ENDER + MACHINE_NAME_DESC_ULTIMAKER
            + MACHINESTATUS_DESC_ENABLED;

        EditMachineCommand.EditMachineDescriptor descriptor =
            new EditMachineDescriptorBuilder().withName(VALID_MACHINE_NAME_ULTIMAKER).withStatus(MachineStatus.ENABLED)
                .build();
        EditMachineCommand expectedCommand =
            new EditMachineCommand(new MachineName(VALID_MACHINE_NAME_ENDER), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = VALID_MACHINE_NAME_ENDER + INVALID_MACHINENAME_DESC + MACHINE_NAME_DESC_ULTIMAKER;
        EditMachineCommand.EditMachineDescriptor descriptor =
            new EditMachineDescriptorBuilder().withName(VALID_MACHINE_NAME_ULTIMAKER).build();
        EditMachineCommand expectedCommand =
            new EditMachineCommand(new MachineName(VALID_MACHINE_NAME_ENDER), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        String userInput2 = VALID_MACHINE_NAME_ENDER + INVALID_MACHINENAME_DESC + MACHINE_NAME_DESC_ULTIMAKER
            + MACHINESTATUS_DESC_DISABLED;
        EditMachineCommand.EditMachineDescriptor descriptor2 =
            new EditMachineDescriptorBuilder().withName(VALID_MACHINE_NAME_ULTIMAKER).withStatus(MachineStatus.DISABLED)
                .build();
        EditMachineCommand expectedCommand2 =
            new EditMachineCommand(new MachineName(VALID_MACHINE_NAME_ENDER), descriptor2);
        assertParseSuccess(parser, userInput2, expectedCommand2);
    }

}
