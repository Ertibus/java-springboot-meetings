package lt.visma.geeks.intern.meetings.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Meeting {
    private int id;
    private String name;
    private String description;
    private Attendee responsiblePerson;
    private MeetingCategory category;
    private MeetingType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Attendee> attendees;

    public Meeting(int id, String name, String description, Attendee responsiblePerson, MeetingCategory category, MeetingType type, LocalDateTime startDate, LocalDateTime endDate, List<Attendee> attendees) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.responsiblePerson = responsiblePerson;
        this.category = category;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attendees = attendees;
    }

    public Meeting() {
        this(-1, "Meetas", "Jono Java Meetas", new Attendee(), MeetingCategory.CODE_MONKEY, MeetingType.LIVE, LocalDateTime.now(), LocalDateTime.now().plusDays(1), new ArrayList<>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Attendee getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Attendee responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public MeetingCategory getCategory() {
        return category;
    }

    public void setCategory(MeetingCategory category) {
        this.category = category;
    }

    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }
}