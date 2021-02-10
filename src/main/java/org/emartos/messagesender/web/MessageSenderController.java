package org.emartos.messagesender.web;

import org.emartos.messagesender.model.Message;
import org.emartos.messagesender.model.MessageSentOperation;
import org.emartos.messagesender.model.exceptions.InvalidMessageException;
import org.emartos.messagesender.model.exceptions.MessageSenderException;
import org.emartos.messagesender.service.ProviderService;
import org.emartos.messagesender.web.validator.MessageValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(MessageSenderApiConstants.MESSAGE_SENDER_API_PATH + MessageSenderApiConstants.VERSION)
public class MessageSenderController {
    @Autowired
    private ProviderService providerService;

    @PostMapping(MessageSenderApiConstants.SEND_MESSAGE_ENDPOINT)
    public ResponseEntity<MessageSentOperation> sendMessage(@RequestBody Message message) throws MessageSenderException {
        validateMessage(message);
        MessageSentOperation messageSentOperation = providerService.sendMessage(message);

        return new ResponseEntity<>(messageSentOperation, HttpStatus.OK);
    }

    private void validateMessage(Message message) throws InvalidMessageException {
        new MessageValidationHelper()
                .checkValidDestinationMobileNumber(message.getToMobileNumber(), "toMobileNumber")
                .checkValidTextToBeSent(message.getTextToSend(), "textToSend");
    }

}
