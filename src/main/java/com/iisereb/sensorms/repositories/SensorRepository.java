package com.iisereb.sensorms.repositories;

import com.iisereb.sensorms.entities.SensorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<SensorEntity, Long> {

}
