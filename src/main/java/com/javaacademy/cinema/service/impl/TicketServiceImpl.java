package com.javaacademy.cinema.service.impl;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.TicketAdminDto;
import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.entity.Ticket;
import com.javaacademy.cinema.exception.NotFoundEntityException;
import com.javaacademy.cinema.exception.TickedAlreadySoldException;
import com.javaacademy.cinema.mapper.SessionMapper;
import com.javaacademy.cinema.mapper.TicketMapper;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import com.javaacademy.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final SessionMapper sessionMapper;

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

    @Override
    public List<TicketAdminDto> createForSession(SessionAdminDto dto) {
        List<Place> places = placeRepository.findAll();
        if (places.isEmpty()) {
            throw new NotFoundEntityException("Посадочные места не найдены");
        }
        List<Ticket> newTickets = new ArrayList<>();
        Session currentSession = sessionMapper.mapToSession(dto);

        for (Place place : places) {
            Ticket newmTicket = new Ticket(null, currentSession, place, FALSE);
            newTickets.add(newmTicket);
        }
        List<Ticket> tickets = ticketRepository.saveSome(newTickets);
        log.info("Созданы билеты для сеанса: {}", newTickets);
        return ticketMapper.mapToTicketDtoList(tickets);
    }

    @Override
    public List<TicketAdminDto> getBySoldCondition(boolean isSold) {
        List<Ticket> ticketsBySoldCondition = ticketRepository.findBySoldCondition(isSold);
        List<TicketAdminDto> ticketAdminDtos = ticketMapper.mapToTicketDtoList(ticketsBySoldCondition);
        log.info("Найдены билеты с параметром isSold = {} : {}", isSold, ticketAdminDtos);
        return ticketAdminDtos;
    }
}
