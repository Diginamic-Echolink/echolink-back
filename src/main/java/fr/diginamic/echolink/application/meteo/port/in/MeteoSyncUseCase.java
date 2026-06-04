package fr.diginamic.echolink.application.meteo.port.in;

public interface MeteoSyncUseCase {

    void initializeQueue();

    void syncTodayMeteo();
}
