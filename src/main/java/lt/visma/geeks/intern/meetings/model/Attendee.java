package lt.visma.geeks.intern.meetings.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Represents an Attendee. Used by {@link Meeting}.
 *
 * It has 3 values: id, name and time (assigned to this attendee)
 *
 * @author Emilis Margevicius
 * @version 0.1.0
 * @since 0.1.0
 * @see Meeting
 */
public class Attendee {
    private final int id;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private final LocalDateTime time;

    /**
     * Constructor with parameters
     * @param id - ID of the attendee
     * @param name - Name of the attendee
     * @param time - Assigned time
     */
    public Attendee(int id, String name, LocalDateTime time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    /**
     * Empty overload constructor. Primarily used for testing.
     */
    public Attendee() {
        this(-1, "Jonas", LocalDateTime.now());
    }

    /**
     * Getter for the ID
     * @return attendees id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the name of the Attendee
     * @return name of the attendee
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the assigned time
     * @return assigned time
     * @see LocalDateTime
     */
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
