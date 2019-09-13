CREATE TABLE IF NOT EXISTS building (

    building_id UUID NOT NULL PRIMARY KEY,
    namee VARCHAR(50) NOT NULL,
    lvl INTEGER NOT NULL,
    needed_metal INTEGER NOT NULL,
    needed_cristal INTEGER NOT NULL,
    needed_deuterium INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS buildings (

    buildings_id UUID NOT NULL PRIMARY KEY,
    b_metal_id UUID NOT NULL REFERENCES building(building_id),
    b_cristal_id UUID NOT NULL REFERENCES building(building_id),
    b_deuterium_id UUID NOT NULL REFERENCES building(building_id)
);

ALTER TABLE user_instance
ADD COLUMN buildings_id UUID
REFERENCES buildings(buildings_id),
ADD UNIQUE (buildings_id);