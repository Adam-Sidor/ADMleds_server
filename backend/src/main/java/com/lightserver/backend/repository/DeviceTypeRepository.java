package com.lightserver.backend.repository;

import com.lightserver.backend.model.DeviceType;
import com.lightserver.backend.model.IoTDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {

}
