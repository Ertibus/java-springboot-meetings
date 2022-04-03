package lt.visma.geeks.intern.meetings.model;

import java.time.LocalDateTime;

public class Attendee {
    private final int id;
    private final String name;
    private final LocalDateTime time;

    public Attendee(int id, String name, LocalDateTime time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public Attendee() {
        this(-1, "Jonas", LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
