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
        name = "PolygonNoFlyZone"
)
@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(
        localName = "PolygonNoFlyZone"
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class PolygonNoFlyZone {

    @JsonProperty("name")
    @JacksonXmlProperty(
            localName = "name"
    )
    @XmlElement(
            name = "name"
    )
    public String name;

    @JsonProperty("vertex1Long")
    @JacksonXmlProperty(
            localName = "vertex1Long"
    )
    @XmlElement(
            name = "vertex1Long"
    )
    public float vertex1Long;

    @JsonProperty("vertex1Lat")
    @JacksonXmlProperty(
            localName = "vertex1Lat"
    )
    @XmlElement(
            name = "vertex1Lat"
    )
    public float vertex1Lat;

    @JsonProperty("vertex2Long")
    @JacksonXmlProperty(
            localName = "vertex2Long"
    )
    @XmlElement(
            name = "vertex2Long"
    )
    public float vertex2Long;

    @JsonProperty("vertex2Lat")
    @JacksonXmlProperty(
            localName = "vertex2Lat"
    )
    @XmlElement(
            name = "vertex2Lat"
    )
    public float vertex2Lat;

    @JsonProperty("vertex3Long")
    @JacksonXmlProperty(
            localName = "vertex3Long"
    )
    @XmlElement(
            name = "vertex3Long"
    )
    public float vertex3Long;

    @JsonProperty("vertex3Lat")
    @JacksonXmlProperty(
            localName = "vertex3Lat"
    )
    @XmlElement(
            name = "vertex3Lat"
    )
    public float vertex3Lat;

    @JsonProperty("vertex4Long")
    @JacksonXmlProperty(
            localName = "vertex4Long"
    )
    @XmlElement(
            name = "vertex4Long"
    )
    public float vertex4Long;

    @JsonProperty("vertex4Lat")
    @JacksonXmlProperty(
            localName = "vertex4Lat"
    )
    @XmlElement(
            name = "vertex4Lat"
    )
    public float vertex4Lat;

    @JsonProperty("maxAltitude")
    @JacksonXmlProperty(
            localName = "maxAltitude"
    )
    @XmlElement(
            name = "maxAltitude"
    )
    public float maxAltitude;

    @JsonProperty("minAltitude")
    @JacksonXmlProperty(
            localName = "minAltitude"
    )
    @XmlElement(
            name = "minAltitude"
    )
    public float minAltitude;

    public PolygonNoFlyZone(String name, float vertex1Long, float vertex1Lat, float vertex2Long, float vertex2Lat, float vertex3Long, float vertex3Lat, float vertex4Long, float vertex4Lat, float maxAltitude, float minAltitude) {
        this.name = name;
        this.vertex1Long = vertex1Long;
        this.vertex1Lat = vertex1Lat;
        this.vertex2Long = vertex2Long;
        this.vertex2Lat = vertex2Lat;
        this.vertex3Long = vertex3Long;
        this.vertex3Lat = vertex3Lat;
        this.vertex4Long = vertex4Long;
        this.vertex4Lat = vertex4Lat;
        this.maxAltitude = maxAltitude;
        this.minAltitude = minAltitude;
    }




    @Override
    public String toString() {
        return "PolygonNoFlyZone{" +
                "name='" + name + '\'' +
                ", vertex1Long=" + vertex1Long +
                ", vertex1Lat=" + vertex1Lat +
                ", vertex2Long=" + vertex2Long +
                ", vertex2Lat=" + vertex2Lat +
                ", vertex3Long=" + vertex3Long +
                ", vertex3Lat=" + vertex3Lat +
                ", vertex4Long=" + vertex4Long +
                ", vertex4Lat=" + vertex4Lat +
                ", maxAltitude=" + maxAltitude +
                ", minAltitude=" + minAltitude +
                '}';
    }
}
