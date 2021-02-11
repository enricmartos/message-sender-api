package org.emartos.messagesender.store;

import org.emartos.messagesender.model.MockProvider;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Repository
public class InMemoryProviderRepository implements ProviderRepository {

    private static final Set<MockProvider> providers = Collections.unmodifiableSet(new TreeSet<MockProvider>() {
        {
            add(MockProvider.create("P1", "0034", 1));
            add(MockProvider.create("P2", "0034", 2));
            add(MockProvider.create("P3", "0034", 1));
            add(MockProvider.create("P3", "0033", 3));
        }
    });

    @Override
    public Set<MockProvider> getByPrefix(String prefix) {
        return providers.stream()
                .filter(provider -> prefix.equals(provider.getPrefix()))
                .collect(Collectors.toSet());
    }

}
