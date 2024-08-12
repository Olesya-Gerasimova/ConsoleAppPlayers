Приложение, которое хранит список игроков и ведет учет очков для каждого игрока

Примеры [http-запросов](./src/main/resources/http_demo.http);


1. `mvn clean package`
2. `docker build --platform=linux/amd64 -t javalin-app .`
3. `docker run -p 7070:7070 javalin-app`
4. `docker tag javalin-app eremin/App:v0.1`
5. `docker tag javalin-app eremin/playersapp:v0.1`