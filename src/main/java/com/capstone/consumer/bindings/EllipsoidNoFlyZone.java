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
        name = "EllipsoidNoFlyZone"
)
@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(
        localName = "EllipsoidNoFlyZone"
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class EllipsoidNoFlyZone {

    @JsonProperty("name")
    @JacksonXmlProperty(
            localName = "name"
    )
    @XmlElement(
            name = "name"
    )
    public String name;

    @JsonProperty("longitude")
    @JacksonXmlProperty(
            localName = "longitude"
    )
    @XmlElement(
            name = "longitude"
    )
    public float longitude;

    @JsonProperty("latitude")
    @JacksonXmlProperty(
            localName = "latitude"
    )
    @XmlElement(
            name = "latitude"
    )
    public float latitude;

    @JsonProperty("altitude")
    @JacksonXmlProperty(
            localName = "altitude"
    )
    @XmlElement(
            name = "altitude"
    )
    public float altitude;

    @JsonProperty("longRadius")
    @JacksonXmlProperty(
            localName = "longRadius"
    )
    @XmlElement(
            name = "longRadius"
    )
    public float longRadius;

    @JsonProperty("latRadius")
    @JacksonXmlProperty(
            localName = "latRadius"
    )
    @XmlElement(
            name = "latRadius"
    )
    public float latRadius;

    @JsonProperty("altRadius")
    @JacksonXmlProperty(
            localName = "altRadius"
    )
    @XmlElement(
            name = "altRadius"
    )
    public float altRadius;

    public EllipsoidNoFlyZone(String name, float longitude, float latitude, float altitude, float longRadius, float latRadius, float altRadius) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.longRadius = longRadius;
        this.latRadius = latRadius;
        this.altRadius = altRadius;
    }

    public EllipsoidNoFlyZone() {}

    @Override
    public String toString() {
        return "EllipsoidNoFlyZone{" +
                "name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", altitude=" + altitude +
                ", longRadius=" + longRadius +
                ", latRadius=" + latRadius +
                ", altRadius=" + altRadius +
                '}';
    }
}
