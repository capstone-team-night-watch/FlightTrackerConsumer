package com.capstone.consumer.messages;

import com.capstone.consumer.bindings.BaseNoFlyZone;
import lombok.Data;

@Data
public class NoFlyZoneCreatedMessage {
    private String noFlyZoneId;

    private String noFlyZoneMessage;

    private BaseNoFlyZone noFlyZone;
}
