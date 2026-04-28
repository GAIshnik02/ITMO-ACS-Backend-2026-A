package com.petproject.itmoacsbackend.property.service;

import com.petproject.itmoacsbackend.property.dto.CreatePropertyRequest;
import com.petproject.itmoacsbackend.property.dto.ImageResponse;
import com.petproject.itmoacsbackend.property.dto.PropertyResponse;
import com.petproject.itmoacsbackend.property.entities.AmenityEntity;
import com.petproject.itmoacsbackend.property.entities.PropertyEntity;
import com.petproject.itmoacsbackend.property.entities.PropertyImageEntity;
import com.petproject.itmoacsbackend.property.repositories.AmenityRepository;
import com.petproject.itmoacsbackend.property.repositories.PropertyImageRepository;
import com.petproject.itmoacsbackend.property.repositories.PropertyRepository;
import com.petproject.itmoacsbackend.users.dto.UserShortResponse;
import com.petproject.itmoacsbackend.users.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PropertyService {

    private final AmenityService amenityService;
    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;

    @Transactional
    public PropertyResponse createProperty(CreatePropertyRequest request, UserEntity user) {

        if (!user.getIsLandlord()) {
            throw new SecurityException("Only landlords can create properties");
        }

        PropertyEntity property = PropertyEntity.builder()
                .userId(user)
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .square(request.square())
                .type(request.type())
                .country(request.country())
                .city(request.city())
                .region(request.region())
                .street(request.street())
                .postalCode(request.postalCode())
                .nearestSubway(request.nearestSubway())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .mainImage(request.mainImage())
                .available(request.available() != null ? request.available() : false)
                .build();

        PropertyEntity savedProperty = propertyRepository.save(property);

        if (request.amenities() != null && !request.amenities().isEmpty()) {
            savedProperty.setAmenities(amenityService.findOrCreateAmenities(request.amenities()));
        }

        if (request.imageUrls() != null && !request.imageUrls().isEmpty()) {
            List<PropertyImageEntity> images = request.imageUrls().stream()
                    .map(url -> PropertyImageEntity.builder()
                            .propertyId(savedProperty)
                            .url(url)
                            .build())
                    .toList();

            savedProperty.setPropertyImages(propertyImageRepository.saveAll(images));
        }
        log.info("Created property with id {}", savedProperty.getId());
        return mapToResponse(propertyRepository.save(savedProperty));
    }

    private PropertyResponse mapToResponse(PropertyEntity request) {
        return PropertyResponse.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .square(request.getSquare())
                .type(request.getType())
                .country(request.getCountry())
                .city(request.getCity())
                .street(request.getStreet())
                .postalCode(request.getPostalCode())
                .nearestSubway(request.getNearestSubway())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .mainImage(request.getMainImage())
                .available(request.getAvailable() != null ? request.getAvailable() : false)
                .owner(UserShortResponse.builder()
                            .id(request.getUserId().getId())
                            .username(request.getUserId().getUsername())
                            .email(request.getUserId().getEmail())
                            .phoneNumber(request.getUserId().getPhoneNumber())
                            .firstName(request.getUserId().getFirstName())
                            .lastName(request.getUserId().getLastName())
                            .patronymic(request.getUserId().getPatronymic())
                            .build()
                )
                .images(request.getPropertyImages() != null ?
                        request.getPropertyImages().stream()
                                .map(img -> ImageResponse.builder()
                                        .id(img.getId())
                                        .url(img.getUrl())
                                        .build())
                                .toList() : List.of())
                .amenities(request.getAmenities() != null ?
                        request.getAmenities().stream()
                                .map(AmenityEntity::getName)
                                .toList() :
                        List.of())
                .build();
    }


    public Page<PropertyResponse> getAllProperties(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by("createdAt"));

        Page<PropertyEntity> properties = propertyRepository.findAll(pageable);

        return properties.map(this::mapToResponse);
    }
}
