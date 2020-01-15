CREATE TABLE IF NOT EXISTS planet (

   planet_id UUID NOT NULL PRIMARY KEY,
   galaxy_position INTEGER NOT NULL,
   planetary_system INTEGER NOT NULL,
   planet_position INTEGER NOT NULL
);

ALTER TABLE user_instance
ADD COLUMN planet_id UUID
REFERENCES planet(planet_id),
ADD UNIQUE (planet_id);