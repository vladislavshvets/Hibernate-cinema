package com.dev.cinema;

import com.dev.cinema.config.AppConfig;
import com.dev.cinema.exception.AuthenticationException;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppMain {
    private static final Logger logger = Logger.getLogger(AppMain.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movie.setDescription("Race, action");
        MovieService movieService =
                context.getBean(MovieService.class);
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

        CinemaHallService cinemaHallService =
                context.getBean(CinemaHallService.class);
        cinemaHallService.add(firstHall);
        MovieSessionService movieSessionService =
                context.getBean(MovieSessionService.class);
        movieSessionService.add(movieSession);
        cinemaHallService.getAll().forEach(logger::info);
        movieSessionService.findAvailableSessions(1L, today.toLocalDate())
                .forEach(logger::info);
        User bob = new User();
        bob.setEmail("bob@gmail.com");
        bob.setPassword("password");
        AuthenticationService authenticationService =
                context.getBean(AuthenticationService.class);
        authenticationService.register(bob.getEmail(), bob.getPassword());
        logger.info("Registered user: " + bob);
        try {
            logger.info("User has logged in: "
                    + authenticationService.login(bob.getEmail(), bob.getPassword()));
        } catch (AuthenticationException e) {
            logger.error("Incorrect password or login" + e);
        }
        UserService userService =
                context.getBean(UserService.class);
        bob = userService.findByEmail("bob@gmail.com").get();
        ShoppingCartService shoppingCartService =
                context.getBean(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession, bob);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(bob);
        logger.info(shoppingCart);
        logger.info("Empty cart: " + shoppingCart);
        OrderService orderService =
                context.getBean(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUser(bob).getTickets(),
                bob);
        shoppingCartService.addSession(movieSession, bob);
        orderService.completeOrder(shoppingCartService.getByUser(bob).getTickets(), bob);
        shoppingCartService.addSession(movieSession, bob);
    }
}
