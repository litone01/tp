package seedu.sochedule.logic.parser;

import static seedu.sochedule.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.sochedule.commons.core.index.Index;
import seedu.sochedule.logic.commands.DoneTaskCommand;
import seedu.sochedule.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DoneTaskCommand object
 */
public class DoneTaskCommandParser implements Parser<DoneTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneTaskCommand
     * and returns a DoneTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneTaskCommand parse(String args) throws ParseException {
        try {
            Index index = SocheduleParserUtil.parseIndex(args);
            return new DoneTaskCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
