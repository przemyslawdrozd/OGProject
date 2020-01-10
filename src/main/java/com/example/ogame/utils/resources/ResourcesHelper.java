package com.example.ogame.utils.resources;

import com.example.ogame.models.Resources;

import java.util.UUID;

public class ResourcesHelper {

    public static Resources createResources(UUID resId) {
        return new Resources(resId, 500, 500, 0);
    }

    public static Object[] insertNewResources(Resources res) {
        return new Object[] {
                res.getResourceId(),
                res.getMetal(),
                res.getCristal(),
                res.getDeuterium()
        };
    }
}
