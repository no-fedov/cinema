package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.PlaceAdminDto;
import com.javaacademy.cinema.dto.PlaceDto;
import com.javaacademy.cinema.entity.Place;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceMapper {

    public PlaceAdminDto mapToPlaceAdminDto(Place place) {
        return new PlaceAdminDto(
                place.getId(),
                place.getName()
        );
    }

    public PlaceDto mapToPlaceDto(Place place) {
        return new PlaceDto(place.getName());
    }

    public List<PlaceDto> mapToPlaceDto(List<Place> places) {
        return places.stream()
                .map(this::mapToPlaceDto)
                .toList();
    }
}
