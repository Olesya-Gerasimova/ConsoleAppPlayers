package ru.inno.course.player.endpoint;

import io.javalin.http.HttpStatus;
import io.javalin.router.Endpoint;
import ru.inno.course.player.service.PlayerService;

import static io.javalin.http.HandlerType.GET;

public class GetPlayerById {

    public static Endpoint endpoint(PlayerService service) {
        return new Endpoint(GET, "/players/{id}", ctx -> {
            String param = ctx.pathParam("id");
            try {
                int id = Integer.parseInt(param);
                ctx.json(service.getPlayerById(id)).status(HttpStatus.OK);
            } catch (NumberFormatException ex) {
                ctx.status(HttpStatus.BAD_REQUEST).result("invalid id");
            }
        });
    }
}
