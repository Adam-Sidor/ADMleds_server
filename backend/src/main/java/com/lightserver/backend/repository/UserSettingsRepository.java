package com.lightserver.backend.repository;

import com.lightserver.backend.model.User;
import com.lightserver.backend.model.UserSetting;
import com.lightserver.backend.model.UserSettingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSetting, UserSettingId> {
    List<UserSetting> findByUser_Username(String username);
}
