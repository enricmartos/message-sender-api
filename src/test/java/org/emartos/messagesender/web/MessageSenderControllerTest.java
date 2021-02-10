package org.emartos.messagesender.web;

import org.emartos.messagesender.MessageMother;
import org.emartos.messagesender.model.Message;
import org.emartos.messagesender.model.MessageSentOperation;
import org.emartos.messagesender.model.exceptions.InvalidMessageException;
import org.emartos.messagesender.model.exceptions.MessageSenderException;
import org.emartos.messagesender.service.ProviderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

public class MessageSenderControllerTest {

    @InjectMocks
    MessageSenderController messageSenderController;
    @Mock
    ProviderService providerService;
    private MessageSentOperation messageSentOperationMock;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // HAPPY PATH
    @Test
    public void whenMessageContainsValidFields_thenOkStatusIsReturned() throws MessageSenderException {
        // GIVEN
        Message message = MessageMother.messageWithValidFields();
        when(providerService.sendMessage(message)).thenReturn(Mockito.mock(MessageSentOperation.class));

        // WHEN
        ResponseEntity<MessageSentOperation> response = messageSenderController.sendMessage(message);

        // THEN
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    // CORNER CASES

    @Test
    public void whenMessageContainsNullFields_thenBadRequestStatusIsReturned() throws MessageSenderException {
        // GIVEN
        Message message = MessageMother.messageWithNullFields();
        thrown.expect(InvalidMessageException.class);

        // WHEN
        ResponseEntity<MessageSentOperation> response = messageSenderController.sendMessage(message);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void whenMessageContainsEmptyFields_thenBadRequestStatusIsReturned() throws MessageSenderException {
        // GIVEN
        Message message = MessageMother.messageWithEmptyFields();
        thrown.expect(InvalidMessageException.class);

        // WHEN
        ResponseEntity<MessageSentOperation> response = messageSenderController.sendMessage(message);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void whenMessageContainsToMobileNumberTooLong_thenBadRequestStatusIsReturned() throws MessageSenderException {
        // GIVEN
        Message message = MessageMother.messageWithToMobileNumberTooLong();
        thrown.expect(InvalidMessageException.class);

        // WHEN
        ResponseEntity<MessageSentOperation> response = messageSenderController.sendMessage(message);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void whenMessageContainsTextToSendTooLong_thenBadRequestStatusIsReturned() throws MessageSenderException {
        // GIVEN
        Message message = MessageMother.messageWithTextToSendTooLong();
        thrown.expect(InvalidMessageException.class);

        // WHEN
        ResponseEntity<MessageSentOperation> response = messageSenderController.sendMessage(message);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }




}
