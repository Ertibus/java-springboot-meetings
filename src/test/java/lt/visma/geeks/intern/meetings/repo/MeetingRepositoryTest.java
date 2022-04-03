package lt.visma.geeks.intern.meetings.repo;

import lt.visma.geeks.intern.meetings.model.Attendee;
import lt.visma.geeks.intern.meetings.model.Meeting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MeetingRepositoryTest {
    private final static String TESTING_JSON = "test_database.json";
    private MeetingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MeetingRepository(TESTING_JSON);
    }

    @AfterEach
    void tearDown() {
        new File(TESTING_JSON).delete();
    }

    @Test
    void addMeeting() {
        assertEquals(0, repository.findMeetings().size());
        repository.addMeeting(new Meeting());
        assertEquals(1, repository.findMeetings().size());
    }

    @Test
    void removeMeeting() {
        Meeting meeting = new Meeting();
        repository.addMeeting(meeting);
        assertEquals(1, repository.findMeetings().size());
        repository.removeMeeting(meeting.getId(), meeting.getResponsiblePerson().getId() + 1);
        assertEquals(1, repository.findMeetings().size());
        repository.removeMeeting(meeting.getId(), meeting.getResponsiblePerson().getId());
        assertEquals(0, repository.findMeetings().size());
    }

    @Test
    void addAttendee() {
        Meeting meeting1 = new Meeting();
        Meeting meeting2 = new Meeting();
        LocalDateTime time = meeting1.getStartDate();
        meeting2.setId(meeting1.getId() + 1);
        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);
        //
        Attendee attendee = new Attendee(0, "Jonas", time.plusMinutes(10));
        assertEquals(0, repository.findMeeting(meeting1.getId()).getAttendees().size());
        repository.addAttendee(meeting1.getId(), attendee);
        assertEquals(1, repository.findMeeting(meeting1.getId()).getAttendees().size());
        assertEquals("This person is already in the meeting!", repository.addAttendee(meeting1.getId(), attendee));
        assertEquals("This person is in another meeting that intersects with this one!", repository.addAttendee(meeting2.getId(), attendee));
        //
    }

    @Test
    void removeAttendee() {
        Meeting meeting = new Meeting();
        LocalDateTime time = meeting.getStartDate();
        Attendee attendee1 = new Attendee();
        Attendee attendee2 = new Attendee(attendee1.getId() + 2, "Jonas", time);
        meeting.setResponsiblePerson(attendee1);

        repository.addMeeting(meeting);
        repository.addAttendee(meeting.getId(), attendee1);
        repository.addAttendee(meeting.getId(), attendee2);
        assertEquals(2, repository.findMeeting(meeting.getId()).getAttendees().size());
        repository.removeAttendee(meeting.getId(), attendee1.getId());
        assertEquals(2, repository.findMeeting(meeting.getId()).getAttendees().size());
        repository.removeAttendee(meeting.getId(), attendee2.getId());
        assertEquals(1, repository.findMeeting(meeting.getId()).getAttendees().size());
    }
}