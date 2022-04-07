package lt.visma.geeks.intern.meetings.model;

import org.junit.jupiter.api.Test;

import java.util.Optional;

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
        FilterParams filterParams = FilterParams.builder().description("java").build();
        assertTrue(filterParams.getDescription() != null);
        assertTrue(filterParams.getAttendees() == null);
    }
}