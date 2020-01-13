package com.example.ogame.utils.facilities;

import com.example.ogame.models.facilities.Building;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
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
                        0, 100, 30,0, 100,
                        "00:01:00", "00:01:30", 1),

                new Building(UUID.randomUUID(), CRISTAL_MINE,
                        0, 120, 60,0, 100,
                        "00:02:00", "00:02:00", 1),

                new Building(UUID.randomUUID(), DEUTERIUM_MINE,
                        0, 130, 70,0, 100,
                        "00:03:00", "00:03:20", 1),

                new Building(UUID.randomUUID(), SOLAR_PLANT,
                        0, 100, 35,0, 100,
                        "00:04:00", "00:04:30", 1),

                new Building(UUID.randomUUID(), FUSION_REACTOR,
                        0, 500, 200,100, 0,
                        "00:05:00", "00:06:30", 1),

                new Building(UUID.randomUUID(), METAL_STORAGE,
                        0, 100, 0,0, 0,
                        "00:01:00", "00:01:30", 1),

                new Building(UUID.randomUUID(), CRISTAL_STORAGE,
                        0, 100, 50,0, 0,
                        "00:02:00", "00:02:30", 1),

                new Building(UUID.randomUUID(), DEUTERIUM_TANK,
                        0, 100, 100,0, 0,
                        "00:02:30", "00:03:30", 1),

                new Building(UUID.randomUUID(), ROBOTICS_FACTORY,
                        0, 200, 60,80, 0,
                        "00:06:00", "00:11:30", 1),

                new Building(UUID.randomUUID(), SHIPYARD,
                        0, 200, 80,60, 0,
                        "00:11:00", "00:15:30", 1),

                new Building(UUID.randomUUID(), RESEARCH_LAB,
                        0, 50, 100,80, 0,
                        "00:13:00", "00:16:30", 1),

                new Building(UUID.randomUUID(), NANITE_FACTORY,
                        0, 10000, 3000,100000, 10000,
                        "01:00:00", "02:00:00", 1)

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
                building.getProductionPerHour(),
                building.getBuildTime().replace(".000", ""),
                building.getNextBuildTime().replace(".000", ""),
                building.getIsAbleToBuild()
        };
    }

    public static Object[] updateBuilding(Building b, String newTime, String nextTime) {
        return new Object[] {
                b.getLevel(),
                b.getNeededMetal(),
                b.getNeededCristal(),
                b.getNeededDeuterium(),
                b.getProductionPerHour(),
                newTime.replace(".000", ""),
                nextTime.replace(".000", ""),
                b.getIsAbleToBuild(),
                b.getBuildingId()
        };
    }

    public static String nextBuildTime(String buildTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            long duration = dateFormat.parse(buildTime).getTime() * 2;
            return DurationFormatUtils.formatDurationHMS(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object[] insertFacilities(List<UUID> buildingsIdList) {
        return buildingsIdList.toArray();
    }
}
