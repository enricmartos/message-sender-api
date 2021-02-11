package org.emartos.messagesender.web;

import org.emartos.messagesender.MessageMother;
import org.emartos.messagesender.model.Message;
import org.emartos.messagesender.model.MessageSentOperation;
import org.emartos.messagesender.model.exceptions.InvalidMessageException;
import org.emartos.messagesender.model.exceptions.MessageSenderException;
import org.emartos.messagesender.service.ProviderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageSenderControllerTest {
    @InjectMocks
    private MessageSenderController messageSenderController;

    @Mock
    private ProviderService providerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // HAPPY PATHS
    @Test
    public void whenMessageContainsValidFields_thenOkStatusIsReturned() throws MessageSenderException {
        Message message = MessageMother.messageWithValidFields();
        when(providerService.sendMessage(message)).thenReturn(Mockito.mock(MessageSentOperation.class));

        ResponseEntity<MessageSentOperation> response = messageSenderController.sendMessage(message);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // CORNER CASES
    @Test
    public void whenMessageContainsNullFields_thenInvalidMessageExceptionIsThrown() {
        Message message = MessageMother.messageWithNullFields();

        assertThrows(InvalidMessageException.class,
                () -> messageSenderController.sendMessage(message));
    }

    @Test
    public void whenMessageContainsEmptyFields_thenInvalidMessageExceptionIsThrown() {
        Message message = MessageMother.messageWithEmptyFields();

        assertThrows(InvalidMessageException.class,
                () -> messageSenderController.sendMessage(message));
    }

    @Test
    public void whenMessageContainsToMobileNumberTooLong_thenInvalidMessageExceptionIsThrown() {
        Message message = MessageMother.messageWithToMobileNumberTooLong();

        assertThrows(InvalidMessageException.class,
                () -> messageSenderController.sendMessage(message));
    }

    @Test
    public void whenMessageContainsTextToSendTooLong_thenInvalidMessageExceptionIsThrown() {
        Message message = MessageMother.messageWithTextToSendTooLong();

        assertThrows(InvalidMessageException.class,
                () -> messageSenderController.sendMessage(message));
    }


}
