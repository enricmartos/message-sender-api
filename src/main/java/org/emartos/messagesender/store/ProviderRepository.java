package org.emartos.messagesender.store;

import org.emartos.messagesender.model.MockProvider;

import java.util.Set;

public interface ProviderRepository {

    Set<MockProvider> getByPrefix(String prefix);

}
