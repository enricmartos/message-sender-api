package org.emartos.messagesender;

import org.emartos.messagesender.model.Message;

public class MessageMother {

    public static Message messageWithNullFields() {
        return new Message(null, null);
    }

    public static Message messageWithEmptyFields() {
        return new Message("", "");
    }

    public static Message messageWithToMobileNumberTooLong() {
        return new Message("0034777111222123", "Test text");
    }

    public static Message messageWithTextToSendTooLong() {
        return new Message("0034777111222",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an.");
    }

    public static Message messageWithValidFields() {
        return new Message("0034777111222", "Test Text");
    }

    public static Message messageWithToMobileNumberPrefix0033() {
        return new Message("0033777111222", "Test Text");
    }

    public static Message messageWithToMobileNumberPrefix0034() {
        return new Message("0034666111222", "Test Text");
    }


    public static Message messageWithToMobileNumberPrefix0035() {
        return new Message("0035777111222", "Test Text");
    }




}
