package lt.visma.geeks.intern.meetings.model;

import lombok.Builder;
import lombok.Getter;

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
@Getter
@Builder
public class FilterParams {
    private final String description;
    private final Integer responsiblePersonId;
    private final MeetingCategory category;
    private final MeetingType type;
    private final LocalDateTime  fromDate;
    private final LocalDateTime  toDate;
    private final Integer attendees;
}
