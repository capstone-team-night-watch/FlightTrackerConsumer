package com.capstone.consumer.bindings;

import lombok.Data;

@Data
public class SuccessResponse<T> {
    private T data;
    private boolean success = true;
    private String message = "Success";

    public SuccessResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public SuccessResponse(T data) {
        this.data = data;
    }
}
