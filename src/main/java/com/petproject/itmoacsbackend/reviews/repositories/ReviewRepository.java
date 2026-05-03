package com.petproject.itmoacsbackend.reviews.repositories;

import com.petproject.itmoacsbackend.reviews.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
