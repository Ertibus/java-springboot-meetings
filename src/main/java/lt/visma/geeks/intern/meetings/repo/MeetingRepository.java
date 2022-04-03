package lt.visma.geeks.intern.meetings.repo;

import lt.visma.geeks.intern.meetings.model.Attendee;
import lt.visma.geeks.intern.meetings.model.Meeting;

import java.util.ArrayList;
import java.util.List;

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
        return meetingList.get(0);
    }

    public List<Meeting> findMeetings() {
        return meetingList;
    }

    public void addMeeting(Meeting newMeeting) {
        meetingList.add(newMeeting);
    }

    public void removeMeeting(int id, int responsibleId) {

    }

    public String addAttendee(int meetingId, Attendee attendee) {
        return null;
    }

    public void removeAttendee(int meetingId, int attendeeId) {

    }
}
