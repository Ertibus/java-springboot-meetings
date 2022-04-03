package lt.visma.geeks.intern.meetings.repo;

import lt.visma.geeks.intern.meetings.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeetingRepository {
    private final static String DEFAULT_JSON = "database.json";
    private static List<Meeting> meetingList;
    private final String jsonFile;


    public MeetingRepository() {
        this(DEFAULT_JSON);
    }

    public MeetingRepository(String jsonFile) {
        this.jsonFile = jsonFile;
        meetingList = new ArrayList<>();
    }

    public Meeting findMeeting(int id) {
        Optional<Meeting> meetingOption = meetingList.stream().filter(meet -> meet.getId() == id).findFirst();
        if (meetingOption.isEmpty()) {
            return null;
        }
        return meetingOption.get();
    }
    public List<Meeting> findMeetings() {
        return meetingList;
    }

    public List<Meeting> findMeetings(FilterParams filter) {
        Stream<Meeting> meetingStream = meetingList.stream();

        if (filter.getResponsiblePersonId().isPresent()) {
            int value = filter.getResponsiblePersonId().get();
            System.out.println(value);
            meetingStream = meetingStream.filter(meeting -> meeting.getResponsiblePerson().getId() == value);
        }
        if (filter.getDescription().isPresent()) {
            String description = filter.getDescription().get();
            meetingStream = meetingStream.filter(meeting ->
                    Pattern.compile(description, Pattern.CASE_INSENSITIVE)
                            .matcher(meeting.getDescription()).find());
        }
        if (filter.getCategory().isPresent()) {
            MeetingCategory category = filter.getCategory().get();
            meetingStream = meetingStream.filter(meeting -> meeting.getCategory().equals(category));
        }
        if (filter.getType().isPresent()) {
            MeetingType type = filter.getType().get();
            meetingStream = meetingStream.filter(meeting -> meeting.getType().equals(type));
        }
        if (filter.getFromDate().isPresent()) {
            LocalDateTime fromDate = filter.getFromDate().get();
            meetingStream = meetingStream.filter(meeting -> meeting.getStartDate().compareTo(fromDate) >= 0);
        }
        if (filter.getToDate().isPresent()) {
            LocalDateTime toDate = filter.getToDate().get();
            meetingStream = meetingStream.filter(meeting -> meeting.getStartDate().compareTo(toDate) < 0);
        }
        if (filter.getAttendees().isPresent()) {
            int attendees = filter.getAttendees().get();
            meetingStream = meetingStream.filter(meeting -> meeting.getAttendees().size() >= attendees);
        }
        return meetingStream.collect(Collectors.toList());
    }

    public void addMeeting(Meeting newMeeting) {
        meetingList.add(newMeeting);
    }

    public void removeMeeting(int id, int responsibleId) {
        Meeting meeting = findMeeting(id);
        if (meeting == null || meeting.getResponsiblePerson().getId() != responsibleId) { return; }
        meetingList.remove(meeting);
    }

    public String addAttendee(int meetingId, Attendee attendee) {
        Meeting meeting = findMeeting(meetingId);
        if (meeting == null) { return "Meeting not found :("; }
        int index = meetingList.indexOf(meeting);
        Stream<Attendee> attendeeStream = meeting.getAttendees().stream();
        // Meeting already has this attendee
        if (attendeeStream.anyMatch(a -> a.getId() == attendee.getId())) {
            return "This person is already in the meeting!";
        }
        // Meeting intersects
        if (findMeetings().stream().anyMatch(meet ->
                (meet.getId() != meetingId)
                        && (meet.getAttendees().stream().anyMatch(a -> a.getId() == attendee.getId()))
                        && (meet.getStartDate().compareTo(attendee.getTime()) <= 0)
                        && (meet.getEndDate().compareTo(attendee.getTime()) >= 0)
        )) {
            return "This person is in another meeting that intersects with this one!";
        }
        // Good to add
        List<Attendee> newList = meeting.getAttendees();
        newList.add(attendee);
        meeting.setAttendees(newList);
        meetingList.set(index, meeting);
        return null;
    }

    public void removeAttendee(int meetingId, int attendeeId) {
        Meeting meeting = findMeeting(meetingId);
        if (meeting == null) { return; }
        int index = meetingList.indexOf(meeting);

        // Is owner
        if (meeting.getResponsiblePerson().getId() == attendeeId) {
            return;
        }
        Stream<Attendee> attendeeStream = meeting.getAttendees().stream();
        // Good to remove
        List<Attendee> newList = meeting.getAttendees().stream().filter(att -> att.getId() != attendeeId).collect(Collectors.toList());
        meeting.setAttendees(newList);
        meetingList.set(index, meeting);
    }
}
