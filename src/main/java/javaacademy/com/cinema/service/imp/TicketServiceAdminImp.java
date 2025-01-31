package javaacademy.com.cinema.service.imp;

import javaacademy.com.cinema.dto.SessionDto;
import javaacademy.com.cinema.entity.Place;
import javaacademy.com.cinema.entity.Session;
import javaacademy.com.cinema.entity.Ticket;
import javaacademy.com.cinema.mapper.SessionMapper;
import javaacademy.com.cinema.repository.PlaceRepository;
import javaacademy.com.cinema.repository.TicketRepository;
import javaacademy.com.cinema.service.TicketService;
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
}
