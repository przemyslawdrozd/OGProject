package com.example.ogame.models.facilities;

import com.example.ogame.utils.facilities.BuildingName;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class Building {

    private UUID buildingId;
    private final BuildingName name;
    private int level;
    private int neededMetal;
    private int neededCristal;
    private int neededDeuterium;
    private int productionPerHour;
    private String buildTime;
    private String nextBuildTime;
    private int isAbleToBuild;

    public Building(UUID buildingId, BuildingName name, int level, int neededMetal, int neededCristal, int neededDeuterium,
                    int productionPerHour, String buildTime, String nextBuildTime, int isAbleToBuild) {
        this.buildingId = buildingId;
        this.name = name;
        this.level = level;
        this.neededMetal = neededMetal;
        this.neededCristal = neededCristal;
        this.neededDeuterium = neededDeuterium;
        this.productionPerHour = productionPerHour;
        this.buildTime = buildTime;
        this.nextBuildTime = nextBuildTime;
        this.isAbleToBuild = isAbleToBuild;
    }

    public boolean countDown() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            long duration = dateFormat.parse(buildTime).getTime() - 1000;
            if (duration > 1001) {
                this.buildTime = DurationFormatUtils.formatDurationHMS(duration).replace(".000", "");
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void lvlUpBuilding(){
        double increaseCost = 1.75;
        this.level++;
        this.neededMetal *= increaseCost;
        this.neededCristal *= increaseCost;
        this.neededDeuterium *= increaseCost;
        if (productionPerHour != 0) productionPerHour *= 2;
    }

    public void setBuildingId(UUID buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getNextBuildTime() {
        return nextBuildTime;
    }

    public void setNextBuildTime(String nextBuildTime) {
        this.nextBuildTime = nextBuildTime;
    }

    public int getIsAbleToBuild() {
        return isAbleToBuild;
    }

    public void setIsAbleToBuild(int isAbleToBuild) {
        this.isAbleToBuild = isAbleToBuild;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNeededMetal() {
        return neededMetal;
    }

    public void setNeededMetal(int neededMetal) {
        this.neededMetal = neededMetal;
    }

    public int getNeededCristal() {
        return neededCristal;
    }

    public void setNeededCristal(int neededCristal) {
        this.neededCristal = neededCristal;
    }

    public int getNeededDeuterium() {
        return neededDeuterium;
    }

    public void setNeededDeuterium(int neededDeuterium) {
        this.neededDeuterium = neededDeuterium;
    }

    public BuildingName getName() {
        return name;
    }

    public UUID getBuildingId() {
        return buildingId;
    }

    public int getProductionPerHour() {
        return productionPerHour;
    }

    public void setProductionPerHour(int productionPerHour) {
        this.productionPerHour = productionPerHour;
    }
}
