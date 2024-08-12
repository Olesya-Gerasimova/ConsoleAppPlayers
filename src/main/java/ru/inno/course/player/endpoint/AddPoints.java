package ru.inno.course.player.endpoint;

import io.javalin.http.HttpStatus;
import io.javalin.router.Endpoint;
import ru.inno.course.player.model.AddPointsReq;
import ru.inno.course.player.model.AddPointsResp;
import ru.inno.course.player.service.PlayerService;

import static io.javalin.http.HandlerType.PATCH;
import static io.javalin.http.HandlerType.POST;

public class AddPoints {

    public static Endpoint endpoint(PlayerService service) {
        return new Endpoint(PATCH, "/players/{id}", ctx -> {
            try {
                String param = ctx.pathParam("id");
                int id = Integer.parseInt(param);
                AddPointsReq reqBody = ctx.bodyAsClass(AddPointsReq.class);

                int points = service.addPoints(id, reqBody.points());
                ctx.json(new AddPointsResp(id, points)).status(HttpStatus.ACCEPTED);
            } catch (NumberFormatException ex) {
                ctx.status(HttpStatus.BAD_REQUEST).result("invalid id");
            }
        });
    }
}
