package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bindings class used for representing Military no-fly zone data
 */
@XmlRootElement(
        name = "MilitaryNoFlyZone"
)
@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(
        localName = "MilitaryNoFlyZone"
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class MilitaryNoFlyZone {

    @JsonProperty("name")
    @JacksonXmlProperty(
            localName = "name"
    )
    @XmlElement(
            name = "name"
    )
    private String name;

    @JsonProperty("geoJson")
    @JacksonXmlProperty(
            localName = "geoJson"
    )
    @XmlElement(
            name = "geoJson"
    )
    private String geoJson;

    public MilitaryNoFlyZone() {}

    public MilitaryNoFlyZone(String name, String geoJson) {
        this.name = name;
        this.geoJson = geoJson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }
}
