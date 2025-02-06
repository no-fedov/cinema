package com.javaacademy.cinema.service.user;

import com.javaacademy.cinema.dto.PlaceDto;

import java.util.List;

public interface PlaceService {

    List<PlaceDto> getFreeBySessionId(Integer sessionId);
}
