package com.javaacademy.cinema.service.user.imp;

import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.entity.Ticket;
import com.javaacademy.cinema.exception.NotFoundEntityException;
import com.javaacademy.cinema.exception.TickedAlreadySoldException;
import com.javaacademy.cinema.mapper.TicketMapper;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import com.javaacademy.cinema.service.user.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketDto buy(TicketBookingDto dto) {
        Optional<Place> currentPlace = placeRepository.findByName(dto.getPlaceName());
        if (currentPlace.isEmpty()) {
            throw new NotFoundEntityException("Места с именем = %s не найдено".formatted(dto.getPlaceName()));
        }
        Optional<Ticket> ticket = ticketRepository.findBySessionByPlace(
                dto.getSessionId(),
                currentPlace.get().getId()
        );
        if (ticket.isEmpty()) {
            throw new NotFoundEntityException("Билет на сеанс с id = %s и место = %s не найден"
                    .formatted(dto.getSessionId(), dto.getPlaceName())
            );
        }
        Ticket currentTicket = ticket.get();
        if (currentTicket.getIsSold()) {
            throw new TickedAlreadySoldException("Билет c id = %s уже продан".formatted(currentTicket.getId()));
        }
        Ticket soldTicket = ticketRepository.buy(currentTicket.getId());
        return ticketMapper.mapToTicketDto(soldTicket);
    }
}
