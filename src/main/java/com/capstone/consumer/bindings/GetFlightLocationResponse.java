package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlElement;

/**
 * Bindings class used for representing Flight Location (region) data
 */
public class GetFlightLocationResponse {

    @JsonProperty("location")
    @JacksonXmlProperty(
            localName = "location"
    )
    @XmlElement(
            name = "location"
    )

    public String location;

    public GetFlightLocationResponse(String location) {
        this.location = location;
    }
}
