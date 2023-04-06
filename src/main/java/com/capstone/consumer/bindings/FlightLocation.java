package com.capstone.consumer.bindings;

public class FlightLocation {
    private String stateName;

    @Override
    public String toString() {
        return "FlightLocationBindings{" +
                "stateName='" + stateName + '\'' +
                '}';
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }



}
