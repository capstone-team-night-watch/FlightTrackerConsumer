package com.capstone.consumer.bindings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SuccessResponseTest {

    private SuccessResponse successResponse;

    @Test
    public void should_create_success_response() {
        successResponse = new SuccessResponse<>(1234, "Success");
        assertEquals("Success", successResponse.getMessage());
        assertEquals(1234, successResponse.getData());
    }

    @Test
    public void should_create_success_response2() {
        successResponse = new SuccessResponse<>("Success");
        assertEquals("Success", successResponse.getData());
    }
}