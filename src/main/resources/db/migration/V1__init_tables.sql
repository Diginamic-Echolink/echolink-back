-- ============================================================
-- V1__init_tables.sql
-- EchoLink - Initial schema MariaDB
-- ============================================================

CREATE TABLE location (
                          id CHAR(36) NOT NULL,

                          name VARCHAR(255) NOT NULL,
                          postal_code VARCHAR(20) NOT NULL,
                          longitude DOUBLE,
                          latitude DOUBLE,
                          altitude DOUBLE,

                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE section (
                         id CHAR(36) NOT NULL,

                         name VARCHAR(255),
                         topic VARCHAR(255),

                         PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE profile (
                         id CHAR(36) NOT NULL,

                         first_name VARCHAR(255),
                         last_name VARCHAR(255),
                         pseudo VARCHAR(255),

                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,

                         city VARCHAR(255),
                         postal_code VARCHAR(20),
                         address VARCHAR(255),
                         phone_number VARCHAR(50),
                         link_img_profile VARCHAR(1000),
                         role VARCHAR(20),

                         location_id CHAR(36),

                         PRIMARY KEY (id),

                         CONSTRAINT uk_profile_email
                             UNIQUE (email),

                         CONSTRAINT chk_profile_role
                             CHECK (role IN ('USER', 'ADMIN')),

                         CONSTRAINT fk_profile_location
                             FOREIGN KEY (location_id)
                                 REFERENCES location(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE demography (
                            id CHAR(36) NOT NULL,

                            recorded_at DATE,

                            total_pop BIGINT,

                            location_id CHAR(36),

                            PRIMARY KEY (id),

                            CONSTRAINT fk_demography_location
                                FOREIGN KEY (location_id)
                                    REFERENCES location(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE air_quality (
                             id CHAR(36) NOT NULL,

                             recorded_at DATETIME,

                             particles_10 DOUBLE,
                             particles_25 DOUBLE,

                             eu_aqi TINYINT,

                             carbon_monoxide DOUBLE,
                             ozone DOUBLE,
                             dust DOUBLE,
                             nitrogen_dioxide DOUBLE,
                             sulfur_dioxide DOUBLE,

                             location_id CHAR(36),

                             PRIMARY KEY (id),

                             CONSTRAINT fk_air_quality_location
                                 FOREIGN KEY (location_id)
                                     REFERENCES location(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE meteo (
                       id CHAR(36) NOT NULL,

                       recorded_at DATETIME,

                       temperature TINYINT,
                       atm_pressure INT,
                       humidity TINYINT,
                       wind_speed TINYINT,
                       wind_direction VARCHAR(50),
                       rain_fall INT,

                       location_id CHAR(36),

                       PRIMARY KEY (id),

                       CONSTRAINT chk_wind_direction
                           CHECK (
                               wind_direction IN (
                                                  'North',
                                                  'South',
                                                  'East',
                                                  'West',
                                                  'NorthEast',
                                                  'SouthEast',
                                                  'NorthWest',
                                                  'SouthWest'
                                   )
                               ),

                       CONSTRAINT fk_meteo_location
                           FOREIGN KEY (location_id)
                               REFERENCES location(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE thread (
                        id CHAR(36) NOT NULL,

                        title VARCHAR(75),
                        subject VARCHAR(500),
                        created_at DATETIME,
                        like_count INT NOT NULL DEFAULT 0,
                        dislike_count INT NOT NULL DEFAULT 0,

                        section_id CHAR(36),
                        profile_id CHAR(36),

                        PRIMARY KEY (id),

                        CONSTRAINT fk_thread_section
                            FOREIGN KEY (section_id)
                                REFERENCES section(id),

                        CONSTRAINT fk_thread_profile
                            FOREIGN KEY (profile_id)
                                REFERENCES profile(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE message (
                         id CHAR(36) NOT NULL,

                         text VARCHAR(10000),
                         like_count INT NOT NULL DEFAULT 0,
                         dislike_count INT NOT NULL DEFAULT 0,

                         thread_id CHAR(36),
                         profile_id CHAR(36),

                         PRIMARY KEY (id),

                         CONSTRAINT fk_message_thread
                             FOREIGN KEY (thread_id)
                                 REFERENCES thread(id),

                         CONSTRAINT fk_message_profile
                             FOREIGN KEY (profile_id)
                                 REFERENCES profile(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
