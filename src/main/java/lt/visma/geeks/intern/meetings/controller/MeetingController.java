package lt.visma.geeks.intern.meetings.controller;

import lt.visma.geeks.intern.meetings.model.*;
import lt.visma.geeks.intern.meetings.repo.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for Internal Meeting application.
 *
 * @author Emilis Margevicius
 * @version 0.1.0
 * @since 0.1.0
 * @see Meeting
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeetingController {
    @Autowired
    private MeetingRepository repository;
    /**
     * Create new meeting endpoint
     * @param meeting - RequestBody of the meeting
     * @return Created {@link Meeting}
     * @see Meeting
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/meetings")
    public Meeting createNewMeeting(@RequestBody Meeting meeting) {
        repository.addMeeting(meeting);
        return meeting;
    }

    /**
     * Delete meeting endpoint
     * @param userId - Logged in persons id
     * @param meetingId - Meeting id
     * @see Meeting
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{userId}/meetings/{meetingId}")
    public void removeMeeting(@PathVariable int userId, @PathVariable int meetingId) {
        repository.removeMeeting(meetingId, userId);
    }

    /**
     * Add an attendee to the meeting
     * @param meetingId - The meeting's id to which add the attendee
     * @param newAttendee - The {@link Attendee} to be added to the meeting
     * @return a String if there was a problem. Else it returns null
     * @see Attendee
     */
    @PostMapping("/meetings/{meetingId}")
    public String addAttendee(@PathVariable int meetingId, @RequestBody Attendee newAttendee) {
        return repository.addAttendee(meetingId, newAttendee);
    }

    /**
     * Remove an attendee from the meeting
     * @param meetingId - The meeting id from which to remove the attendee
     * @param userId - The removed attendee's id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/meetings/{meetingId}/{userId}")
    public void removeAttendee(@PathVariable int meetingId, @PathVariable int userId) {
        repository.removeAttendee(meetingId, userId);
    }

    /**
     * Find meetings endpoint. Can pass optional parameters to filter results.
     * For example, to filter by description ('java') and category ('CODE_MONKEY') the request would look like this:
     * `/api/meetings/?desc=java&category=CODE_MONKEY
     * @param responsible - Optional. The ID of the responsible person for the meeting by which to filter results.
     * @param desc - Optional. The pattern in the description by which to filter results.
     * @param category - Optional. The category of the meetings by which to filter results.
     * @param type - Optional. The type of the meeting by which to filter results.
     * @param fromDate - Optional. Filter meetings that happen before this date.
     * @param toDate - Optional. Filter meetings that happen till this date
     * @param attendees - Optional. Filter meetings that have at least this amount of attendees
     * @return A list of meetings.
     * @see Meeting
     */
    @GetMapping("/meetings")
    public List<Meeting> findMeetings(
            @RequestParam(required = false) Integer responsible,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) MeetingCategory category,
            @RequestParam(required = false) MeetingType type,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            @RequestParam(required = false) Integer attendees
    ) {
        FilterParams filterParams = FilterParams.builder()
                .responsiblePersonId(responsible)
                .description(desc)
                .fromDate(fromDate)
                .toDate(toDate)
                .attendees(attendees)
                .category(category)
                .type(type)
                .build();

        return repository.findMeetings(filterParams);
    }
}
