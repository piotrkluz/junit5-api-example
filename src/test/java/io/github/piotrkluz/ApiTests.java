package io.github.piotrkluz;

import org.junit.jupiter.api.*;

import io.github.piotrkluz.api.GamesApi;

import java.util.List;

public class ApiTests {

    GamesApi api = new GamesApi();

    @Test
    @DisplayName("User with the highest balance, irrespective of currency, is the only user with the ”high-roller” field set to true")
    public void exampleTest() {
        try {
            List a = api.getGames();
            List b = api.getUsers();
            System.out.println("fdsa");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
