package com.example.ogame.models.research;

import org.apache.commons.lang3.time.DurationFormatUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class Technology {

    private UUID techId;
    private String name;
    private int lvl;
    private int neededMetal;
    private int neededCristal;
    private int neededDeuterium;
    private String buildTime;
    private String nextBuildTime;
    private int isAbleToBuild;

    public Technology(UUID tech_id, String name, int lvl, int neededMetal, int neededCristal,
                      int neededDeuterium, String buildTime, String nextBuildTime, int isAbleToBuild) {
        this.techId = tech_id;
        this.name = name;
        this.lvl = lvl;
        this.neededMetal = neededMetal;
        this.neededCristal = neededCristal;
        this.neededDeuterium = neededDeuterium;
        this.buildTime = buildTime;
        this.isAbleToBuild = isAbleToBuild;
        this.nextBuildTime = nextBuildTime;
    }

    public void lvlUpTech() {
        lvl++;
        this.neededMetal *= 2;
        this.neededCristal *= 2;
        this.neededDeuterium *= 2;
    }

    public String getNextBuildTime() {
        return nextBuildTime.replace(".000", "");
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

    public void setNextBuildTime(String nextBuildTime) {
        this.nextBuildTime = nextBuildTime;
    }

    public void setIsAbleToBuild(int isAbleToBuild) {
        this.isAbleToBuild = isAbleToBuild;
    }

    public int getIsAbleToBuild() {
        return isAbleToBuild;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public UUID getTechId() {
        return techId;
    }

    public void setTechId(UUID techId) {
        this.techId = techId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public int isAbleToBuild() {
        return isAbleToBuild;
    }

    public void setAbleToBuild(int ableToBuild) {
        isAbleToBuild = ableToBuild;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "name='" + name + '\'' +
                ", lvl=" + lvl +
                ", buildTime='" + buildTime + '\'' +
                '}';
    }
}
