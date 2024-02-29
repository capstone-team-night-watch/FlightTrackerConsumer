package com.capstone.consumer.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.capstone.consumer.bindings.TfrNotam;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Service Handler class that facilitates adding new TFR to the repository
 */
@Component
public class TfrBean {
    /**
     * The Repository Object that facilitates the database interaction
     */
    private static final List<TfrNotam> allCurrentTfr = new ArrayList<TfrNotam>();

    /**
     * Handles making the necessary Repository call to add a TFR
     *
     * @param tfr The TFR Notam Object that will be used
     */
    public static void addNewTFR(TfrNotam tfr) {
        allCurrentTfr.add(tfr);
    }

    /**
     * Returns the current JSON object of the current TFRs
     */
    public static List<TfrNotam> getAllTfr() {
        return allCurrentTfr;
    }
}
