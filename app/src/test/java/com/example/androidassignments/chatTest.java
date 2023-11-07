package com.example.androidassignments;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class chatTest {

    @Test
    public void testFormatMessage() {

        String inputMessage = "Helloo!!";
        String formattedMessage = chatTest.formatMessage(inputMessage);
        assertEquals("Helloo!!", formattedMessage);
    }
    public static String formatMessage(String message) {
        return message;
    }

}