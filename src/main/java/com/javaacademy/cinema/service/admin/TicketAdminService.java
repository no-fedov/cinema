package com.javaacademy.cinema.service.admin;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.TicketAdminDto;

import java.util.List;

public interface TicketAdminService {

    List<TicketAdminDto> createTicketForSession(SessionAdminDto dto);

    List<TicketAdminDto> getTicketsBySoldCondition(boolean isSold);
}
