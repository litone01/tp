package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.common.Category;
import seedu.address.model.common.Date;
import seedu.address.model.common.Name;
import seedu.address.model.common.Tag;


/**
 * Represents an event in SOChedule.
 */
public class Event {
    // Fields
    private final Name name;
    private final Date startDate;
    private final Time startTime;
    private final Date endDate;
    private final Time endTime;
    private final Category category;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Name field must be present and not null.
     */
    public Event(Name name, Date startDate, Time startTime,
                Date endDate, Time endTime, Category category, Set<Tag> tags) {
        requireAllNonNull(name, startDate, endDate);
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.category = category;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return this.name;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Time getEndTime() {
        return this.endTime;
    }

    public Category getCategory() {
        return this.category;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    /**
     * Returns true if both tasks have the same name.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName());
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getStartTime().equals(getStartTime())
                && otherEvent.getEndDate().equals(getEndDate())
                && otherEvent.getEndTime().equals(getEndTime())
                && otherEvent.getCategory().equals(getCategory())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Start Date: ")
                .append(getStartDate())
                .append("; Start Time: ")
                .append(getStartTime())
                .append("; End Date: ")
                .append(getEndDate())
                .append("; End Time: ")
                .append(getEndTime())
                .append("; Category: ")
                .append(getCategory());

        Set<seedu.address.model.common.Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}