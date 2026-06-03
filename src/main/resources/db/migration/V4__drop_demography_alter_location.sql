-- ============================================================
-- V4__drop_demography_table_alter_location.sql
-- Permanent removal of the demography table
-- ============================================================

ALTER TABLE location
    ADD COLUMN population BIGINT NOT NULL DEFAULT 0;

UPDATE location l
    JOIN demography d ON d.location_id = l.id
    SET l.population = d.total_pop;

DROP TABLE demography;
