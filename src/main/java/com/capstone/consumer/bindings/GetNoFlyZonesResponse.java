package com.capstone.consumer.bindings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


/**
 * Bindings class used to represent all the no-fly zones existing in the database
 */
@XmlRootElement(
        name = "GetNoFlyZonesResponse"
)
@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(
        localName = "GetNoFlyZonesResponse"
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class GetNoFlyZonesResponse {

    @JsonProperty("ellipsoidNoFlyZones")
    @JacksonXmlProperty(
            localName = "ellipsoidNoFlyZones"
    )
    @XmlElement(
            name = "ellipsoidNoFlyZones"
    )
    public List<EllipsoidNoFlyZone> ellipsoidNoFlyZones;

    @JsonProperty("rectangleNoFlyZones")
    @JacksonXmlProperty(
            localName = "rectangleNoFlyZones"
    )
    @XmlElement(
            name = "rectangleNoFlyZones"
    )
    public List<RectangleNoFlyZone> rectangleNoFlyZones;

    @JsonProperty("polygonNoFlyZones")
    @JacksonXmlProperty(
            localName = "polygonNoFlyZones"
    )
    @XmlElement(
            name = "polygonNoFlyZones"
    )
    public List<PolygonNoFlyZone> polygonNoFlyZones;

    @JsonProperty("militaryNoFlyZones")
    @JacksonXmlProperty(
            localName = "militaryNoFlyZones"
    )
    @XmlElement(
            name = "militaryNoFlyZones"
    )
    public List<MilitaryNoFlyZone> militaryNoFlyZones;

    public List<EllipsoidNoFlyZone> getEllipsoidNoFlyZones() {
        return ellipsoidNoFlyZones;
    }

    public void setEllipsoidNoFlyZones(List<EllipsoidNoFlyZone> ellipsoidNoFlyZones) {
        this.ellipsoidNoFlyZones = ellipsoidNoFlyZones;
    }

    public List<RectangleNoFlyZone> getRectangleNoFlyZones() {
        return rectangleNoFlyZones;
    }

    public void setRectangleNoFlyZones(List<RectangleNoFlyZone> rectangleNoFlyZones) {
        this.rectangleNoFlyZones = rectangleNoFlyZones;
    }

    public List<MilitaryNoFlyZone> getMilitaryNoFlyZones() {
        return militaryNoFlyZones;
    }

    public void setMilitaryNoFlyZones(List<MilitaryNoFlyZone> militaryNoFlyZones) {
        this.militaryNoFlyZones = militaryNoFlyZones;
    }


    public List<PolygonNoFlyZone> getPolygonNoFlyZones() {
        return polygonNoFlyZones;
    }

    @Override
    public String toString() {
        return "GetNoFlyZonesResponse{" +
                "ellipsoidNoFlyZones=" + ellipsoidNoFlyZones.toString() +
                ", rectangleNoFlyZones=" + rectangleNoFlyZones.toString() +
                ", polygonNoFlyZones=" + polygonNoFlyZones.toString() +
                '}';
    }

    public void setPolygonNoFlyZones(List<PolygonNoFlyZone> polygonNoFlyZones) {
        this.polygonNoFlyZones = polygonNoFlyZones;
    }
}
