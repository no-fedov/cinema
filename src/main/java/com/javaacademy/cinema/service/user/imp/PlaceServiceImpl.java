package com.javaacademy.cinema.service.user.imp;

import com.javaacademy.cinema.dto.PlaceDto;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.exception.NotFoundEntityException;
import com.javaacademy.cinema.mapper.PlaceMapper;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.service.user.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final SessionRepository sessionRepository;
    private final PlaceMapper placeMapper;

    @Override
    public List<PlaceDto> getFreeBySessionId(Integer sessionId) {
        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundEntityException("Сеанс с id = %s не найден".formatted(sessionId)));
        List<Place> places = placeRepository.findFreeBySessionId(sessionId);
        log.info("Найдены свободные места для сеанса с id = {} : {}", sessionId, places);
        return placeMapper.mapToPlaceDto(places);
    }
}
