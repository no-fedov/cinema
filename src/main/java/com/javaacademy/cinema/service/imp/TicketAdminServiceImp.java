package com.javaacademy.cinema.service.imp;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.TicketAdminDto;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.entity.Ticket;
import com.javaacademy.cinema.mapper.SessionMapper;
import com.javaacademy.cinema.mapper.TicketMapper;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import com.javaacademy.cinema.service.TicketAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketAdminServiceImp implements TicketAdminService {

    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final SessionMapper sessionMapper;
    private final TicketMapper ticketMapper;

    public List<TicketAdminDto> createTicketForSession(SessionAdminDto dto) {
        List<Place> places = placeRepository.findAll();
        if (places.isEmpty()) {
            throw new RuntimeException("Нет посадочных мест");
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
    public List<TicketAdminDto> getTicketsBySoldCondition(boolean isSold) {
        List<Ticket> ticketsBySoldCondition = ticketRepository.findTicketsBySoldCondition(isSold);
        List<TicketAdminDto> ticketAdminDtos = ticketMapper.mapToTicketDtoList(ticketsBySoldCondition);
        log.info("Найдены билеты с параметром isSold = {} : {}", isSold, ticketAdminDtos);
        return ticketAdminDtos;
    }
}
