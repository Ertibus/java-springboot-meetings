package lt.visma.geeks.intern.meetings.model;

import java.time.LocalDateTime;

public class Attendee {
    private int id;
    private String name;
    private LocalDateTime time;

    public Attendee(int id, String name, LocalDateTime time) {
        this.id = 0;
        this.name = name;
        this.time = time;
    }

    public Attendee() {
        this(-1, "Jonas", LocalDateTime.now());
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
