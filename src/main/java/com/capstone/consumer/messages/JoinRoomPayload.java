package com.capstone.consumer.messages;

import lombok.Data;

import java.util.ArrayList;

@Data
public class JoinRoomPayload {
    private ArrayList<String> rooms;
}
