package org.emartos.messagesender.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MockProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockProvider.class.getName());

    private final String name;
    private final String prefix;
    private final Integer cost;

    private MockProvider(String name, String prefix, Integer cost) {
        this.name = name;
        this.prefix = prefix;
        this.cost = cost;
    }

    public static MockProvider create(String name, String prefix, Integer cost) {
        return new MockProvider(name, prefix, cost);
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public Integer getCost() {
        return cost;
    }

    public MessageSentOperation sendMessage(Message message) {
        LOGGER.info("Message with text {} has been sent to mobile phone number {}", message.getTextToSend(), message.getToMobileNumber());
        MessageSentOperation messageSentOperation = new MessageSentOperation(this.getName());
        LOGGER.info("Operation id: {}, provider used: {}", messageSentOperation.getId(), messageSentOperation.getProviderName());

        return messageSentOperation;
    }


}
