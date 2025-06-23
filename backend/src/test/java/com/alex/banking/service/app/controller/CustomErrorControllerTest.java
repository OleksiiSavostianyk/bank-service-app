package com.alex.banking.service.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

class CustomErrorControllerTest {

    private final CustomErrorController controller = new CustomErrorController();

    @Test
    void shouldReturnFormattedErrorMessage() {
        Exception e = new Exception("Test error");
        String result = controller.handleException(e);
        assertEquals("Error:  Test error", result);
    }

    @Test
    void shouldHandleNullMessage() {
        Exception e = new Exception((String) null);
        String result = controller.handleException(e);
        assertEquals("Error:  null", result);
    }
}