package com.example.ogame.tools;

import com.example.ogame.datasource.FacilitiesDataAccess;
import com.example.ogame.datasource.ResourceDataAccess;
import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.models.Resources;
import com.example.ogame.models.facilities.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ResourceThread implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(ResourceThread.class);

    private final ResourceDataAccess resourceDataAccess;
    private final UserDataAccess userDataAccess;
    private final FacilitiesDataAccess facilitiesDataAccess;

    @Autowired
    public ResourceThread(ResourceDataAccess resourceDataAccess,
                          UserDataAccess userDataAccess,
                          FacilitiesDataAccess facilitiesDataAccess) {
        this.resourceDataAccess = resourceDataAccess;
        this.userDataAccess = userDataAccess;
        this.facilitiesDataAccess = facilitiesDataAccess;
    }

    /**
     * At first creates and executes new thread to init update resources process
     * After first loop check occurs if new user has appear
     * @param contextRefreshedEvent starts event
     */
    @Override
    public synchronized void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Init onApplicationEvent - update resources");
        ExecutorService exec = Executors.newFixedThreadPool(1);
        exec.submit(() -> {
            boolean startFlag = true;
            int initUsers = 0;
            do {
                final List<UUID> listOfUserIds = userDataAccess.selectAllUsersUUID();
//                logger.info("Number of users: " + listOfUserIds.size());
                int amountOfUsers = listOfUserIds.size();
                if (startFlag && amountOfUsers > 1) { // init start
                    ExecutorService executorService = Executors.newFixedThreadPool(amountOfUsers);
                    for (UUID user_id : listOfUserIds) {
                        executorService.submit(() -> singleUpdateResThread(user_id));
                    }
                    startFlag = false;
                    initUsers = amountOfUsers;
                } else if (amountOfUsers != initUsers) { // if there is other amount of users
                    final UUID newUUID = listOfUserIds.get(listOfUserIds.size() - 1);
                    Executors.newFixedThreadPool(1).submit(() -> singleUpdateResThread(newUUID));
                    initUsers = amountOfUsers;
                }
                Thread.sleep(2000);
            } while (true);

        });
    }

    /**
     * This method retrieves users's resources and production per hour from every resources building
     * Next increase values of resources depend of Mines' levels
     * Updated Resources update in database
     * @param userId user's id
     */
    private void singleUpdateResThread(UUID userId) {
        while (true) {
            Resources resources = resourceDataAccess.selectResourcesByUserId(userId);
            Building metal = facilitiesDataAccess.selectBuilding(userId, "METAL_MINE");
            Building cristal =  facilitiesDataAccess.selectBuilding(userId, "CRISTAL_MINE");
            Building deuterium = facilitiesDataAccess.selectBuilding(userId, "DEUTERIUM_MINE");

            resources.updatePerSec(
                    metal.getProductionPerHour(),
                    cristal.getProductionPerHour(),
                    deuterium.getProductionPerHour()
            );
            resourceDataAccess.updateResources(resources);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}