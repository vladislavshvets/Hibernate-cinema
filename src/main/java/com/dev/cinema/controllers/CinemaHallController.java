package com.dev.cinema.controllers;

import com.dev.cinema.dto.cinemaHall.CinemaHallDtoMapper;
import com.dev.cinema.dto.cinemaHall.CinemaHallRequestDto;
import com.dev.cinema.dto.cinemaHall.CinemaHallResponseDto;
import com.dev.cinema.service.CinemaHallService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cinema-halls")
public class CinemaHallController {
    private final CinemaHallService hallService;
    private final CinemaHallDtoMapper hallMapper;

    public CinemaHallController(CinemaHallService hallService, CinemaHallDtoMapper hallMapper) {
        this.hallService = hallService;
        this.hallMapper = hallMapper;
    }

    @PostMapping
    public void create(@RequestBody CinemaHallRequestDto cinemaHallDto) {
        hallService.add(hallMapper.mapToHall(cinemaHallDto));
    }

    @GetMapping
    public List<CinemaHallResponseDto> getAll() {
        return hallService.getAll().stream()
                .map(hallMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
