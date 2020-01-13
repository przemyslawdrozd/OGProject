package com.example.ogame.tools;

import com.example.ogame.datasource.FacilitiesDataAccess;
import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.models.facilities.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BuildingScheduler {
    private Logger logger = LoggerFactory.getLogger(BuildingScheduler.class);

    private FacilitiesDataAccess facilitiesDataAccess;
    private UserDataAccess userDataAccess;

    public BuildingScheduler(FacilitiesDataAccess facilitiesDataAccess, UserDataAccess userDataAccess) {
        this.facilitiesDataAccess = facilitiesDataAccess;
        this.userDataAccess = userDataAccess;
    }

    @Scheduled(fixedDelay = 1000)
    public void researching() {
        final List<UUID> users = userDataAccess.selectAllUsersUUID();

        users.stream().skip(1).forEach(userId -> {
            List<Building> facilities = facilitiesDataAccess.selectFacilities(userId);
            facilities.forEach(b -> {
                if (b.getIsAbleToBuild() == 2) {
                    if (b.countDown()) {
                        facilitiesDataAccess.updateCountDown(b);
                    } else {
                        b.lvlUpBuilding();
                        facilitiesDataAccess.updateBuilding(b);
                        unblockTech(userId);
                    }
                }
            });
        });
    }

    private void unblockTech(UUID userId) {
        List<Building> facilities = facilitiesDataAccess.selectFacilities(userId);
        facilities.forEach(b -> facilitiesDataAccess.unblockBuilding(b.getBuildingId()));
    }
}
