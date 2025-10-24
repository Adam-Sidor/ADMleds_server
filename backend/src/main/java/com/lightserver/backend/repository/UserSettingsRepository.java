package com.lightserver.backend.repository;

import com.lightserver.backend.model.UserSetting;
import com.lightserver.backend.model.UserSettingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSetting, UserSettingId> {
    List<UserSetting> findByUser_Username(String username);
    Optional<UserSetting> findByUser_UsernameAndDevice_IpAddress(String username, String ipAddress);
    List<UserSetting> findByUser_UsernameAndDeviceState_StateId(String username, int StateId);
}
