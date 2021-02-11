package org.emartos.messagesender.web.validator;

import org.emartos.messagesender.model.exceptions.InvalidMessageException;

/**
* This class defines the integrity rules that Message fields must fulfill
*/
public class MessageValidationHelper {

    private static final int MOBILE_NUMBER_LENGTH = 13;
    private static final int MAX_TEXT_LENGTH = 160;

    public MessageValidationHelper checkValidDestinationMobileNumber(String value, String parameterName) throws InvalidMessageException {
        checkNotNull(value, parameterName);
        checkNotEmpty(value, parameterName);
        if (value.length() != MOBILE_NUMBER_LENGTH) {
            throw new InvalidMessageException(String.format("Field %s has a length different from %d",
                    parameterName, MOBILE_NUMBER_LENGTH));
        }
        return this;
    }

    public MessageValidationHelper checkValidTextToBeSent(String value, String parameterName) throws InvalidMessageException {
        checkNotNull(value, parameterName);
        checkNotEmpty(value, parameterName);
        if (value.length() > MAX_TEXT_LENGTH) {
            throw new InvalidMessageException(String.format("Field %s can't be greater than %d",
                    parameterName, MOBILE_NUMBER_LENGTH));
        }
        return this;
    }


    private void checkNotNull(String value, String parameterName) throws InvalidMessageException {
        if (value == null) {
            throw new InvalidMessageException(String.format("Field %s can't be null", parameterName));
        }
    }

    private void checkNotEmpty(String value, String parameterName) throws InvalidMessageException {
        if (value.isEmpty()) {
            throw new InvalidMessageException(String.format("Field %s can't be empty", parameterName));
        }
    }
}
