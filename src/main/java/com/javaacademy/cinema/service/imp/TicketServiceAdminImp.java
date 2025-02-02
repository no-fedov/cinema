package com.javaacademy.cinema.service.imp;

import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.entity.Ticket;
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

import static java.lang.Boolean.FALSE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceAdminImp implements TicketService {

    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final SessionMapper sessionMapper;
    private final TicketMapper ticketMapper;

    public void createTicketForSession(SessionDto dto) {
        List<Place> places = placeRepository.findAll();
        List<Ticket> newTickets = new ArrayList<>();
        Session currentSession = sessionMapper.mapToSession(dto);

        for (int i = 0; i < places.size(); i++) {
            Ticket newmTicket = new Ticket(null, currentSession, places.get(i), FALSE);
            newTickets.add(newmTicket);
        }
        ticketRepository.saveSome(newTickets);
        log.info("Созданы билеты для сеанса: {}", newTickets);
    }

    @Override
    public List<TicketDto> getTicketsBySoldCondition(boolean isSold) {
        List<Ticket> ticketsBySoldCondition = ticketRepository.findTicketsBySoldCondition(isSold);
        List<TicketDto> ticketDtos = ticketMapper.mapToTicketDtoList(ticketsBySoldCondition);
        log.info("Найдены билеты с параметром isSold = {} : {}", isSold, ticketDtos);
        return ticketDtos;
    }
}
