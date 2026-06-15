-- ============================================================
-- V9__add_profile_favorite_locations.sql
-- Add favorite locations (Many-to-Many Profile <-> Location) and remove old OneToMany/legacy fields
-- ============================================================

CREATE TABLE profile_favorite_locations (
                                            profile_id CHAR(36) NOT NULL,
                                            location_id CHAR(36) NOT NULL,

                                            PRIMARY KEY (profile_id, location_id),

                                            CONSTRAINT fk_pfl_profile
                                                FOREIGN KEY (profile_id)
                                                    REFERENCES profile(id)
                                                    ON DELETE CASCADE,

                                            CONSTRAINT fk_pfl_location
                                                FOREIGN KEY (location_id)
                                                    REFERENCES location(id)
                                                    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE profile
    DROP FOREIGN KEY fk_profile_location;

ALTER TABLE profile
    DROP COLUMN location_id;

ALTER TABLE profile
    DROP COLUMN city;

ALTER TABLE profile
    DROP COLUMN address;
