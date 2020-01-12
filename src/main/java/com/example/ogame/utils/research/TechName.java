package com.example.ogame.utils.research;

public class TechName {

    public static final String ENERGY_TECH = "Energy Technology";
    public static final String LASER_TECH = "Laser Technology";
    public static final String ION_TECH = "Ion Technology";
    public static final String HYPER_TECH = "Hyperspace Technology";
    public static final String PLASMA_TECH = "Plasma Technology";
    public static final String COMBUSTION_DRIVE = "Combustion Drive";
    public static final String IMPULSIVE_DRIVE = "Impulse Drive";
    public static final String HYPERSPACE_DRIVE = "Hyperspace Drive";
    public static final String SPY_TECH = "Espionage Technology";
    public static final String COMP_TECH = "Computer Technology";
    public static final String ASTRO_TECH = "Astrophysics";
    public static final String INTER_RESEARCH_TECH = "Intergalactic Research Network";
    public static final String GRAVITON_TECH = "Graviton Technology";
    public static final String WEP_TECH = "Weapons Technology";
    public static final String SHIELD_TECH = "Shielding Technology";
    public static final String ARMOR_TECH = "Armor Technology";

    public static boolean checkTechName(String techName) {
        return ENERGY_TECH.toLowerCase().equals(techName) ||
                LASER_TECH.toLowerCase().equals(techName) ||
                ION_TECH.toLowerCase().equals(techName) ||
                HYPER_TECH.toLowerCase().equals(techName) ||
                PLASMA_TECH.toLowerCase().equals(techName) ||
                COMBUSTION_DRIVE.toLowerCase().equals(techName) ||
                IMPULSIVE_DRIVE.toLowerCase().equals(techName) ||
                HYPERSPACE_DRIVE.toLowerCase().equals(techName) ||
                SPY_TECH.toLowerCase().equals(techName) ||
                COMP_TECH.toLowerCase().equals(techName) ||
                ASTRO_TECH.toLowerCase().equals(techName) ||
                INTER_RESEARCH_TECH.toLowerCase().equals(techName) ||
                GRAVITON_TECH.toLowerCase().equals(techName) ||
                WEP_TECH.toLowerCase().equals(techName) ||
                SHIELD_TECH.toLowerCase().equals(techName) ||
                ARMOR_TECH.toLowerCase().equals(techName);
    }

    private TechName() {}
}
