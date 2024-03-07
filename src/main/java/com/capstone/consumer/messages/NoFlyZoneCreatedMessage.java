package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.BaseNoFlyZone;
import com.capstone.consumer.enums.NoFlyZoneType;
import lombok.Data;

@Data
public class NoFlyZoneCreatedMessage {
    private NoFlyZoneType type;

    private String noFlyZoneId;

    private String noFlyZoneMessage;

    private BaseNoFlyZone noFlyZone;
}
