package com.example.ogame.utils.facilities;

import com.example.ogame.models.facilities.Building;
import java.util.List;
import java.util.UUID;

import static com.example.ogame.utils.facilities.BuildingName.*;

public class FacilitiesHelper {

    /**
     * Method that will create all building for new user instance that will be unique fo him
     *
     * @return buildingList
     */
    public static List<Building> createFacilities() {
        return List.of(
                new Building(UUID.randomUUID(), METAL_MINE,
                        0, 100, 30,0, 100, 180),

                new Building(UUID.randomUUID(), CRISTAL_MINE,
                        0, 120, 60,0, 100, 90),

                new Building(UUID.randomUUID(), DEUTERIUM_MINE,
                        0, 130, 70,0, 100, 10),

                new Building(UUID.randomUUID(), SOLAR_PLANT,
                        0, 100, 35,0, 100, 0),

                new Building(UUID.randomUUID(), FUSION_REACTOR,
                        0, 500, 200,100, 100, 0),

                new Building(UUID.randomUUID(), METAL_STORAGE,
                        0, 100, 0,0, 100, 0),

                new Building(UUID.randomUUID(), CRISTAL_STORAGE,
                        0, 100, 50,0, 100, 0),

                new Building(UUID.randomUUID(), DEUTERIUM_TANK,
                        0, 100, 100,0, 100, 0),

                new Building(UUID.randomUUID(), ROBOTICS_FACTORY,
                        0, 200, 60,80, 100, 0),

                new Building(UUID.randomUUID(), SHIPYARD,
                        0, 200, 80,60, 100, 0),

                new Building(UUID.randomUUID(), RESEARCH_LAB,
                        0, 50, 100,80, 100, 0),

                new Building(UUID.randomUUID(), NANITE_FACTORY,
                        0, 10000, 3000,100000, 10000, 0)

        );
    }

    public static Object[] insertBuilding(Building building) {
        return new Object[] {
                building.getBuildingId(),
                building.getName().toString(),
                building.getLevel(),
                building.getNeededMetal(),
                building.getNeededCristal(),
                building.getNeededDeuterium(),
                building.getBuildTime(),
                building.getProductionPerHour()
        };
    }

    public static Object[] updateBuilding(Building b) {
        return new Object[] {
                b.getLevel(),
                b.getNeededMetal(),
                b.getNeededCristal(),
                b.getNeededDeuterium(),
                b.getProductionPerHour(),
                b.getBuildingId()
        };
    }

    public static Object[] insertFacilities(List<UUID> buildingsIdList) {
        return buildingsIdList.toArray();
    }
}
