CREATE TABLE IF NOT EXISTS ship (

    ship_id UUID NOT NULL PRIMARY KEY,
    ship_name VARCHAR(20) NOT NULL
        CHECK (
            ship_name = 'SMALL_CARGO_SHIP' OR
            ship_name = 'LARGE_CARGO_SHIP' OR
            ship_name = 'LIGHT_FIGHTER' OR
            ship_name = 'BATTLE_SHIP' OR
            ship_name = 'COLONY_SHIP' ),
    attack INTEGER NOT NULL,
    defense INTEGER NOT NULL,
    speed INTEGER NOT NULL,
    capacity INTEGER NOT NULL,
    fuel INTEGER NOT NULL,
    metal_cost INTEGER NOT NULL,
    cristal_cost INTEGER NOT NULL,
    deuterium_cost INTEGER NOT NULL,
    amount_of_ship INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS fleet (

    fleet_id UUID NOT NULL PRIMARY KEY,
    small_cargo_ship UUID NOT NULL REFERENCES ship(ship_id),
    large_cargo_ship UUID NOT NULL REFERENCES ship(ship_id),
    light_fighter UUID NOT NULL REFERENCES ship(ship_id),
    battle_ship UUID NOT NULL REFERENCES ship(ship_id),
    colony_ship UUID NOT NULL REFERENCES ship(ship_id)
);

ALTER TABLE user_instance
ADD COLUMN fleet_id UUID
REFERENCES fleet(fleet_id),
ADD UNIQUE (fleet_id);