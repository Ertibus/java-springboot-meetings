package lt.visma.geeks.intern.meetings.model;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Object that represent available parameters to filter the meetings with.
 * Instead of a typical constructor it uses the Builder Pattern for initialization.
 *
 * @author Emilis Margevicius
 * @version 0.1.0
 * @since 0.1.0
 */
public class FilterParams {
    private final Optional<String> description;
    private final Optional<Integer> responsiblePersonId;
    private final Optional<MeetingCategory> category;
    private final Optional<MeetingType> type;
    private final Optional<LocalDateTime>  fromDate;
    private final Optional<LocalDateTime>  toDate;
    private final Optional<Integer> attendees;

    /**
     * Private constructor called by the builder
     * @param builder the builder with the needed data.
     */
    private FilterParams(Builder builder) {
        description = (builder.description == null || builder.description.isBlank())?  Optional.empty() : Optional.of(builder.description);
        responsiblePersonId = (builder.responsiblePersonId == -1)? Optional.empty() : Optional.of(builder.responsiblePersonId);
        category = (builder.category == null)? Optional.empty() : Optional.of(builder.category);
        type =  (builder.type == null)? Optional.empty() : Optional.of(builder.type);;
        fromDate = (builder.fromDate == null)? Optional.empty() : Optional.of(builder.fromDate);;
        toDate =  (builder.toDate == null)? Optional.empty() : Optional.of(builder.toDate);;
        attendees =  (builder.attendees == -1)? Optional.empty() : Optional.of(builder.attendees);;
    }

    /**
     * Get description filter
     * @return String description
     */
    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Get Responsible person ID
     * @return The ID
     */
    public Optional<Integer> getResponsiblePersonId() {
        return responsiblePersonId;
    }

    /**
     * Get the category filter
     * @return category
     * @see MeetingCategory
     */
    public Optional<MeetingCategory> getCategory() {
        return category;
    }

    /**
     * Get the type filter
     * @return meeting type
     * @see MeetingType
     */
    public Optional<MeetingType> getType() {
        return type;
    }

    /**
     * Get before date
     * @return before date
     * @see LocalDateTime
     */
    public Optional<LocalDateTime> getFromDate() {
        return fromDate;
    }

    /**
     * Get till date
     * @return till date
     * @see LocalDateTime
     */
    public Optional<LocalDateTime> getToDate() {
        return toDate;
    }

    /**
     * Get minimum attendees
     * @return min attendees
     */
    public Optional<Integer> getAttendees() {
        return attendees;
    }

    /**
     * Object's builder.
     * Usage: `FilterParams filter = new FilterParams.Builder().description("java").build();
     * @author Emilis Margevicius
     * @version 0.1.0
     * @since 0.1.0
     */
    public static class Builder {
        private String description;
        private int responsiblePersonId = -1;
        private MeetingCategory category;
        private MeetingType type;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
        private int attendees = -1;

        /**
         * Filter by description.
         * @param description pattern to look out for in the description.
         * @return Self
         */
        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Filter by responsible person ID.
         * @param responsiblePersonId id by which to filter.
         * @return Self
         */
        public Builder responsiblePersonId(final int responsiblePersonId) {
            this.responsiblePersonId = responsiblePersonId;
            return this;
        }

        /**
         * Filter by meeting category
         * @param category category by which to filter
         * @return Self
         * @see MeetingCategory
         */
        public Builder category(final MeetingCategory category) {
            this.category = category;
            return this;
        }

        /**
         * Filter by meeting type
         * @param type type of the meeting by which to filter
         * @return Self
         * @see MeetingType
         */
        public Builder type(final MeetingType type) {
            this.type = type;
            return this;
        }

        /**
         * After date for filtering
         * @param fromDate find meeting till this date
         * @return Self
         * @see LocalDateTime
         */
        public Builder fromDate(final LocalDateTime fromDate) {
            this.fromDate = fromDate;
            return this;
        }
        /**
         * Before date for filtering
         * @param toDate find meetings before this date
         * @return Self
         * @see LocalDateTime
         */
        public Builder toDate(final LocalDateTime toDate) {
            this.toDate = toDate;
            return this;
        }

        /**
         * Minimum attendees
         * @param attendees Minimum attendees
         * @return Self
         */
        public Builder attendees(final int attendees) {
            this.attendees = attendees;
            return this;
        }

        /**
         * Create FilterParams
         * @return FilterParams
         * @see FilterParams
         */
        public FilterParams build() {
            return new FilterParams(this);
        }
    }
}
