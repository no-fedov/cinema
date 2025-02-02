package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.TicketAdminDto;
import com.javaacademy.cinema.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketMapper {

    private final SessionMapper sessionMapper;
    private final PlaceMapper placeMapper;

    public TicketAdminDto mapToTicketDto(Ticket ticket) {
        return new TicketAdminDto(
                ticket.getId(),
                sessionMapper.mapToSessionDto(ticket.getSession()),
                placeMapper.mapToPlaceDto(ticket.getPlace()),
                ticket.getIsSold()
        );
    }

    public List<TicketAdminDto> mapToTicketDtoList(List<Ticket> tickets) {
        return tickets.stream().map(this::mapToTicketDto).toList();
    }
}
