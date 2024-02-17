package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

/**
 * Bindings class used for representing Flight Location (region) data
 */
@Data
public class GetFlightLocationResponse {
    private String location;

    public GetFlightLocationResponse(String location) {
        this.location = location;
    }
}
