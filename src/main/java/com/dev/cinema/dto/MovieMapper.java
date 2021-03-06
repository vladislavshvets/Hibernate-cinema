package com.dev.cinema.dto;

import com.dev.cinema.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieResponseDto mapMovie(Movie movie) {
        return new MovieResponseDto(movie.getId(), movie.getTitle(), movie.getDescription());
    }

    public Movie unmapMovie(MovieRequestDto dto) {
        return new Movie(dto.getTitle(), dto.getDescription());
    }
}
