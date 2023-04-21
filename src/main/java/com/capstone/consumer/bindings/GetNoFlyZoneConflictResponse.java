package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
        name = "GetNoFlyZoneConflictResponse"
)
@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(
        localName = "GetNoFlyZoneConflictResponse"
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class GetNoFlyZoneConflictResponse {

    @JsonProperty("noFlyZoneName")
    @JacksonXmlProperty(
            localName = "noFlyZoneName"
    )
    @XmlElement(
            name = "noFlyZoneName"
    )

    public String noFlyZoneName;

    @JsonProperty("inConflict")
    @JacksonXmlProperty(
            localName = "inConflict"
    )
    @XmlElement(
            name = "inConflict"
    )

    public Boolean inConflict;

    public GetNoFlyZoneConflictResponse(String noFlyZoneName, Boolean inConflict) {
        this.noFlyZoneName = noFlyZoneName;
        this.inConflict = inConflict;
    }

    public String getNoFlyZoneName() {
        return noFlyZoneName;
    }

    public void setNoFlyZoneName(String noFlyZoneName) {
        this.noFlyZoneName = noFlyZoneName;
    }

    public Boolean getInConflict() {
        return inConflict;
    }

    public void setInConflict(Boolean inConflict) {
        this.inConflict = inConflict;
    }

    @Override
    public String toString() {
        return "GetNoFlyZoneConflictResponse{" +
                "name='" + noFlyZoneName + '\'' +
                ", inConflict=" + inConflict +
                '}';
    }
}
