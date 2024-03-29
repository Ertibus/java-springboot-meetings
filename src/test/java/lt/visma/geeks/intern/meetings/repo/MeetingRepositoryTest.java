package lt.visma.geeks.intern.meetings.repo;

import lt.visma.geeks.intern.meetings.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Main MeetingRepository testing.
 *
 * @author Emilis Margevicius
 * @version 0.1.0
 * @since 0.1.0
 * @see MeetingRepository
 * @see FilterParams
 * @see Meeting
 */
class MeetingRepositoryTest {
    private static final String TESTING_JSON = "test_database.json";
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
        repository.removeMeeting(meeting.getId(), meeting.getResponsiblePersonId() + 1);
        assertEquals(1, repository.findMeetings().size());
        repository.removeMeeting(meeting.getId(), meeting.getResponsiblePersonId());
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
        meeting.setResponsiblePersonId(attendee1.getId());

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

        FilterParams filter = FilterParams.builder().description(description).build();
        int found = repository.findMeetings().size();
        assertEquals(2, found);
        found = repository.findMeetings(filter).size();
        assertEquals(1, found);
    }

    @Test
    void findMeetingsByResponsiblePerson() {
        int attendeeId1 = 0;
        int attendeeId2 = 1;

        Meeting meeting1 = new Meeting();
        meeting1.setResponsiblePersonId(attendeeId1);

        Meeting meeting2 = new Meeting();
        meeting2.setResponsiblePersonId(attendeeId2);
        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);

        FilterParams filter = FilterParams.builder().responsiblePersonId(attendeeId1).build();

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

        FilterParams filter = FilterParams.builder().category(category1).build();
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
        meeting2.setType(type2);

        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);

        FilterParams filter = FilterParams.builder().type(type1).build();
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
        filter = FilterParams.builder().fromDate(fromDate).build();
        found = repository.findMeetings(filter).size();
        assertEquals(2, found);
        // Just after
        filter = FilterParams.builder().toDate(toDate).build();
        found = repository.findMeetings(filter).size();

        assertEquals(2, found);
        // In between
        filter = FilterParams.builder().fromDate(fromDate).toDate(toDate).build();
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

        FilterParams filter = FilterParams.builder().attendees(2).build();

        int found = repository.findMeetings(filter).size();

        assertEquals(1, found);
    }
}