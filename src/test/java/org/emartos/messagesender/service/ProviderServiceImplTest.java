package org.emartos.messagesender.service;

import org.emartos.messagesender.MessageMother;
import org.emartos.messagesender.MockProviderMother;
import org.emartos.messagesender.model.Message;
import org.emartos.messagesender.model.MessageSentOperation;
import org.emartos.messagesender.model.exceptions.ProviderNotFoundException;
import org.emartos.messagesender.store.ProviderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProviderServiceImplTest {
    @InjectMocks
    private ProviderServiceImpl providerService;

    @Mock
    private ProviderRepository providerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // HAPPY PATHS
    @Test
    public void whenThereIsASingleProviderWithTheMobilePrefix_thenThisProviderIsReturned()
            throws ProviderNotFoundException {
        Message message = MessageMother.messageWithToMobileNumberPrefix0033();
        when(providerRepository.getByPrefix("0033")).thenReturn(MockProviderMother.providersFor0033Prefix());

        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        Assert.assertNotNull(messageSentOperation.getId());
        Assert.assertEquals("P3", messageSentOperation.getProviderName());
    }

    @Test
    public void whenThereAreManyProvidersWithTheSameMobilePrefix_thenTheProviderWithMinCostIsReturned()
            throws ProviderNotFoundException {
        Message message = MessageMother.messageWithToMobileNumberPrefix0032();
        when(providerRepository.getByPrefix("0032")).thenReturn(MockProviderMother.providersFor0032Prefix());

        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        Assert.assertNotNull(messageSentOperation.getId());
        Assert.assertEquals("P5", messageSentOperation.getProviderName());
    }

    @Test
    public void whenThereAreManyProvidersWithTheSameMobilePrefixAndWithSameMinCost_thenOneOfThemIsReturnedRandomly()
            throws ProviderNotFoundException {
        Message message = MessageMother.messageWithToMobileNumberPrefix0034();
        when(providerRepository.getByPrefix("0034")).thenReturn(MockProviderMother.providersFor0034Prefix());

        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        Assert.assertNotNull(messageSentOperation.getId());
        assertThat(messageSentOperation.getProviderName(), anyOf(is("P1"), is("P3")));
    }

    // CORNER CASES
    @Test
    public void whenMobileNumberPrefixDoesNotExist_thenNotFoundStatusIsReturned() {
        Message message = MessageMother.messageWithToMobileNumberPrefix0035();
        when(providerRepository.getByPrefix("0035")).thenReturn(MockProviderMother.providersFor0035Prefix());

        assertThrows(ProviderNotFoundException.class,
            ()-> providerService.sendMessage(message));
    }

}
