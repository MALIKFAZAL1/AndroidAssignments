package com.example.androidassignments;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class chatTest {

    @Test
    public void testFormatMessage() {
        String inputMessage = "Heyy!!";
        String formattedMessage = chatTest.formatMessage(inputMessage);
        assertEquals("Heyy!!", formattedMessage);
    }
    public static String formatMessage(String message) {
        // Some logic to format the message (e.g., adding timestamps)
        return message;
    }

}