package org.emartos.messagesender.service;

import org.emartos.messagesender.MessageMother;
import org.emartos.messagesender.MockProviderMother;
import org.emartos.messagesender.model.Message;
import org.emartos.messagesender.model.MessageSentOperation;
import org.emartos.messagesender.model.exceptions.ProviderNotFoundException;
import org.emartos.messagesender.store.ProviderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class ProviderServiceImplTest {
    @InjectMocks
    ProviderServiceImpl providerService;
    @Mock
    ProviderRepository providerRepository;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // HAPPY PATHS
    @Test
    public void whenThereIsASingleProviderWithTheMobilePrefix_thenThisProviderIsReturned()
            throws ProviderNotFoundException {
        // GIVEN
        Message message = MessageMother.messageWithToMobileNumberPrefix0033();
        when(providerRepository.getByPrefix("0033")).thenReturn(MockProviderMother.providersFor0033Prefix());

        // WHEN
        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        // THEN
        Assert.assertNotNull(messageSentOperation.getId());
        Assert.assertEquals("P3", messageSentOperation.getProviderName());
    }

    @Test
    public void whenThereAreManyProvidersWithTheSameMobilePrefix_thenTheProviderWithMinCostIsReturned()
            throws ProviderNotFoundException {
        // GIVEN
        Message message = MessageMother.messageWithToMobileNumberPrefix0033();
        when(providerRepository.getByPrefix("0033")).thenReturn(MockProviderMother.providersFor0033Prefix());

        // WHEN
        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        // THEN
        Assert.assertNotNull(messageSentOperation.getId());
        Assert.assertEquals("P4", messageSentOperation.getProviderName());
    }

    @Test
    public void whenThereAreManyProvidersWithTheSameMobilePrefixWithSameMinCost_thenOneOfThemIsReturnedRandomly()
            throws ProviderNotFoundException {
        // GIVEN
        Message message = MessageMother.messageWithToMobileNumberPrefix0034();
        when(providerRepository.getByPrefix("0034")).thenReturn(MockProviderMother.providersFor0034Prefix());

        // WHEN
        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        // THEN
        Assert.assertNotNull(messageSentOperation.getId());
        assertThat(messageSentOperation.getProviderName(), anyOf(is("P1"), is("P3")));
    }

    // CORNER CASES
    @Test
    public void whenMobileNumberPrefixDoesNotExist_thenNotFoundStatusIsReturned() throws ProviderNotFoundException {
        // GIVEN
        Message message = MessageMother.messageWithToMobileNumberPrefix0035();
        when(providerRepository.getByPrefix("0035")).thenReturn(MockProviderMother.providersFor0035Prefix());
        thrown.expect(ProviderNotFoundException.class);

        // WHEN
        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        // THEN
        Assert.assertNull(messageSentOperation);
    }

}
