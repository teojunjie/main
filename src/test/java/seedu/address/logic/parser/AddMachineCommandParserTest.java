package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MACHINENAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MACHINESTATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MACHINESTATUS_DESC_DISABLED;
import static seedu.address.logic.commands.CommandTestUtil.MACHINESTATUS_DESC_ENABLED;
import static seedu.address.logic.commands.CommandTestUtil.MACHINE_NAME_DESC_ENDER;
import static seedu.address.logic.commands.CommandTestUtil.MACHINE_NAME_DESC_ULTIMAKER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MACHINE_NAME_ULTIMAKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MACHINE_STATUS_ENABLED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.machine.AddMachineCommand;
import seedu.address.logic.parser.machine.AddMachineCommandParser;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineStatus;
import seedu.address.testutil.MachineBuilder;

public class AddMachineCommandParserTest {
    private AddMachineCommandParser parser = new AddMachineCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Machine expectedMachine =
            new MachineBuilder().withName(VALID_MACHINE_NAME_ULTIMAKER).withStatus(MachineStatus.ENABLED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MACHINE_NAME_DESC_ULTIMAKER + MACHINESTATUS_DESC_ENABLED,
            new AddMachineCommand(expectedMachine));

        // multiple names - last name accepted
        assertParseSuccess(parser, MACHINE_NAME_DESC_ENDER + MACHINE_NAME_DESC_ULTIMAKER + MACHINESTATUS_DESC_ENABLED,
            new AddMachineCommand(expectedMachine));

        // multiple Statuses - last status accepted
        assertParseSuccess(parser,
            MACHINE_NAME_DESC_ULTIMAKER + MACHINESTATUS_DESC_DISABLED + MACHINESTATUS_DESC_ENABLED,
            new AddMachineCommand(expectedMachine));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMachineCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, MACHINE_NAME_DESC_ULTIMAKER + VALID_MACHINE_STATUS_ENABLED, expectedMessage);

        // missing status prefix
        assertParseFailure(parser, VALID_MACHINE_NAME_ULTIMAKER + MACHINESTATUS_DESC_ENABLED, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MACHINE_NAME_ULTIMAKER + VALID_MACHINE_STATUS_ENABLED, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_MACHINENAME_DESC + MACHINESTATUS_DESC_ENABLED,
            Machine.MESSAGE_NAME_CONSTRAINTS);

        // invalid name Reserved keyword
        assertParseFailure(parser, " " + PREFIX_NAME + "AUTO" + MACHINESTATUS_DESC_ENABLED,
            Machine.MESSAGE_NAME_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, MACHINE_NAME_DESC_ULTIMAKER + INVALID_MACHINESTATUS_DESC,
            Machine.MESSAGE_WRONG_STATUS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MACHINE_NAME_DESC_ULTIMAKER + MACHINESTATUS_DESC_ENABLED,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMachineCommand.MESSAGE_USAGE));
    }
}
