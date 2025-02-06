package com.javaacademy.cinema.service.user.imp;

import com.javaacademy.cinema.dto.PlaceDto;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.mapper.PlaceMapper;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.service.user.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    @Override
    public List<PlaceDto> getFreeBySessionId(Integer sessionId) {
        List<Place> places = placeRepository.findFreeBySessionId(sessionId);
        return placeMapper.mapToPlaceDto(places);
    }
}
