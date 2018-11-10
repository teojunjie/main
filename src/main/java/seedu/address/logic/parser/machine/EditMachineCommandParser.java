package seedu.address.logic.parser.machine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MACHINE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.machine.EditMachineCommand;
import seedu.address.logic.commands.machine.EditMachineCommand.EditMachineDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.machine.Machine;
import seedu.address.model.machine.MachineName;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditMachineCommandParser implements Parser<EditMachineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditMachineCommand
     * and returns an EditMachineCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMachineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_MACHINE_STATUS);

        MachineName machineName;

        try {
            machineName = ParserUtil.parseMachineName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMachineCommand.MESSAGE_USAGE),
                pe);
        }

        if (machineName.fullName.equals("AUTO")) {
            throw new ParseException(Machine.MESSAGE_NAME_CONSTRAINTS);
        }

        EditMachineDescriptor editMachineDescriptor = new EditMachineDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            MachineName newName = ParserUtil.parseMachineName(argMultimap.getValue(PREFIX_NAME).get());
            if (newName.toString().equals("AUTO")) {
                throw new ParseException(Machine.MESSAGE_NAME_CONSTRAINTS);
            }
            editMachineDescriptor.setName(newName);
        }


        if (argMultimap.getValue(PREFIX_MACHINE_STATUS).isPresent()) {
            editMachineDescriptor
                .setStatus(ParserUtil.parseMachineStatus(argMultimap.getValue(PREFIX_MACHINE_STATUS).get()));
        }

        if (!editMachineDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMachineCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMachineCommand(machineName, editMachineDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
