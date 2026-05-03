package com.petproject.itmoacsbackend.booking.repositories;

import com.petproject.itmoacsbackend.booking.entities.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    Page<BookingEntity> findByPropertyIdAll(Long propertyId, Pageable pageable);
}
