package lt.visma.geeks.intern.meetings.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.visma.geeks.intern.meetings.model.Attendee;
import lt.visma.geeks.intern.meetings.model.Meeting;
import lt.visma.geeks.intern.meetings.model.MeetingCategory;
import lt.visma.geeks.intern.meetings.model.MeetingType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonTransformer {
    public static List<Meeting> fromJsonArray(JSONArray jsonArray) {
        List<Meeting> meetingList = new ArrayList<Meeting>();

        for (JSONObject obj : (Iterable<JSONObject>) jsonArray) {
            List<Attendee> attendees = new ArrayList<>();
            for(JSONObject object: (Iterable<JSONObject>) (JSONArray) obj.get("attendees")) {
                attendees.add(
                        new Attendee(
                                ((Number) object.get("id")).intValue(),
                                (String) object.get("name"),
                                LocalDateTime.parse((String) object.get("time"))
                        )
                );
            }
            Meeting meeting = new Meeting(
                    ((Number) obj.get("id")).intValue(),
                    (String) obj.get("name"),
                    (String) obj.get("description"),
                    ((Number) obj.get("responsiblePersonId")).intValue(),
                    MeetingCategory.valueOf((String) obj.get("category")),
                    MeetingType.valueOf((String) obj.get("type")),
                    LocalDateTime.parse((String) obj.get("startDate")),
                    LocalDateTime.parse((String) obj.get("endDate")),
                    attendees
            );
            meetingList.add(meeting);
        }
        return meetingList;
    }
    public static String toJsonString(List<Meeting> meetingList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = "";
        json = mapper.writeValueAsString(meetingList);
        return json;
    }
}
