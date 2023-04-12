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
        name = "TestResponse"
)
@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(
        localName = "TestResponse"
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class TestResponse {

    @JsonProperty("nameField")
    @JacksonXmlProperty(
            localName = "nameField"
    )
    @XmlElement(
            name = "nameField"
    )
    private String nameField;

    public TestResponse(String nameField) {
        this.nameField = nameField;
    }
}
