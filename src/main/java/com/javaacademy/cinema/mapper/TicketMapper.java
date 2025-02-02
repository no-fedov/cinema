package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketMapper {

    private final SessionMapper sessionMapper;
    private final PlaceMapper placeMapper;

    public TicketDto mapToTicketDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                sessionMapper.mapToSessionDto(ticket.getSession()),
                placeMapper.mapToPlaceDto(ticket.getPlace()),
                ticket.getIsSold()
        );
    }

    public List<TicketDto> mapToTicketDtoList(List<Ticket> tickets) {
        return tickets.stream().map(this::mapToTicketDto).toList();
    }
}
