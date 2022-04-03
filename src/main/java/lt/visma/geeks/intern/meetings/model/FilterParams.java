package lt.visma.geeks.intern.meetings.model;

import java.time.LocalDateTime;
import java.util.Optional;

public class FilterParams {
    private final Optional<String> description;
    private final Optional<Integer> responsiblePersonId;
    private final Optional<MeetingCategory> category;
    private final Optional<MeetingType> type;
    private final Optional<LocalDateTime>  fromDate;
    private final Optional<LocalDateTime>  toDate;
    private final Optional<Integer> attendees;

    private FilterParams(Builder builder) {
        description = (builder.description == null || builder.description.isBlank())?  Optional.empty() : Optional.of(builder.description);
        responsiblePersonId = (builder.responsiblePersonId == -1)? Optional.empty() : Optional.of(builder.responsiblePersonId);
        category = (builder.category == null)? Optional.empty() : Optional.of(builder.category);
        type =  (builder.type == null)? Optional.empty() : Optional.of(builder.type);;
        fromDate = (builder.fromDate == null)? Optional.empty() : Optional.of(builder.fromDate);;
        toDate =  (builder.toDate == null)? Optional.empty() : Optional.of(builder.toDate);;
        attendees =  (builder.attendees == -1)? Optional.empty() : Optional.of(builder.attendees);;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<Integer> getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public Optional<MeetingCategory> getCategory() {
        return category;
    }

    public Optional<MeetingType> getType() {
        return type;
    }

    public Optional<LocalDateTime> getFromDate() {
        return fromDate;
    }

    public Optional<LocalDateTime> getToDate() {
        return toDate;
    }

    public Optional<Integer> getAttendees() {
        return attendees;
    }

    public static class Builder {
        private String description;
        private int responsiblePersonId = -1;
        private MeetingCategory category;
        private MeetingType type;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
        private int attendees = -1;

        public Builder description(final String description) {
            this.description = description;
            return this;
        }
        public Builder responsiblePersonId(final int responsiblePersonId) {
            this.responsiblePersonId = responsiblePersonId;
            return this;
        }
        public Builder category(final MeetingCategory category) {
            this.category = category;
            return this;
        }
        public Builder type(final MeetingType type) {
            this.type = type;
            return this;
        }
        public Builder fromDate(final LocalDateTime fromDate) {
            this.fromDate = fromDate;
            return this;
        }
        public Builder toDate(final LocalDateTime toDate) {
            this.toDate = toDate;
            return this;
        }
        public Builder attendees(final int attendees) {
            this.attendees = attendees;
            return this;
        }
        public FilterParams build() {
            return new FilterParams(this);
        }
    }
}
