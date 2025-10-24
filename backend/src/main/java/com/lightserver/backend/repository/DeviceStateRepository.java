package com.lightserver.backend.repository;

import com.lightserver.backend.model.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DeviceStateRepository extends JpaRepository<DeviceState, Integer> {
    Optional<DeviceState> findByStateName(String stateName);
}
