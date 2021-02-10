package org.emartos.messagesender.model.exceptions;

public class InvalidMessageException extends MessageSenderException {

    public InvalidMessageException(String message) {
        super(message);
    }

    public InvalidMessageException(String message, Throwable t) {
        super(message, t);
    }
}
