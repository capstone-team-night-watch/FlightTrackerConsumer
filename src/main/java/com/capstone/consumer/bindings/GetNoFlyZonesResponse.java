package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


/**
 * Bindings class used to represent all the no-fly zones existing in the database
 */
@Data
public class GetNoFlyZonesResponse {
    private List<EllipsoidNoFlyZone> ellipsoidNoFlyZones;
    private List<RectangleNoFlyZone> rectangleNoFlyZones;
    private List<PolygonNoFlyZone> polygonNoFlyZones;
    private List<MilitaryNoFlyZone> militaryNoFlyZones;
}
