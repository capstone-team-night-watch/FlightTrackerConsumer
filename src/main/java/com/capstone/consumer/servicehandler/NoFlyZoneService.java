package com.capstone.consumer.servicehandler;

import com.capstone.consumer.bindings.BaseNoFlyZone;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoFlyZoneService {

    private final List<BaseNoFlyZone> noFlyZoneStore = Collections.synchronizedList(new ArrayList<BaseNoFlyZone>());

    public void createNoFlyZone(BaseNoFlyZone noFlyZone){
        noFlyZoneStore.removeIf(x -> Objects.equals(x.getId(), noFlyZone.getId()));
        noFlyZoneStore.add(noFlyZone);
    }

    public List<BaseNoFlyZone> getNoFlyZones() {
        return noFlyZoneStore;
    }
}
