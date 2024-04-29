package com.capstone.consumer;

import com.capstone.geocode.ReverseGeoCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ReverseGeoCodeTest {
    @Autowired
    private ReverseGeoCode reverseGeoCoder;

    @Test
    public void ReverseGeoCode_ShouldWork_WhenOmahaLatLong() {
        var nearestPlace = reverseGeoCoder.nearestPlace(51.5072, -0.1276);

        Assert.assertEquals("London", nearestPlace.getName());
    }
}
