package com.dev.cinema;

import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

public class AppMain {
    private static Injector injector =
            Injector.getInstance("com.dev.cinema");
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    public static void main(String[] args) throws AuthenticationException {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("3D");
        LocalDateTime today = LocalDateTime.now();
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(firstHall);
        movieSession.setShowTime(today);
        movieSession.setMovie(movie);

        cinemaHallService.add(firstHall);
        movieSessionService.add(movieSession);
        cinemaHallService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(1L, today.toLocalDate()).forEach(System.out::println);

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("test");
        authenticationService.register(user.getEmail(), user.getPassword());
        User userTest = authenticationService.login(user.getEmail(), user.getPassword());
    }
}
