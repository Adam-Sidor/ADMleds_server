package com.lightserver.backend.repository;

import com.lightserver.backend.model.IoTDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IoTDeviceRepository extends JpaRepository<IoTDevice, Integer> {
    Optional<IoTDevice> findByIpAddress(String ipAddress);
}
