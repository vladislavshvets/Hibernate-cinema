package com.dev.cinema;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDateTime;
import org.apache.log4j.Logger;

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
    private static ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private static UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private static OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);
    private static final Logger logger = Logger.getLogger(AppMain.class);

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movieService.add(movie);
        movieService.getAll().forEach(logger::info);

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
        cinemaHallService.getAll().forEach(logger::info);
        movieSessionService.findAvailableSessions(1L, today.toLocalDate())
                .forEach(logger::info);
        User bob = new User();
        bob.setEmail("bob@gmail.com");
        bob.setPassword("password");
        authenticationService.register(bob.getEmail(), bob.getPassword());
        logger.info("Registered user: " + bob);
        try {
            logger.info("User has logged in: "
                    + authenticationService.login(bob.getEmail(), bob.getPassword()));
        } catch (AuthenticationException e) {
            logger.info("Incorrect password or login" + e);
        }

        bob = userService.findByEmail("bob@gmail.com").get();
        shoppingCartService.addSession(movieSession, bob);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(bob);
        logger.info(shoppingCart);
        logger.info("Empty cart: " + shoppingCart);
        orderService.completeOrder(shoppingCartService.getByUser(bob).getTickets(),
                bob);
        shoppingCartService.addSession(movieSession, bob);
        orderService.completeOrder(shoppingCartService.getByUser(bob).getTickets(), bob);
        shoppingCartService.addSession(movieSession, bob);
    }
}
