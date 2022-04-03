package lt.visma.geeks.intern.meetings.repo;

import lt.visma.geeks.intern.meetings.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    //*********** FIND FILTER TESTS ********
    @Test
    void findMeetingsAll() {
        assertEquals(0, repository.findMeetings().size());
    }

    @Test
    void findMeetingsByDescription() {
        String description = "java";

        Meeting meeting1 = new Meeting();
        meeting1.setDescription("Quick brown fox");
        Meeting meeting2 = new Meeting();
        meeting2.setDescription("Jono Java Meetas");

        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);

        FilterParams filter = new FilterParams.Builder().description(description).build();
        int found = repository.findMeetings().size();
        assertEquals(2, found);
        found = repository.findMeetings(filter).size();
        assertEquals(1, found);
    }

    @Test
    void findMeetingsByResponsiblePerson() {
        Attendee attendee1 = new Attendee(0, "Jonas", LocalDateTime.now());
        Attendee attendee2 = new Attendee(1, "Tomas", LocalDateTime.now());

        Meeting meeting1 = new Meeting();
        meeting1.setResponsiblePerson(attendee1);

        Meeting meeting2 = new Meeting();
        meeting2.setResponsiblePerson(attendee2);
        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);

        FilterParams filter = new FilterParams.Builder().responsiblePersonId(attendee1.getId()).build();

        int found = repository.findMeetings().size();
        assertEquals(2, found);
        found = repository.findMeetings(filter).size();
        assertEquals(1, found);
    }
    @Test
    void findMeetingsByCategory() {
        MeetingCategory category1 = MeetingCategory.SHORT;
        MeetingCategory category2 = MeetingCategory.HUB;

        Meeting meeting1 = new Meeting();
        meeting1.setCategory(category1);

        Meeting meeting2 = new Meeting();
        meeting2.setCategory(category2);

        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);

        FilterParams filter = new FilterParams.Builder().category(category1).build();
        int found = repository.findMeetings().size();
        assertEquals(2, found);
        found = repository.findMeetings(filter).size();
        assertEquals(1, found);
    }

    @Test
    void findMeetingsByType() {
        MeetingType type1 = MeetingType.LIVE;
        MeetingType type2 = MeetingType.IN_PERSON;

        Meeting meeting1 = new Meeting();
        meeting1.setType(type1);

        Meeting meeting2 = new Meeting();
        meeting1.setType(type2);

        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);

        FilterParams filter = new FilterParams.Builder().type(type1).build();
        int found = repository.findMeetings().size();
        assertEquals(2, found);
        found = repository.findMeetings(filter).size();
        assertEquals(1, found);
    }

    @Test
    void findMeetingsByDates() {
        LocalDateTime fromDate = LocalDateTime.now().plusDays(1);
        LocalDateTime toDate = fromDate.plusDays(2);

        // Before fromDate
        Meeting meeting1 = new Meeting();
        meeting1.setStartDate(fromDate.minusDays(1));
        meeting1.setEndDate(fromDate.minusDays(1).plusHours(1));
        repository.addMeeting(meeting1);

        // Between fromDate and toDate
        Meeting meeting2 = new Meeting();
        meeting2.setStartDate(fromDate.plusHours(1));
        meeting2.setEndDate(toDate.minusHours(1));
        repository.addMeeting(meeting2);
        // after toDate
        Meeting meeting3 = new Meeting();
        meeting3.setStartDate(toDate.plusHours(1));
        meeting3.setEndDate(toDate.plusHours(2));
        repository.addMeeting(meeting3);

        int expected;
        int found;
        FilterParams filter;
        // Just before
        filter = new FilterParams.Builder().fromDate(fromDate).build();
        found = repository.findMeetings(filter).size();
        assertEquals(2, found);
        // Just after
        filter = new FilterParams.Builder().toDate(toDate).build();
        found = repository.findMeetings(filter).size();

        assertEquals(2, found);
        // In between
        filter = new FilterParams.Builder().fromDate(fromDate).toDate(toDate).build();
        found = repository.findMeetings(filter).size();
        assertEquals(1, found);
    }
    @Test
    void findMeetingsByAttendeeCount() {
        Meeting meeting1 = new Meeting();
        Meeting meeting2 = new Meeting();
        List<Attendee> attendeeList = new ArrayList<>();
        attendeeList.add(new Attendee());
        attendeeList.add(new Attendee());
        meeting2.setAttendees(attendeeList);
        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);

        FilterParams filter = new FilterParams.Builder().attendees(2).build();

        int found = repository.findMeetings(filter).size();

        assertEquals(1, found);
    }
}