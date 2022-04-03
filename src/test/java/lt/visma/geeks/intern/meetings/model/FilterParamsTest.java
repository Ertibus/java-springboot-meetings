package lt.visma.geeks.intern.meetings.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FilterParams testing.
 *
 * @author Emilis Margevicius
 * @version 0.1.0
 * @since 0.1.0
 * @see FilterParams
 */
class FilterParamsTest {
    @Test
    void testBuilder() {
        FilterParams filterParams = new FilterParams.Builder().description("java").build();
        assertTrue(filterParams.getDescription().isPresent());
        assertTrue(filterParams.getAttendees().isEmpty());
    }
}