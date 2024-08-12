package ru.inno.course.player;

import io.javalin.Javalin;
import io.javalin.router.Endpoint;
import ru.inno.course.player.endpoint.*;
import ru.inno.course.player.service.PlayerService;
import ru.inno.course.player.service.PlayerServiceImpl;

import java.util.List;


public class WebApp {
    private final static PlayerService service = new PlayerServiceImpl();
    private final static List<Endpoint> endpointList = List.of(
            GetPlayers.endpoint(service),
            GetPlayerById.endpoint(service),
            CreatePlayer.endpoint(service),
            DeletePlayerById.endpoint(service),
            AddPoints.endpoint(service)
    );

    public static void main(String[] args) {
        Javalin app = Javalin.create();
        endpointList.forEach(app::addEndpoint);
        app.start(7070);
    }
}

