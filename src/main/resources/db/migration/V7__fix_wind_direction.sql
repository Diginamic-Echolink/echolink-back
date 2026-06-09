-- ============================================================
-- V7__fix_wind_direction.sql
-- Wind direction migration to the new values
-- ============================================================

ALTER TABLE meteo
DROP CONSTRAINT chk_wind_direction;

UPDATE meteo
SET wind_direction = 'NORTH'
WHERE wind_direction = 'North';

UPDATE meteo
SET wind_direction = 'SOUTH'
WHERE wind_direction = 'South';

UPDATE meteo
SET wind_direction = 'EAST'
WHERE wind_direction = 'East';

UPDATE meteo
SET wind_direction = 'WEST'
WHERE wind_direction = 'West';

UPDATE meteo
SET wind_direction = 'NORTH_EAST'
WHERE wind_direction = 'NorthEast';

UPDATE meteo
SET wind_direction = 'SOUTH_EAST'
WHERE wind_direction = 'SouthEast';

UPDATE meteo
SET wind_direction = 'NORTH_WEST'
WHERE wind_direction = 'NorthWest';

UPDATE meteo
SET wind_direction = 'SOUTH_WEST'
WHERE wind_direction = 'SouthWest';

ALTER TABLE meteo
    ADD CONSTRAINT chk_wind_direction
        CHECK (
            wind_direction IN (
                               'NORTH',
                               'SOUTH',
                               'EAST',
                               'WEST',
                               'NORTH_EAST',
                               'SOUTH_EAST',
                               'NORTH_WEST',
                               'SOUTH_WEST'
                )
            );
