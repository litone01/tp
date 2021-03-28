package seedu.sochedule.logic.parser;

import static seedu.sochedule.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sochedule.commons.core.Messages.MESSAGE_PAST_EVENT_END_DATE_TIME;
import static seedu.sochedule.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.sochedule.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.sochedule.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.sochedule.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.sochedule.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.sochedule.logic.commands.AddEventCommand;
import seedu.sochedule.logic.commands.AddTaskCommand;
import seedu.sochedule.logic.parser.exceptions.ParseException;
import seedu.sochedule.model.common.Category;
import seedu.sochedule.model.common.Date;
import seedu.sochedule.model.common.DatePastPredicate;
import seedu.sochedule.model.common.Name;
import seedu.sochedule.model.common.Tag;
import seedu.sochedule.model.task.Priority;
import seedu.sochedule.model.task.Task;


/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DEADLINE, PREFIX_PRIORITY,
                        PREFIX_CATEGORY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DEADLINE, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        Name name = SocheduleParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Date deadline = SocheduleParserUtil.parseDate(argMultimap.getValue(PREFIX_DEADLINE).get());
        Priority priority = SocheduleParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        Set<Category> categoryList = SocheduleParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));
        Set<Tag> tagList = SocheduleParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (!isDeadlineBeforeNow(deadline)) {
            throw new ParseException(String.format(MESSAGE_PAST_EVENT_END_DATE_TIME, AddEventCommand.MESSAGE_USAGE));
        }

        Task task = new Task(name, deadline, priority, categoryList, tagList);

        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if deadline is not past.
     */
    private boolean isDeadlineBeforeNow(Date deadline) {
        return new DatePastPredicate().test(deadline);
    }
}
