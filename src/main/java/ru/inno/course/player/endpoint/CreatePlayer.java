package ru.inno.course.player.endpoint;

import io.javalin.http.HttpStatus;
import io.javalin.router.Endpoint;
import ru.inno.course.player.model.CreatePlayerReq;
import ru.inno.course.player.model.CreatePlayerResp;
import ru.inno.course.player.service.PlayerService;

import static io.javalin.http.HandlerType.POST;

public class CreatePlayer  {

    public static Endpoint endpoint(PlayerService service) {
        return new Endpoint(POST, "/players", ctx -> {
            CreatePlayerReq reqBody;
            try {
                reqBody = ctx.bodyAsClass(CreatePlayerReq.class);
                int id = service.createPlayer(reqBody.name());
                ctx.json(new CreatePlayerResp(id)).status(HttpStatus.CREATED);
            } catch (IllegalArgumentException ex) {
                ctx.status(HttpStatus.BAD_REQUEST).result("invalid req. {\"name\": \"PlayerName\"}");

            }
        });
    }
}
