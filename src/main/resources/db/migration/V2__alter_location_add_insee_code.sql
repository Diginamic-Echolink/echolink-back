-- V2__alter_location_add_insee_code.sql

ALTER TABLE location
    DROP COLUMN altitude;

ALTER TABLE location
    ADD COLUMN insee_code VARCHAR(10);

ALTER TABLE location
    ADD CONSTRAINT uk_location_insee_code UNIQUE (insee_code);

ALTER TABLE location
    MODIFY insee_code VARCHAR(10) NOT NULL;

ALTER TABLE location
    MODIFY postal_code VARCHAR(20) NULL;