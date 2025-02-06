package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.TicketAdminDto;
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

    public TicketAdminDto mapToTicketAdminDto(Ticket ticket) {
        return new TicketAdminDto(
                ticket.getId(),
                sessionMapper.mapToSessionAdminDto(ticket.getSession()),
                placeMapper.mapToPlaceAdminDto(ticket.getPlace()),
                ticket.getIsSold()
        );
    }

    public List<TicketAdminDto> mapToTicketDtoList(List<Ticket> tickets) {
        return tickets.stream().map(this::mapToTicketAdminDto).toList();
    }

    public TicketDto mapToTicketDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                placeMapper.mapToPlaceDto(ticket.getPlace()).toString(),
                ticket.getSession().getMovie().getName(),
                ticket.getSession().getDateTime()
        );
    }
}
