package com.dev.cinema.controllers;

import com.dev.cinema.dto.MovieSessionRequestDto;
import com.dev.cinema.dto.MovieSessionResponseDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.mapper.MovieSessionMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieSessionService sessionService;
    private final MovieSessionMapper sessionMapper;

    @Autowired
    public MovieSessionController(MovieSessionService sessionService,
                                  MovieSessionMapper sessionMapper) {
        this.sessionService = sessionService;
        this.sessionMapper = sessionMapper;
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getSessionsByDate(@RequestParam Long movieId,
                                                           @RequestParam LocalDate date) {
        return sessionService.findAvailableSessions(movieId, date).stream()
                .map(sessionMapper::mapMovieSession)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addSession(@RequestBody MovieSessionRequestDto dto) {
        MovieSession session = sessionMapper.unmapMovieSession(dto);
        sessionService.add(session);
    }
}
