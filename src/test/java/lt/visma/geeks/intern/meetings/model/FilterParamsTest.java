package lt.visma.geeks.intern.meetings.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterParamsTest {
    @Test
    void testBuilder() {
        FilterParams filterParams = new FilterParams.Builder().description("java").build();
        assertTrue(filterParams.getDescription().isPresent());
        assertTrue(filterParams.getAttendees().isEmpty());
    }
}