package com.capstone.consumer.repositories;

import com.capstone.consumer.entities.FlightInformationEntity;
import org.springframework.data.repository.CrudRepository;

interface FlightInformationRepository extends CrudRepository<FlightInformationEntity, Long> {
}
