package com.igromatic.lunches.repository;

import com.igromatic.lunches.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName (String name);

    UserEntity findByChatId(Long chatId);

    List<UserEntity> findAllByStatus(String status);

    UserEntity findByStatus(String status);
}
