package fr.diginamic.echolink.domain.thread;

import java.time.LocalDateTime;
import java.util.UUID;

import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile2;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile3;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection1;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection2;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection3;

public final class ThreadTestData {

    public static Thread givenThread1() {

        return new Thread(
                "Qualité de l'air à Lyon",
                "Que pensez-vous des derniers relevés ?",
                LocalDateTime.of(2025, 1, 10, 10, 0),
                givenSection1(),
                givenProfile1()
        );
    }

    public static Thread givenThread2() {

        return new Thread(
                "Pollution urbaine",
                "Impact des transports en ville",
                LocalDateTime.of(2025, 1, 11, 14, 30),
                givenSection2(),
                givenProfile2()
        );
    }

    public static Thread givenThread3() {

        return new Thread(
                "Prévisions météo",
                "Temps prévu cette semaine",
                LocalDateTime.of(2025, 1, 12, 8, 15),
                givenSection3(),
                givenProfile3()
        );
    }

    public static ThreadCreateRequest givenThreadCreateRequest(UUID sectionId, UUID profileId) {

        return new ThreadCreateRequest(
                sectionId,
                profileId,
                "Mon sujet",
                "Description"
        );
    }

    public static ThreadUpdateRequest givenThreadUpdateRequest(UUID sectionId, UUID profileId) {

        return new ThreadUpdateRequest(
                "Nouveau sujet",
                "Nouvelle Description",
                profileId,
                sectionId
        );
    }
}
