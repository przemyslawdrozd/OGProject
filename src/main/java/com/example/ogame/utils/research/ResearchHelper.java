package com.example.ogame.utils.research;

import com.example.ogame.models.research.Technology;
import java.util.List;
import java.util.UUID;

import static com.example.ogame.utils.research.TechName.*;

public class ResearchHelper {

    public static List<Technology> createResearch() {

        return List.of(
                new Technology(UUID.randomUUID(), ENERGY_TECH, 0, 0, 800, 400, "00:04:00", 1),
                new Technology(UUID.randomUUID(), LASER_TECH, 0, 200, 100, 0, "00:01:30", 1),
                new Technology(UUID.randomUUID(), ION_TECH, 0, 1000, 300, 100, "00:06:30", 1),
                new Technology(UUID.randomUUID(), HYPER_TECH, 0, 0, 4000, 2000, "00:20:00", 1),
                new Technology(UUID.randomUUID(), PLASMA_TECH, 0, 2000, 4000, 1000, "00:30:00", 1),
                new Technology(UUID.randomUUID(), COMBUSTION_DRIVE, 0, 400, 0, 600, "00:02:00", 1),
                new Technology(UUID.randomUUID(), IMPULSIVE_DRIVE, 0, 2000, 4000, 600, "00:30:00", 1),
                new Technology(UUID.randomUUID(), HYPERSPACE_DRIVE, 0, 10000, 20000, 6660, "01:30:00", 1),
                new Technology(UUID.randomUUID(), SPY_TECH, 0, 2000, 1000, 200, "00:05:00", 1),
                new Technology(UUID.randomUUID(), COMP_TECH, 0, 0, 400, 600, "00:02:00", 1),
                new Technology(UUID.randomUUID(), ASTRO_TECH, 0, 4000, 8000, 4000, "01:00:00", 1),
                new Technology(UUID.randomUUID(), INTER_RESEARCH_TECH, 0, 100_000, 100_000, 100_000, "24:00:00", 1),
                new Technology(UUID.randomUUID(), GRAVITON_TECH, 0, 0, 0, 0, "00:00:01", 1),
                new Technology(UUID.randomUUID(), WEP_TECH, 0, 800, 200, 0, "00:05:00", 1),
                new Technology(UUID.randomUUID(), SHIELD_TECH, 0, 200, 600, 0, "00:04:00", 1),
                new Technology(UUID.randomUUID(), ARMOR_TECH, 0, 1000, 0, 0, "00:05:00", 1)
        );
    }

    public static Object[] insertTech(Technology tech) {
        return new Object[] {
                tech.getTechId(),
                tech.getName(),
                tech.getLvl(),
                tech.getNeededMetal(),
                tech.getNeededCristal(),
                tech.getNeededDeuterium(),
                tech.getBuildTime(),
                tech.isAbleToBuild()
        };
    }

    public static Object[] updateTech(Technology tech) {
        return new Object[] {
                tech.getLvl(),
                tech.getNeededMetal(),
                tech.getNeededCristal(),
                tech.getNeededDeuterium(),
                tech.getBuildTime().replace(".000", ""),
                tech.isAbleToBuild(),
                tech.getTechId()
        };
    }

    public static Object[] insertResearch(List<UUID> researchIdList) {
        return researchIdList.toArray();
    }

}
