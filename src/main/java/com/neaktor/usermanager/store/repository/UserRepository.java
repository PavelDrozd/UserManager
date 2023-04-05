package com.neaktor.usermanager.store.repository;

import com.neaktor.usermanager.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
