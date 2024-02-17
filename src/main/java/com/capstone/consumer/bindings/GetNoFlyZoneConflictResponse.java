package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bindings class used for representing data relating to Flight no-fly zone conflicts
 */


@Data
public class GetNoFlyZoneConflictResponse {
    private String noFlyZoneName;
    private Boolean inConflict;

    public GetNoFlyZoneConflictResponse(String noFlyZoneName, Boolean inConflict) {
        this.noFlyZoneName = noFlyZoneName;
        this.inConflict = inConflict;
    }
}
