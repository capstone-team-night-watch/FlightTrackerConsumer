package com.capstone.consumer.servicehandler;


import com.capstone.shared.bindings.BaseNoFlyZone;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class NoFlyZoneService {

    private final List<BaseNoFlyZone> noFlyZoneStore = Collections.synchronizedList(new ArrayList<BaseNoFlyZone>());

    public void createNoFlyZone(BaseNoFlyZone noFlyZone) {
        noFlyZoneStore.removeIf(x -> Objects.equals(x.getId(), noFlyZone.getId()));
        noFlyZoneStore.add(noFlyZone);
    }

    public List<BaseNoFlyZone> getNoFlyZones() {
        return noFlyZoneStore;
    }
}
