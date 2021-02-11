package org.emartos.messagesender.service;

import org.emartos.messagesender.model.Message;
import org.emartos.messagesender.model.MessageSentOperation;
import org.emartos.messagesender.model.MockProvider;
import org.emartos.messagesender.model.exceptions.ProviderNotFoundException;
import org.emartos.messagesender.store.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProviderServiceImpl implements ProviderService {
    private static final String PROVIDER_NOT_FOUND_EXCEPTION_MESSAGE = "Provider not found. Please introduce a mobile number with a valid prefix.";

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public MessageSentOperation sendMessage(Message message) throws ProviderNotFoundException {
        Optional<MockProvider> provider = getProvider(message);
        if (!provider.isPresent()) {
            throw new ProviderNotFoundException(PROVIDER_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        return provider.get().sendMessage(message);
    }

    private Optional<MockProvider> getProvider(Message message) {
        Set<MockProvider> providersWithSamePrefix = providerRepository.getByPrefix(message.extractMobileNumberPrefix());
        if (providersWithSamePrefix.isEmpty()) {
            return Optional.empty();
        }

        if (isASingleProvider(providersWithSamePrefix)) {
            return providersWithSamePrefix.stream().findFirst();
        }

        Set<MockProvider> providersWithSameMinCost = getProvidersWithSameMinCost(providersWithSamePrefix);
        if (isASingleProvider(providersWithSameMinCost)) {
            return providersWithSameMinCost.stream().findFirst();
        }

        return Optional.of(getRandomProvider(providersWithSameMinCost));
    }

    private boolean isASingleProvider(Set<MockProvider> candidateProviders) {
        return candidateProviders.size() == 1;
    }

    private Set<MockProvider> getProvidersWithSameMinCost(Set<MockProvider> providers) {
        Integer providersMinimumCost = getProvidersMinCost(providers);
        return providers
                .stream()
                .filter(provider -> providersMinimumCost.equals(provider.getCost()))
                .collect(Collectors.toSet());
    }

    private Integer getProvidersMinCost(Set<MockProvider> providers) {
        return providers
                .stream()
                .findFirst()
                .get()
                .getCost();
    }

//    Integer getProvidersMinCost(Set<MockProvider> mockProviders) {
//        return mockProviders
//                .stream()
//                .min(Comparator.comparing(MockProvider::getCost))
//                .map(MockProvider::getCost)
//                .orElse(null);
//    }

    MockProvider getRandomProvider(Set<MockProvider> mockProviders) {
        return mockProviders
                .stream()
                .skip(new Random().nextInt(mockProviders.size()))
                .findFirst()
                .orElse(null);
    }


}
