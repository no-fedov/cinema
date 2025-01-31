package javaacademy.com.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCreateDto {
    @JsonProperty("movie_id")
    private Integer movieId;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    private BigDecimal price;
}
