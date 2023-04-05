package com.neaktor.usermanager.store.repository;

import com.neaktor.usermanager.store.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
