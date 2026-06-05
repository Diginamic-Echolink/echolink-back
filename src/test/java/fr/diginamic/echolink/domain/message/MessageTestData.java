package fr.diginamic.echolink.domain.message;

import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread1;

public final class MessageTestData {

    public static Message givenMessage() {

        Message message = new Message("Hello world !");
        message.setProfile(givenProfile1());
        message.setThread(givenThread1());
        return message;
    }
}
