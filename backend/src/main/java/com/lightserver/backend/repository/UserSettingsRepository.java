package com.lightserver.backend.repository;

import com.lightserver.backend.model.User;
import com.lightserver.backend.model.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSetting, Integer> {
    //Optional<UserSetting> findByUser(String user);
}
