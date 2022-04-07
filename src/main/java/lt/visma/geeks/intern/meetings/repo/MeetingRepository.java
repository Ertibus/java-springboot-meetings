package lt.visma.geeks.intern.meetings.repo;

import lt.visma.geeks.intern.meetings.model.*;
import lt.visma.geeks.intern.meetings.transformer.JsonTransformer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Meeting repository manager.
 *
 * @author Emilis Margevicius
 * @version 0.1.0
 * @since 0.1.0
 * @see Meeting
 * @see JsonTransformer
 */
@Service
public class MeetingRepository {
    private final static String DEFAULT_JSON = "database.json";
    private static List<Meeting> meetingList;
    private final String jsonFile;

    /**
     * Initialize default repo
     */
    public MeetingRepository() {
        this(DEFAULT_JSON);
    }

    /**
     * Construct a new repository with a custom JSON file
     * @param jsonFile
     */
    public MeetingRepository(String jsonFile) {
        this.jsonFile = jsonFile;
        meetingList = new ArrayList<>();
        loadData();
    }

    /**
     * Write JSON data
     */
    private void saveData() {
        try(FileWriter file = new FileWriter(jsonFile)) {
            String json = JsonTransformer.toJsonString(meetingList);
            file.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load JSON data
     */
    private void loadData() {
        File f = new File(jsonFile);
        if(!f.exists() || f.isDirectory() || f.length() == 0) { return; }
        try (FileReader file = new FileReader(jsonFile)) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(file);
            meetingList = JsonTransformer.fromJsonArray(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Find a meeting by its ID
     * @param id of the meeting
     * @return Meeting
     * @see Meeting
     */
    public Meeting findMeeting(int id) {
        Optional<Meeting> meetingOption = meetingList.stream().filter(meet -> meet.getId() == id).findFirst();
        if (meetingOption.isEmpty()) {
            return null;
        }
        return meetingOption.get();
    }

    /**
     * Find all meetings
     * @return a list of meetings
     * @see Meeting
     */
    public List<Meeting> findMeetings() {
        return meetingList;
    }

    /**
     * Find meetings and filter them by the given params.
     * @param filter - what to filter by
     * @return a list of meetings
     * @see Meeting
     * @see FilterParams
     */
    public List<Meeting> findMeetings(FilterParams filter) {
        Stream<Meeting> meetingStream = meetingList.stream();

        if (filter.getResponsiblePersonId().isPresent()) {
            int value = filter.getResponsiblePersonId().get();
            System.out.println(value);
            meetingStream = meetingStream.filter(meeting -> meeting.getResponsiblePersonId() == value);
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
        saveData();
    }

    public void removeMeeting(int id, int responsibleId) {
        Meeting meeting = findMeeting(id);
        if (meeting == null || meeting.getResponsiblePersonId() != responsibleId) { return; }
        meetingList.remove(meeting);
        saveData();
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
        saveData();
        return null;
    }

    public void removeAttendee(int meetingId, int attendeeId) {
        Meeting meeting = findMeeting(meetingId);
        if (meeting == null) { return; }
        int index = meetingList.indexOf(meeting);

        // Is owner
        if (meeting.getResponsiblePersonId() == attendeeId) {
            return;
        }
        Stream<Attendee> attendeeStream = meeting.getAttendees().stream();
        // Good to remove
        List<Attendee> newList = meeting.getAttendees().stream().filter(att -> att.getId() != attendeeId).collect(Collectors.toList());
        meeting.setAttendees(newList);
        meetingList.set(index, meeting);
        saveData();
    }
}
