package fr.diginamic.echolink.application.meteo.port.in;

public interface MeteoSyncUseCase {

    void syncTodayMeteo() throws InterruptedException;
}
