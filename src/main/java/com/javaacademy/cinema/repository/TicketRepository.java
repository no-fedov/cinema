package com.javaacademy.cinema.repository;

import com.javaacademy.cinema.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> findById(Integer id);

    Ticket save(Ticket newTicket);

    // TODO: посмотреть куда вынести проверку на существование билета
    Ticket buyTicket(Integer id);

    List<Ticket> findBySessionId(Integer sessionId, boolean isSold);

    List<Ticket> saveSome(List<Ticket> tickets);

    List<Ticket> findTicketsBySoldCondition(boolean isSold);
}
