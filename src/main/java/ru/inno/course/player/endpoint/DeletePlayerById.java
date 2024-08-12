package ru.inno.course.player.endpoint;

import io.javalin.http.HttpStatus;
import io.javalin.router.Endpoint;
import ru.inno.course.player.model.Player;
import ru.inno.course.player.service.PlayerService;

import static io.javalin.http.HandlerType.DELETE;

public class DeletePlayerById {

    public static Endpoint endpoint(PlayerService service) {
        return new Endpoint(DELETE, "/players/{id}", ctx -> {
            String param = ctx.pathParam("id");
            try {
                int id = Integer.parseInt(param);
                Player player = service.deletePlayer(id);
                ctx.json(player).status(HttpStatus.ACCEPTED);
            } catch (NumberFormatException ex) {
                ctx.status(HttpStatus.BAD_REQUEST).result("invalid id");
            }
        });
    }
}
