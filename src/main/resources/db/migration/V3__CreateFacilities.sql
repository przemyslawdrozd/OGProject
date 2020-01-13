CREATE TABLE IF NOT EXISTS building (

    building_id UUID NOT NULL PRIMARY KEY,
    building_name VARCHAR(20) NOT NULL
        CHECK (
            building_name = 'METAL_MINE' OR
            building_name = 'CRISTAL_MINE' OR
            building_name = 'DEUTERIUM_MINE' OR
            building_name = 'SOLAR_PLANT' OR
            building_name = 'FUSION_REACTOR' OR
            building_name = 'METAL_STORAGE' OR
            building_name = 'CRISTAL_STORAGE' OR
            building_name = 'DEUTERIUM_TANK' OR
            building_name = 'ROBOTICS_FACTORY' OR
            building_name = 'SHIPYARD' OR
            building_name = 'RESEARCH_LAB' OR
            building_name = 'NANITE_FACTORY' ),
    lvl INTEGER NOT NULL,
    needed_metal INTEGER NOT NULL,
    needed_cristal INTEGER NOT NULL,
    needed_deuterium INTEGER NOT NULL,
    production_per_hour INTEGER NOT NULL,
    build_time VARCHAR (20) NOT NULL,
    next_build_time VARCHAR (20) NOT NULL,
    is_able INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS facilities (

    facilities_id UUID NOT NULL PRIMARY KEY,
    metal_mine UUID NOT NULL REFERENCES building(building_id),
    cristal_mine UUID NOT NULL REFERENCES building(building_id),
    deuterium_mine UUID NOT NULL REFERENCES building(building_id),
    solar_plant UUID NOT NULL REFERENCES building(building_id),
    fusion_reactor UUID NOT NULL REFERENCES building(building_id),

    metal_storage UUID NOT NULL REFERENCES building(building_id),
    cristal_storage UUID NOT NULL REFERENCES building(building_id),
    deuterium_tank UUID NOT NULL REFERENCES building(building_id),

    robotics_factory UUID NOT NULL REFERENCES building(building_id),
    shipyard UUID NOT NULL REFERENCES building(building_id),
    research_lab UUID NOT NULL REFERENCES building(building_id),
    nanite_factory UUID NOT NULL REFERENCES building(building_id)
);

ALTER TABLE user_instance
ADD COLUMN facilities_id UUID
REFERENCES facilities(facilities_id),
ADD UNIQUE (facilities_id);