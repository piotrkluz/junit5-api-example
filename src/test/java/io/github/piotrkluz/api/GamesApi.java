package io.github.piotrkluz.api;

import java.util.Arrays;
import java.util.List;

import io.github.piotrkluz.models.Game;
import io.github.piotrkluz.models.User;

import static io.restassured.RestAssured.*;

public class GamesApi {
    public List<Game> getGames() {
       return get("http://localhost:3000/games", Game[].class);
    }

    public List<User> getUsers() {
        return get("http://localhost:3000/users", User[].class);
    }

    private <T> List<T> get(String url, Class<T[]> target) {
        return Arrays.asList(given().get(url).as(target));
    }
}