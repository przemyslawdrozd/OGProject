package com.example.ogame.models.research;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Research {

    List<UUID> researchIdList;
    List<Technology> researchList;

    public Research(UUID researchId, List<Technology> research) {
        this.researchList = research;
        this.researchIdList = new ArrayList<>();
        this.researchIdList.add(researchId);
        initResearchList();
    }

    private void initResearchList() {
        this.researchList.forEach(tech -> researchIdList.add(tech.getTechId()));
    }

    public List<UUID> getResearchIdList() {
        return researchIdList;
    }

    public List<Technology> getTechList() {
        return researchList;
    }
}
