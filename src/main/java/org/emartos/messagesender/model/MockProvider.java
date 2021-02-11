package org.emartos.messagesender.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class MockProvider implements Comparable<MockProvider> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockProvider that = (MockProvider) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(prefix, that.prefix) &&
                Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prefix, cost);
    }

    @Override
    public int compareTo(MockProvider mockProvider) {
        return  this.getCost() > mockProvider.getCost() ? 1 : -1;
    }

    public MessageSentOperation sendMessage(Message message) {
        LOGGER.info("Message with text {} has been sent to mobile phone number {}", message.getTextToSend(), message.getToMobileNumber());
        MessageSentOperation messageSentOperation = new MessageSentOperation(this.getName());
        LOGGER.info("Operation id: {}, provider used: {}", messageSentOperation.getId(), messageSentOperation.getProviderName());

        return messageSentOperation;
    }


}
