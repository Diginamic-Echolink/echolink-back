package fr.diginamic.echolink.domain.airquality;

import java.time.LocalDateTime;

import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation1;
import static fr.diginamic.echolink.domain.location.LocationTestData.givenLocation2;

public final class AirQualityTestData {

    public static AirQuality givenAirQuality1() {

        AirQuality aq = new AirQuality(
                LocalDateTime.now(),
                12.5f,
                20.3f,
                (byte) 2,
                0.4f,
                0.03f,
                10.2f,
                15.1f,
                0.7f
        );
        aq.setLocation(givenLocation1());
        return aq;
    }

    public static AirQuality givenAirQuality2() {

        AirQuality aq = new AirQuality(
                LocalDateTime.now().minusHours(2),
                35.8f,
                42.1f,
                (byte) 4,
                1.2f,
                0.08f,
                18.6f,
                22.4f,
                1.5f
        );
        aq.setLocation(givenLocation2());
        return aq;
    }

    public static AirQuality givenAirQuality3() {

        AirQuality aq = new AirQuality(
                LocalDateTime.now().minusMinutes(30),
                85.6f,
                110.3f,
                (byte) 7,
                3.5f,
                0.15f,
                55.0f,
                80.2f,
                4.8f
        );
        aq.setLocation(givenLocation1());
        return aq;
    }
}
