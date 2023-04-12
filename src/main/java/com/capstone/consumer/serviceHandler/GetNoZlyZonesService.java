package com.capstone.consumer.serviceHandler;

import com.capstone.consumer.bindings.GetNoFlyZonesResponse;
import com.capstone.consumer.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetNoZlyZonesService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GetNoZlyZonesService.class);

    @Autowired
    private Repository repository;

    public GetNoFlyZonesResponse handle(){
        GetNoFlyZonesResponse response = new GetNoFlyZonesResponse();
        response.setRectangleNoFlyZones(repository.getRectangleNoFlyZones());
        response.setPolygonNoFlyZones(repository.getPolygonNoFlyZones());
        response.setEllipsoidNoFlyZones(repository.getEllipsoidNoFlyZones());

        return response;
    }
}
