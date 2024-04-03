package com.capstone.consumer.messages;

import com.capstone.consumer.enums.Rooms;
import com.capstone.consumer.utils.StringUtils;
import lombok.Data;

@Data
public class NoFlyZoneDeletedMessage implements SocketMessageInterface {
    private String noFlyZoneId;

    @Override
    public String getRoom() {
        return Rooms.NO_FLY_ZONE_ROOM;
    }

    @Override
    public String getName() {
        return StringUtils.getMessageName(NoFlyZoneDeletedMessage.class);
    }

    @Override
    public String getMessage() {
        return "No fly zone has been deleted";
    }
}
