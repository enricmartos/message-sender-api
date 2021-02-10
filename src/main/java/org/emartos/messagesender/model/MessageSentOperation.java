package org.emartos.messagesender.model;

import java.util.UUID;

public class MessageSentOperation {

    private final String id;
    private final String providerName;

    public MessageSentOperation(String providerName) {
        this.id = UUID.randomUUID().toString();
        this.providerName = providerName;
    }

    public String getId() {
        return id;
    }

    public String getProviderName() {
        return providerName;
    }
}
