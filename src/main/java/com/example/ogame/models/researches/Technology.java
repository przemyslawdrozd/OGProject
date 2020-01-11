package com.example.ogame.models.researches;

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
    private boolean isAbleToBuild;

    public Technology(String name, int lvl, int neededMetal, int neededCristal,
                      int neededDeuterium, String buildTime, boolean isAbleToBuild) {
        this.name = name;
        this.lvl = lvl;
        this.neededMetal = neededMetal;
        this.neededCristal = neededCristal;
        this.neededDeuterium = neededDeuterium;
        this.buildTime = buildTime;
        this.isAbleToBuild = isAbleToBuild;
    }

    public long getDuration() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss",
                    Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse(buildTime).getTime();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private void setNewTime(long newTime) {
        this.buildTime = DurationFormatUtils.formatDurationHMS(newTime);
    }

    public void lvlUpTech() {
        lvl++;
        this.neededMetal *= 2;
        this.neededCristal *= 2;
        this.neededDeuterium *= 2;
        long newTime = getDuration() * 2;
        setNewTime(newTime);
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

    public boolean isAbleToBuild() {
        return isAbleToBuild;
    }

    public void setAbleToBuild(boolean ableToBuild) {
        isAbleToBuild = ableToBuild;
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
