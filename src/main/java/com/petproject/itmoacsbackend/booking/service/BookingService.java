package com.petproject.itmoacsbackend.booking.service;

import com.petproject.itmoacsbackend.auth.enums.GlobalRole;
import com.petproject.itmoacsbackend.booking.dto.BookingCreateRequest;
import com.petproject.itmoacsbackend.booking.dto.BookingResponse;
import com.petproject.itmoacsbackend.booking.entities.BookingEntity;
import com.petproject.itmoacsbackend.booking.enums.BookingStatus;
import com.petproject.itmoacsbackend.booking.repositories.BookingRepository;
import com.petproject.itmoacsbackend.property.entities.PropertyEntity;
import com.petproject.itmoacsbackend.property.repositories.PropertyRepository;
import com.petproject.itmoacsbackend.users.entities.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;

    @Transactional
    public BookingResponse createBooking(
            Long propertyId,
            BookingCreateRequest request,
            UserEntity user
    ) {
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException("No such entity with id:" + propertyId)
        );

        var bookingToCreate = BookingEntity.builder()
                .renterId(user)
                .propertyId(property)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .bookingStatus(BookingStatus.PENDING)
                .totalPrice(request.totalPrice())
                .guestsCount(request.guestsCount())
                .details(request.details())
                .build();

        var savedBooking = bookingRepository.save(bookingToCreate);

        return mapToResponse(savedBooking);
    }

    public Page<BookingResponse> getAllBookings(
            Long propertyId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());

        Page<BookingEntity> entities =  bookingRepository.findByPropertyIdAll(propertyId, pageable);

        return entities.map(this::mapToResponse);
    }

    public BookingResponse getBookingById(Long propertyId, Long bookingId, UserEntity user) {
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new EntityNotFoundException("No such entity with id:" + propertyId)
        );
        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new EntityNotFoundException("No such booking with id:" + bookingId)
        );

        if (!user.getId().equals(booking.getRenterId().getId()) && !user.getGlobalRole().equals(GlobalRole.ADMIN)) {
            throw new SecurityException("You are not allowed to perform this action");
        }

        return mapToResponse(booking);
    }


    private BookingResponse mapToResponse(BookingEntity booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .renterId(booking.getRenterId().getId())
                .propertyId(booking.getPropertyId().getId())
                .paymentId(booking.getPaymentId().getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .status(booking.getBookingStatus())
                .totalPrice(booking.getTotalPrice())
                .guestsCount(booking.getGuestsCount())
                .details(booking.getDetails())
                .createdAt(booking.getCreatedAt())
                .build();
    }

    @Transactional
    public BookingResponse cancelBooking(Long id, UserEntity user) {
        BookingEntity booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No such booking with id:" + id)
        );

        if (!user.getId().equals(booking.getRenterId().getId()) && !user.getGlobalRole().equals(GlobalRole.ADMIN)) {
            throw new SecurityException("You are not allowed to perform this action");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        var saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    @Transactional
    public BookingResponse confirmBooking(Long id, UserEntity user) {
        BookingEntity booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No such booking with id:" + id)
        );

        PropertyEntity property = propertyRepository.findById(booking.getPropertyId().getId()).orElseThrow(
                () -> new EntityNotFoundException("No such property with id:" + booking.getPropertyId())
        );

        if (!user.getId().equals(property.getUserId().getId()) && !user.getGlobalRole().equals(GlobalRole.ADMIN)) {
            throw new SecurityException("You are not allowed to perform this action");
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        var saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    @Transactional
    public BookingResponse rejectBooking(Long id, UserEntity user) {
        BookingEntity booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No such booking with id:" + id)
        );

        PropertyEntity property = propertyRepository.findById(booking.getPropertyId().getId()).orElseThrow(
                () -> new EntityNotFoundException("No such property with id:" + booking.getPropertyId())
        );

        if (!user.getId().equals(property.getUserId().getId()) && !user.getGlobalRole().equals(GlobalRole.ADMIN)) {
            throw new SecurityException("You are not allowed to perform this action");
        }
        booking.setBookingStatus(BookingStatus.REJECTED);
        var saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    public BookingResponse completeBooking(Long id, UserEntity user) {
        BookingEntity booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No such booking with id:" + id)
        );

        PropertyEntity property = propertyRepository.findById(booking.getPropertyId().getId()).orElseThrow(
                () -> new EntityNotFoundException("No such property with id:" + booking.getPropertyId())
        );

        if (!user.getId().equals(property.getUserId().getId()) && !user.getGlobalRole().equals(GlobalRole.ADMIN)) {
            throw new SecurityException("You are not allowed to perform this action");
        }

        if (booking.getEndDate().isAfter(LocalDate.now())) {
            throw new SecurityException("You can't complete booking before end date, if you want, connect administration");
        }

        booking.setBookingStatus(BookingStatus.COMPLETED);
        var saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }
}
