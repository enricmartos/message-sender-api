package org.emartos.messagesender.model;

public class Message {
    private static final int PREFIX_LENGTH = 4;

    private final String toMobileNumber;
    private final String textToSend;

    public Message(String toMobileNumber, String textToSend) {
        this.toMobileNumber = toMobileNumber;
        this.textToSend = textToSend;
    }

    public String getToMobileNumber() {
        return toMobileNumber;
    }

    public String getTextToSend() {
        return textToSend;
    }

    public String extractMobileNumberPrefix() {
        return this.getToMobileNumber().substring(0, PREFIX_LENGTH);
    }
}
