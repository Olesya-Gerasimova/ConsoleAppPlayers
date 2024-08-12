package ru.inno.course.player.endpoint;

import io.javalin.http.HttpStatus;
import io.javalin.router.Endpoint;
import ru.inno.course.player.service.PlayerService;

import static io.javalin.http.HandlerType.GET;

public class GetPlayers {

    public static Endpoint endpoint(PlayerService service) {
        return new Endpoint(GET, "/players", ctx -> ctx.json(service.getPlayers()).status(HttpStatus.OK));
    }
}
