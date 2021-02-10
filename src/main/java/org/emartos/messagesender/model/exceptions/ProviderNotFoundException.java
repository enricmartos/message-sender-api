package org.emartos.messagesender.model.exceptions;

public class ProviderNotFoundException extends MessageSenderException {

    public ProviderNotFoundException(String message) {
        super(message);
    }

    public ProviderNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}
