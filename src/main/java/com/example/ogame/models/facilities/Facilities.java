package com.example.ogame.models.facilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Facilities {

    private List<UUID> buildingsIdList;

    public Facilities(UUID facilitiesId, List<Building> buildings) {
        this.buildingsIdList = new ArrayList<>();
        this.buildingsIdList.add(facilitiesId);
        initBuildingsList(buildings);
    }

    private void initBuildingsList(List<Building> buildings) {
        buildings.forEach(building -> buildingsIdList.add(building.getBuildingId()));
    }

    public List<UUID> getBuildingList() {
        return this.buildingsIdList;
    }
}
