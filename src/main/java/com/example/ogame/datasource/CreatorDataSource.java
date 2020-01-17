package com.example.ogame.datasource;

import com.example.ogame.exeptions.ApiRequestException;
import com.example.ogame.models.Planet;
import com.example.ogame.models.User;
import com.example.ogame.models.facilities.Building;
import com.example.ogame.models.facilities.Facilities;
import com.example.ogame.models.fleet.Fleet;
import com.example.ogame.models.fleet.Ship;
import com.example.ogame.models.research.Research;
import com.example.ogame.models.research.Technology;
import com.example.ogame.utils.facilities.FacilitiesHelper;
import com.example.ogame.utils.fleet.FleetHelper;
import com.example.ogame.utils.galaxy.GalaxyHelper;
import com.example.ogame.utils.research.ResearchHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

import static com.example.ogame.utils.resources.ResourcesHelper.*;

@Repository
public class CreatorDataSource {
    private Logger logger = LoggerFactory.getLogger(CreatorDataSource.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreatorDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertResources(UUID resId) {
        final String sql = "INSERT INTO resources (" +
                "resource_id, metal, cristal, deuterium) " +
                "VALUES (?, ?, ?, ?)";
        logger.info("insertNewResourcesToNewUser - " + sql);
        jdbcTemplate.update(sql, insertNewResources(createResources(resId)));
    }

    public void insertUser(UUID userId, User newUser) {
        final String sql = "INSERT INTO users (user_id, username, password, email) VALUES (?, ?, ?, ?)";
        logger.info("insertUser - " + sql);
        jdbcTemplate.update(sql, userId,
                newUser.getUsername(),
                newUser.getPassword(),
                newUser.getEmail());
    }

    public void insertNewInstance(UUID userId, UUID resourceId, UUID facilitiesId, UUID fleetId, UUID researchId, UUID planetId) {
        final String sql = "INSERT INTO user_instance (user_id, resource_id, facilities_id, fleet_id, research_id, planet_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        logger.info("insertNewInstance = " + sql);
        jdbcTemplate.update(sql, userId, resourceId, facilitiesId, fleetId, researchId, planetId);
    }

    public void insertResearch(UUID researchId) {
        Research research = new Research(researchId, ResearchHelper.createResearch());
        research.getTechList().forEach(this::insertTech);
        logger.info("Research created");

        final String sql = "INSERT INTO research VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insert Research");
        jdbcTemplate.update(sql, ResearchHelper.insertResearch(research.getResearchIdList()));
    }

    private void insertTech(Technology tech) {
        final String sql = "INSERT INTO technology VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insert tech - " + tech.toString());
        jdbcTemplate.update(sql, ResearchHelper.insertTech(tech));
    }

    public void insertFleet(UUID fleet_id) {
        List<Ship> ships = FleetHelper.createShips();
        Fleet fleet = new Fleet(fleet_id, ships);
        ships.forEach(this::insertNewShip);
        logger.info("Ships inserted");

        final String sql = "INSERT INTO fleet (" +
                "fleet_id, small_cargo_ship, large_cargo_ship, light_fighter, battle_ship, colony_ship)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, FleetHelper.insertFleet(fleet.getShipList()));
        logger.info("Fleet inserted");
    }

    public void insertNewShip(Ship ship) {
        final String sql = "INSERT INTO ship (" +
                "ship_id, ship_name, attack, defense, speed, capacity, fuel, metal_cost, cristal_cost, deuterium_cost, amount_of_ship)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insertNewShip - " + sql);
        jdbcTemplate.update(sql, FleetHelper.insertShip(ship));
    }

    public void insertFacilities(UUID facilitiesId) {
        List<Building> buildingList = FacilitiesHelper.createFacilities();
        Facilities facilities = new Facilities(facilitiesId, buildingList);
        buildingList.forEach(this::insertNewBuilding);
        logger.info("Buildings inserted");

        final String sql = "INSERT INTO facilities VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, FacilitiesHelper.insertFacilities(facilities.getBuildingList()));
    }

    public void insertNewBuilding(Building building) {
        final String sql = "INSERT INTO building VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("insertNewBuilding - " + sql);
        jdbcTemplate.update(sql, FacilitiesHelper.insertBuilding(building));
    }

    public void insertPlanet(UUID planetId) {
        Planet planet = createPlanetPosition(planetId);
        final String sql = "INSERT INTO planet VALUES " +
                "(?, ?, ?, ?)";
        logger.info("insert planet");
        jdbcTemplate.update(sql, GalaxyHelper.insertPlanet(planetId, planet));
    }

    public boolean ifCoordinatesExists(int galaxyPosition, int planetarySystem, int planetPosition) {
        final String sql = "SELECT EXISTS ( SELECT 1 FROM planet WHERE " +
                "galaxy_position = ? AND planetary_system = ? AND planet_position = ? )";
        return jdbcTemplate.queryForObject(sql, new Object[] {galaxyPosition, planetarySystem, planetPosition},
                (rs, i) -> rs.getBoolean(1));
    }

    private Planet createPlanetPosition(UUID planetId) {
        for (int planetSystem = 1; planetSystem < 4; planetSystem++) {
            for (int planetPosition = 1; planetPosition < 11; planetPosition++) {
                if (!ifCoordinatesExists(1, planetSystem, planetPosition)){
                    logger.info("Create new planet position");
                    return new Planet(planetId, 1, planetSystem, planetPosition);
                }
            }
        }
        logger.error("Creating planet position failed!");
        throw new ApiRequestException("There is no place to create planet!");
    }
}
