package org.emartos.messagesender.service;

import org.emartos.messagesender.model.Message;
import org.emartos.messagesender.model.MessageSentOperation;
import org.emartos.messagesender.model.exceptions.ProviderNotFoundException;

public interface ProviderService {

    MessageSentOperation sendMessage(Message message) throws ProviderNotFoundException;




}
