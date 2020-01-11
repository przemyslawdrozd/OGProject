package com.example.ogame.models.researches;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Research {

    List<UUID> researchesIdList;

    public Research(UUID researchId, List<Technology> researches) {
        this.researchesIdList = new ArrayList<>();
        this.researchesIdList.add(researchId);
        initResearchesList(researches);
    }

    private void initResearchesList(List<Technology> researches) {
        researches.stream()
                .peek(tech -> tech.setTechId(UUID.randomUUID()))
                .forEach(tech -> researchesIdList.add(tech.getTechId()));
    }

    public List<UUID> getResearchesIdList() {
        return researchesIdList;
    }
}
