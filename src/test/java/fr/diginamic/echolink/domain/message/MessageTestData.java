package fr.diginamic.echolink.domain.message;

import java.util.UUID;

import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile2;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile3;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread1;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread3;

public final class MessageTestData {

    public static Message givenMessage1() {

        Message message = new Message();
        message.setText("Hello World");
        message.setProfile(givenProfile1());
        message.setThread(givenThread1());
        return message;
    }

    public static Message givenMessage2() {

        Message message = new Message();
        message.setText("Second message content");
        message.setProfile(givenProfile2());
        message.setThread(givenThread1());
        return message;
    }

    public static Message givenMessage3() {

        Message message = new Message();
        message.setText("Message in another thread");
        message.setProfile(givenProfile3());
        message.setThread(givenThread3());
        return message;
    }

    public static MessageCreateRequest givenMessageCreateRequest(UUID threadId, UUID profileId) {

        return new MessageCreateRequest(
                threadId,
                profileId,
                "Hello World !!!"
        );
    }

    public static MessageUpdateRequest givenMessageUpdateRequest() {

        return new MessageUpdateRequest("YES");
    }
}
