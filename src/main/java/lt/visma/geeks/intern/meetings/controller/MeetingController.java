package lt.visma.geeks.intern.meetings.controller;

import lt.visma.geeks.intern.meetings.model.*;
import lt.visma.geeks.intern.meetings.repo.MeetingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeetingController {
    private static MeetingRepository repository;

    public MeetingController() {
        repository = new MeetingRepository();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/meetings")
    public Meeting createNewMeeting(@RequestBody Meeting meeting) {
        repository.addMeeting(meeting);
        return meeting;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{userId}/meetings/{meetingId}")
    public void removeMeeting(@PathVariable int userId, @PathVariable int meetingId) {
        repository.removeMeeting(meetingId, userId);
    }

    @PostMapping("/meetings/{meetingId}")
    public String addAttendee(@PathVariable int meetingId, @RequestBody Attendee newAttendee) {
        return repository.addAttendee(meetingId, newAttendee);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/meetings/{meetingId}/{userId}")
    public void removeAttendee(@PathVariable int meetingId, @PathVariable int userId) {
        repository.removeAttendee(meetingId, userId);
    }

    @GetMapping("/meetings")
    public List<Meeting> findMeetings(
            @RequestParam(required = false) String responsible,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String attendees
    ) {
        FilterParams.Builder builder = new FilterParams.Builder();
        try {
            int responsibleId = Integer.parseInt(responsible);
            builder.responsiblePersonId(responsibleId);
        } catch (Exception ignored) { }
        try {
            builder.description(desc);
        } catch (Exception ignored) { }

        try {
            LocalDateTime from = LocalDateTime.parse(fromDate);
            builder.fromDate(from);
        } catch (Exception ignored) { }
        try {
            LocalDateTime to = LocalDateTime.parse(toDate);
            builder.toDate(to);
        } catch (Exception ignored) { }

        try {
            int n = Integer.parseInt(attendees);
            builder.attendees(n);
        } catch (Exception ignored) { }

        try {
            MeetingCategory meetingCategory = MeetingCategory.valueOf(category);
            builder.category(meetingCategory);
        } catch (Exception ignored) { }

        try {
            MeetingType meetingType = MeetingType.valueOf(type);
            builder.type(meetingType);
        } catch (Exception ignored) { }

        return repository.findMeetings(builder.build());
    }
}
