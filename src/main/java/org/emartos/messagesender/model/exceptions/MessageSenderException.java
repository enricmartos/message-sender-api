package org.emartos.messagesender.model.exceptions;

public abstract class MessageSenderException extends Exception {

    public MessageSenderException(String message) {
        super(message);
    }

    public MessageSenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
