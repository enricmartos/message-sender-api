package org.emartos.messagesender;

import org.emartos.messagesender.model.MockProvider;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MockProviderMother {

    public static Set<MockProvider> providersFor0033Prefix() {
        return Collections.unmodifiableSet(new TreeSet<MockProvider>() {
            {
                add(MockProvider.create("P3", "0033", 3));
                add(MockProvider.create("P4", "0033", 2));
            }
        });
    }

    public static Set<MockProvider> providersFor0034Prefix() {
        return Collections.unmodifiableSet(new TreeSet<MockProvider>() {
            {
                add(MockProvider.create("P1", "0034", 1));
                add(MockProvider.create("P2", "0034", 2));
                add(MockProvider.create("P3", "0034", 1));
            }
        });
    }

    public static Set<MockProvider> providersFor0035Prefix() {
        return Collections.emptySet();
    }

}
