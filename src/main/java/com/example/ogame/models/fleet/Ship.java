package com.example.ogame.models.fleet;

import com.example.ogame.utils.facilities.ShipName;
import java.util.UUID;

public class Ship {

    private UUID shipId;
    private final ShipName name;
    private final int attack;
    private final int defense;
    private final int speed;
    private final int capacity;
    private final int fuel;
    private final int metalCost;
    private final int cristalCost;
    private final int deuteriumCost;

    private int amountOfShip;

    public Ship(UUID shipId, ShipName name, int attack, int defense, int speed, int capacity, int fuel,
                int metalCost, int cristalCost, int deuteriumCost, int amountOfShip) {
        this.shipId = shipId;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.capacity = capacity;
        this.fuel = fuel;
        this.metalCost = metalCost;
        this.cristalCost = cristalCost;
        this.deuteriumCost = deuteriumCost;
        this.amountOfShip = amountOfShip;
    }

    public void increaseAmountOfShips(int amountOfShip) {
        this.amountOfShip += amountOfShip;
    }

    public UUID getShipId() {
        return shipId;
    }

    public ShipName getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFuel() {
        return fuel;
    }

    public int getMetalCost() {
        return metalCost;
    }

    public int getCristalCost() {
        return cristalCost;
    }

    public int getDeuteriumCost() {
        return deuteriumCost;
    }

    public int getAmountOfShip() {
        return amountOfShip;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipId=" + shipId +
                ", name=" + name +
                '}';
    }
}
