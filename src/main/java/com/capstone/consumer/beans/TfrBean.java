package com.capstone.consumer.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capstone.consumer.bindings.TfrNotam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service Handler class that facilitates adding new TFR to the repository
 */
@Component
public class TfrBean {
    /**
     * The Repository Object that facilitates the database interaction
     */
    private static List<TfrNotam> allCurrentTfr = new ArrayList<TfrNotam>();
    private static String json;

    /**
     * Handles making the necessary Repository call to add a TFR
     *
     * @param tfr The TFR Notam Object that will be used
     * @throws JsonProcessingException 
     */
    public static void addNewTFR(TfrNotam tfr) throws JsonProcessingException {
        allCurrentTfr.add(tfr);
        updateJson(); // Update based on modification instead of create on call
    }

    /**
     * Returns the current JSON object of the current TFRs
     */
    public static String getJsonString() {
        return json;
    }

    private static void updateJson() throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(allCurrentTfr);
    }
}
