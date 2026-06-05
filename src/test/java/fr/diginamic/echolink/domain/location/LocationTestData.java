package fr.diginamic.echolink.domain.location;

public final class LocationTestData {

    public static Location givenLocation1() {

        return new Location(
                "Saint-Étienne",
                "42218",
                "42000",
                4.3872,
                45.4397,
                173000L
        );
    }

    public static Location givenLocation2() {

        return new Location(
                "Lyon",
                "69123",
                "69000",
                4.8357,
                45.7640,
                520000L
        );
    }

    public static Location givenLocation3() {

        return new Location(
                "Paris",
                "75056",
                "75000",
                2.3522,
                48.8566,
                2_100_000L
        );
    }

    public static Location givenLocation4() {

        return new Location(
                "Nantes",
                "44109",
                "44000",
                -1.5536,
                47.2184,
                320_000L
        );
    }
}
