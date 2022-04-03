package lt.visma.geeks.intern.meetings.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lt.visma.geeks.intern.meetings.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonTransformer testing.
 *
 * @author Emilis Margevicius
 * @version 0.1.0
 * @since 0.1.0
 * @see Attendee
 * @see Meeting
 * @see JsonTransformer
 */
class JsonTransformerTest {

    @Test
    void fromJsonArray() {
        int id = 0;
        int responsiblePerson =  0;
        String name = "Test";
        String description = "Performing a test";
        MeetingCategory category = MeetingCategory.CODE_MONKEY;
        MeetingType type = MeetingType.LIVE;
        String startDate = "2022-04-02T15:36:20.00397763";
        String endDate = "2022-04-03T15:36:20.00397763";
        List<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee(id, name, LocalDateTime.parse(startDate)));
        attendees.add(new Attendee(id, name, LocalDateTime.parse(startDate)));

        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArrayA = new JSONArray();

        JSONObject attendee1 = new JSONObject();
        attendee1.put("id", id);
        attendee1.put("name", name);
        attendee1.put("time", startDate);
        jsonArrayA.add(attendee1);
        JSONObject attendee2 = new JSONObject();
        attendee2.put("id", id);
        attendee2.put("name", name);
        attendee2.put("time", startDate);
        jsonArrayA.add(attendee2);

        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("name", name);
        obj.put("description", description);
        obj.put("responsiblePersonId", responsiblePerson);
        obj.put("category", category.toString());
        obj.put("type", type.toString());
        obj.put("startDate", startDate);
        obj.put("endDate", endDate);
        obj.put("attendees", jsonArrayA);
        jsonArray.add(obj);
        List<Meeting> meetingList = JsonTransformer.fromJsonArray(jsonArray);

        Meeting expectedMeeting = new Meeting(
                id,
                name,
                description,
                responsiblePerson,
                category,
                type,
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate),
                attendees
        );
        assertEquals(meetingList.get(0).toString(), expectedMeeting.toString());
    }

    @Test
    void toJsonString() {
        int id = 0;
        String name = "Test";
        String description = "Performing a test";
        int responsiblePerson =  0;
        MeetingCategory category = MeetingCategory.CODE_MONKEY;
        MeetingType type = MeetingType.LIVE;
        String startDate = "2022-04-02T15:36:20.00397763";
        String endDate = "2022-04-03T15:36:20.00397763";
        List<Attendee> attendees = new ArrayList<>();

        String expectedValue = "[{\"id\":0,\"name\":\"Test\",\"description\":\"Performing a test\",\"responsiblePersonId\":0,\"category\":\"CODE_MONKEY\",\"type\":\"LIVE\",\"startDate\":\"2022-04-02T15:36:20\",\"endDate\":\"2022-04-03T15:36:20\",\"attendees\":[]},{\"id\":0,\"name\":\"Test\",\"description\":\"Performing a test\",\"responsiblePersonId\":0,\"category\":\"CODE_MONKEY\",\"type\":\"LIVE\",\"startDate\":\"2022-04-02T15:36:20\",\"endDate\":\"2022-04-03T15:36:20\",\"attendees\":[]}]";
       Meeting meeting = new Meeting(
                id,
                name,
                description,
                responsiblePerson,
                category,
                type,
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate),
                attendees
        );
        List<Meeting> meetingList = new ArrayList<Meeting>();
        meetingList.add(meeting);
        meetingList.add(meeting);
        String json = "";
        try {
            json = JsonTransformer.toJsonString(meetingList);
        } catch (JsonProcessingException e) {
            fail("Failed to parse to JSON");
            e.printStackTrace();
        } finally {
            assertEquals(expectedValue, json);
        }
    }
}