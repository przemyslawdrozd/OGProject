ALTER TABLE building
ADD COLUMN build_time INTEGER,
ADD COLUMN production_per_hour INTEGER;

ALTER TABLE buildings
ADD COLUMN b_shipyard_id UUID REFERENCES building(building_id);