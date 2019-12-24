package com.example.ogame.utils;

import com.example.ogame.models.building.Building;
import com.example.ogame.models.building.ResourceBuilding;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateNewBuildingsHelper {

    /**
     * Method that will create all building for new user instance that will be unique fo him
     *
     * @return buildingList
     */
    public static List<Building> createNewBuildingsForInstance() {

        List<Building> buildingList = new ArrayList<>();
        buildingList.add(new ResourceBuilding(UUID.randomUUID(), "Metal Mine",
                1, 100, 30,0, 100, 500));

        buildingList.add(new ResourceBuilding(UUID.randomUUID(), "Cristal Mine",
                1, 120, 60,0, 150, 250));

        buildingList.add(new ResourceBuilding(UUID.randomUUID(), "Deuterium Synthesizer",
                1, 150, 40,0, 150, 100));

        buildingList.add(new Building(UUID.randomUUID(), "Shipyard",
                0,1500,250,100,2000));

        return buildingList;
    }
}
