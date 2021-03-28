package seedu.sochedule.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.sochedule.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Set;

import seedu.sochedule.commons.core.Messages;
import seedu.sochedule.commons.core.index.Index;
import seedu.sochedule.logic.commands.exceptions.CommandException;
import seedu.sochedule.model.Model;
import seedu.sochedule.model.common.Category;
import seedu.sochedule.model.common.Date;
import seedu.sochedule.model.common.Name;
import seedu.sochedule.model.common.Tag;
import seedu.sochedule.model.task.Priority;
import seedu.sochedule.model.task.Task;


/**
 * Marks the status of an incomplete task as complete.
 */
public class DoneTaskCommand extends Command {
    public static final String COMMAND_WORD = "done_task";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_TASK_ALREADY_COMPLETE = "This task has already been marked as complete.";

    private final Index targetIndex;

    public DoneTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command and returns the result message.
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        // verify if index is valid
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex.getZeroBased());
        // verify if task is already completed
        if (taskToComplete.isComplete()) {
            throw new CommandException(MESSAGE_TASK_ALREADY_COMPLETE);
        }

        Task completedTask = createCompletedTask(taskToComplete);

        // replace the old task with the new and completed task and update
        model.setTask(taskToComplete, completedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToComplete));
    }

    /**
     * Creates and returns a {@code Task} with the same details of {@code taskToComplete},
     * but completionStatus as complete.
     *
     * @param taskToComplete task to be marked as complete.
     * @return a completed task.
     */
    private static Task createCompletedTask(Task taskToComplete) {
        assert taskToComplete != null;

        Task completedTask = copyTask(taskToComplete);
        completedTask.markTaskAsDone();
        return completedTask;
    }

    /**
     * Copies the task given and returns a new task with the same details as the given task.
     *
     * @param taskToCopy task to be copied, here is the task to be completed.
     * @return a copied task.
     */
    private static Task copyTask(Task taskToCopy) {
        Name taskName = taskToCopy.getName();
        Date deadline = taskToCopy.getDeadline();
        Priority priority = taskToCopy.getPriority();
        Set<Category> categories = taskToCopy.getCategories();
        Set<Tag> tags = taskToCopy.getTags();

        Task completedTask = new Task(taskName, deadline, priority, categories, tags);
        if (taskToCopy.isPinned()) {
            completedTask.pin();
        }
        return completedTask;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DoneTaskCommand) other).targetIndex)); // state check
    }

}
