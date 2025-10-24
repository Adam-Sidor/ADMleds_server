package com.lightserver.backend.repository;

import com.lightserver.backend.model.DeviceType;
import com.lightserver.backend.model.IoTDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {

}
