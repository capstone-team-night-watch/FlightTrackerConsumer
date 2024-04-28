package com.capstone.consumer.messages;


import com.capstone.consumer.enums.Rooms;
import com.capstone.consumer.utils.StringUtils;
import com.capstone.shared.bindings.BaseNoFlyZone;
import lombok.Data;

@Data
public class NoFlyZoneCreatedMessage implements SocketMessageInterface {
    private BaseNoFlyZone noFlyZone;

    public NoFlyZoneCreatedMessage(BaseNoFlyZone noFlyZone) {
        this.noFlyZone = noFlyZone;
    }

    @Override
    public String getRoom() {
        return Rooms.NO_FLY_ZONE_ROOM;
    }

    @Override
    public String getName() {
        return StringUtils.getMessageName(NoFlyZoneCreatedMessage.class);
    }

    @Override
    public String getMessage() {
        return "New no fly zone has been created";
    }
}
