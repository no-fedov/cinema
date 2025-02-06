package com.javaacademy.cinema.service.user.imp;

import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.entity.Ticket;
import com.javaacademy.cinema.mapper.TicketMapper;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import com.javaacademy.cinema.service.user.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final PlaceRepository placeRepository;
    private final SessionRepository sessionRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketDto buyTicket(TicketBookingDto dto) {
        Optional<Place> currentPlace = placeRepository.findByName(dto.getPlaceName());
        if (currentPlace.isEmpty()) {
            throw new RuntimeException();
        }
        Optional<Ticket> ticket = ticketRepository.findIdBySessionByPlace(
                dto.getSessionId(),
                currentPlace.get().getId()
        );
        if (ticket.isEmpty()) {
            throw new RuntimeException("Билета нет");
        }
        Ticket currentTicket = ticket.get();
        if (currentTicket.getIsSold()) {
            throw new RuntimeException("Билет уже продан");
        }
        Ticket soldTicket = ticketRepository.buy(currentTicket.getId());
        return ticketMapper.mapToTicketDto(soldTicket);
    }
}
