package com.capstone.consumer.messages;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ExitRoomPayload {
    private ArrayList<String> rooms;
}
