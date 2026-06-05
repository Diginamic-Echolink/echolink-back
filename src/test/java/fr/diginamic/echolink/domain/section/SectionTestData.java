package fr.diginamic.echolink.domain.section;

public final class SectionTestData {

    public static Section givenSection1() {

        return new Section(
                "Environnement",
                "Discussions autour de l'écologie et du climat"
        );
    }

    public static Section givenSection2() {

        return new Section(
                "Qualité de l'air",
                "Pollution, AQI et santé publique"
        );
    }

    public static Section givenSection3() {

        return new Section(
                "Météo",
                "Prévisions et phénomènes météorologiques"
        );
    }

    public static SectionUpsertRequest givenSectionUpsertRequest() {

        return new SectionUpsertRequest(
                "Mobilité",
                "Transport et mobilité durable"
        );
    }
}
