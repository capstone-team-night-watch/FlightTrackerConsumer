package com.capstone.consumer.messages;


import com.capstone.shared.bindings.BaseNoFlyZone;
import com.capstone.shared.enums.NoFlyZoneType;
import lombok.Data;

@Data
public class NoFlyZoneCreatedMessage {
    private NoFlyZoneType type;

    private String noFlyZoneId;

    private String noFlyZoneMessage;

    private BaseNoFlyZone noFlyZone;
}
