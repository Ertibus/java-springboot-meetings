package lt.visma.geeks.intern.meetings.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Attendee {
    private final int id;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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

    /**
     * Override for toString() method from {@link java.lang.Object}
     *
     * @return a string representation of the object.
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return String.format("\t\tID:\t\t%d\n" +
                        "\t\tName:\t\t%s\n" +
                        "\t\tdate: \t%s\n"
                , id, name, time);
    }
}
