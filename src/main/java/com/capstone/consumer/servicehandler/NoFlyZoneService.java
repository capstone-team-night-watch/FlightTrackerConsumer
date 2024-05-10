package com.capstone.consumer.servicehandler;


import com.capstone.shared.bindings.BaseNoFlyZone;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Service to store and retrieve no-fly-zones
 */
@Service
public class NoFlyZoneService {

    private final List<BaseNoFlyZone> noFlyZoneStore = Collections.synchronizedList(new ArrayList<BaseNoFlyZone>());

    /**
     * Stores a no-fly-zone
     * @param noFlyZone the no-fly-zone to store
     */
    public void storeNoFlyZone(BaseNoFlyZone noFlyZone) {
        noFlyZoneStore.removeIf(x -> Objects.equals(x.getId(), noFlyZone.getId()));
        noFlyZoneStore.add(noFlyZone);
    }

    /**
     * Retrieves all no-fly-zones
     * @return a list of all no-fly-zones
     */
    public List<BaseNoFlyZone> getNoFlyZones() {
        return noFlyZoneStore;
    }
}
