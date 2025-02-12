package com.javaacademy.cinema.repository.imp;

import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.entity.Ticket;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TicketRepositoryImp implements TicketRepository {

    private static final String TICKET_BY_ID_QUERY = "select * from ticket where id = ?";
    private static final String SAVE_TICKET_QUERY = """
                insert into ticket (place_id, session_id, is_sold)
                values (?, ?, false)
                returning id;
            """;
    private static final String BUY_TICKET_QUERY = """
                update ticket
                set is_sold = true
                where id = ?;
            """;
    private static final String TICKETS_BY_SESSION_BY_IS_SOLD = """
                select t.*,
                    p.name as place_name,
                    s.movie_id,
                    s.price,
                    s.datetime,
                    m.name as movie_name,
                    m.description as movie_description
                from ticket t
                    join place p on t.place_id = p.id
                    join session s on t.session_id = s.id
                    left join movie m on s.movie_id = m.id
                where s.id = ? and t.is_sold = ?;
            """;
    private static final String TICKETS_IS_SOLD = """
                select t.*,
                    p.name as place_name,
                    s.movie_id,
                    s.price,
                    s.datetime,
                    m.name as movie_name,
                    m.description as movie_description
                from ticket t
                    join place p on t.place_id = p.id
                    join session s on t.session_id = s.id
                    left join movie m on s.movie_id = m.id
                where t.is_sold = ?
                order by t.id;
            """;
    private static final String TICKET_ID_BY_SESSION_BY_PLACE = """
                select *
                from ticket
                where session_id = ? and place_id = ?;
            """;

    private final JdbcTemplate jdbcTemplate;
    private final SessionRepository sessionRepository;
    private final PlaceRepository placeRepository;

    @Override
    public Optional<Ticket> findById(Integer id) {
        Optional<Ticket> currentTicket = jdbcTemplate.query(
                TICKET_BY_ID_QUERY,
                this::mapToTicket,
                id
        ).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", TICKET_BY_ID_QUERY, id, currentTicket);
        return currentTicket;
    }

    @Override
    public Ticket save(final Ticket newTicket) {
        Integer placeId = newTicket.getPlace().getId();
        Integer sessionId = newTicket.getSession().getId();
        Integer ticketId = jdbcTemplate.queryForObject(
                SAVE_TICKET_QUERY,
                Integer.class,
                placeId,
                sessionId
        );
        newTicket.setId(ticketId);
        log.info("Сохранен новый билет: {}", newTicket);
        return newTicket;
    }

    @Override
    public Ticket buy(Integer id) {
        Ticket currentTicket = findById(id)
                .orElseThrow(() -> new RuntimeException("билет с id = %s не существует".formatted(id)));
        jdbcTemplate.update(BUY_TICKET_QUERY, id);
        currentTicket.setIsSold(Boolean.TRUE);
        return currentTicket;
    }

    @Override
    public List<Ticket> findBySessionId(Integer sessionId, boolean isSold) {
        List<Ticket> tickets = jdbcTemplate.query(
                TICKETS_BY_SESSION_BY_IS_SOLD,
                this::mapToTicketForList,
                sessionId,
                isSold
        );
        log.info("Найдены билеты на сеанс: {}", tickets);
        return tickets;
    }

    @Override
    public List<Ticket> saveSome(final List<Ticket> tickets) {
        List<Integer> queryParameters = new ArrayList<>();

        tickets.forEach(ticket -> {
            queryParameters.add(ticket.getPlace().getId());
            queryParameters.add(ticket.getSession().getId());
        });

        String patternForInsert = queryPatternForInsertNewTicket(tickets.size());

        String sql = """
                insert into ticket (place_id, session_id, is_sold)
                values %s
                returning id;
                """.formatted(patternForInsert);

        List<Integer> ids = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getInt("id"),
                queryParameters.toArray()
        );
        for (int i = 0; i < ids.size(); i++) {
            tickets.get(i).setId(ids.get(i));
        }
        return tickets;
    }

    @Override
    public List<Ticket> findBySoldCondition(boolean isSold) {
        List<Ticket> tickets = jdbcTemplate.query(
                TICKETS_IS_SOLD,
                this::mapToTicketForList,
                isSold);
        log.info("Найдены все билеты, где sold = {} : {}", isSold, tickets);
        return tickets;
    }

    @Override
    public Optional<Ticket> findBySessionByPlace(Integer sessionId, Integer placeId) {
        Optional<Ticket> ticket = jdbcTemplate.query(
                TICKET_ID_BY_SESSION_BY_PLACE,
                this::mapToTicket,
                sessionId,
                placeId
        ).stream().findFirst();
        return ticket;
    }

    @SneakyThrows
    private Ticket mapToTicket(ResultSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setIsSold(rs.getBoolean("is_sold"));
        ticket.setPlace(placeRepository.findById(rs.getInt("place_id")).get());
        ticket.setSession(sessionRepository.findById(rs.getInt("session_id")).get());
        return ticket;
    }

    @SneakyThrows
    private Ticket mapToTicketForList(ResultSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setIsSold(rs.getBoolean("is_sold"));

        Place place = new Place();
        place.setId(rs.getInt("place_id"));
        place.setName(rs.getString("place_name"));
        ticket.setPlace(place);

        Session session = new Session();
        session.setId(rs.getInt("session_id"));
        session.setPrice(rs.getBigDecimal("price"));
        session.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());

        String movieId = rs.getString("movie_id");
        if (Objects.nonNull(movieId)) {
            Movie movie = new Movie();
            movie.setId(Integer.parseInt(movieId));
            movie.setName(rs.getString("movie_name"));
            movie.setDescription(rs.getString("movie_description"));
            session.setMovie(movie);
        }
        ticket.setSession(session);
        return ticket;
    }

    private String queryPatternForInsertNewTicket(int countTickets) {
        String patternForInsert = "(?, ?, false),";
        patternForInsert = patternForInsert.repeat(countTickets);
        patternForInsert = StringUtils.chop(patternForInsert);
        return patternForInsert;
    }
}
