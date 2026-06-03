-- ============================================================
-- V3__update_meteo_precision.sql
-- EchoLink - Update Meteo precision types
-- ============================================================

ALTER TABLE meteo
    ADD COLUMN weather_condition VARCHAR(30);

ALTER TABLE meteo
    MODIFY temperature FLOAT;

ALTER TABLE meteo
    MODIFY atm_pressure FLOAT;

ALTER TABLE meteo
    MODIFY humidity FLOAT;

ALTER TABLE meteo
    MODIFY wind_speed FLOAT;

ALTER TABLE meteo
    MODIFY rain_fall FLOAT;
