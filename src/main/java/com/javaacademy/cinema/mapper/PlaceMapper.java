package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.PlaceDto;
import com.javaacademy.cinema.entity.Place;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {

    public PlaceDto mapToPlaceDto(Place place) {
        return new PlaceDto(
                place.getId(),
                place.getName()
        );
    }
}
