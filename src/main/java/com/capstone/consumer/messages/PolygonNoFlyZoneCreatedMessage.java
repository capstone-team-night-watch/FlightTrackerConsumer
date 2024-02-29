package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.BaseNoFlyZone;
import lombok.Data;

@Data
public class PolygonNoFlyZoneCreatedMessage {
    private String notamNumber;

    private String noFlyZoneId;

    private String noFlyZoneMessage;

    private BaseNoFlyZone noFlyZone;
}
