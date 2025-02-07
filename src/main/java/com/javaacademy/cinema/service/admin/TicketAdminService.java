package com.javaacademy.cinema.service.admin;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.TicketAdminDto;

import java.util.List;

public interface TicketAdminService {

    List<TicketAdminDto> createForSession(SessionAdminDto dto);

    List<TicketAdminDto> getBySoldCondition(boolean isSold);
}
