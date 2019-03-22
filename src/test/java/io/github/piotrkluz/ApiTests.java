package io.github.piotrkluz;

import io.github.piotrkluz.api.GamesApi;
import io.github.piotrkluz.models.Game;
import io.github.piotrkluz.models.User;
import io.github.piotrkluz.utils.Utils;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ApiTests {
    private GamesApi api = new GamesApi("http://localhost:3000");

    @Test
    @DisplayName("User with the highest balance, irrespective of currency, is the only user with the ”high-roller” field set to true")
    public void highRollerFieldSet() {
        List<User> users = api.getUsers();
        Optional<User> maxUserOptional = users.stream().max((u1, u2) -> Float.compare(u1.balance, u2.balance));
        assertTrue(maxUserOptional.isPresent(), "User with max balance should be found.");
        User maxUser = maxUserOptional.get();

        List<User> wrongHighRollerUsers = users.stream()
                .filter(u -> u != maxUser)
                .filter(u -> u.highRoller != null && u.highRoller)
                .collect(Collectors.toList());

        assertTrue(maxUser.highRoller, "User with highest amount should have High-Roller field set.\n" + maxUser.toString());
        assertEquals(wrongHighRollerUsers.size(), 0,
                "All other users should have highRoller field set to false. Following have set to true: "
                        + Utils.listToString(wrongHighRollerUsers));
    }

    @Test
    @DisplayName("Ensure all users have a balance in Pounds Sterling, and convert any balances and currency codes which are not")
    public void userBalances() {
        List<User> users = api.getUsers();

        List notGBPUsers = users.stream().filter(u -> !u.currency.equals("GBP")).collect(Collectors.toList());
        assertTrue(
                notGBPUsers.size() == 0,
                () -> "There shouldn't be users with Currency different than GBP. Following users are wrong: " + Utils.listToString(notGBPUsers)
        );
    }

    @Test
    @DisplayName("Process a deposit (update the balance) for Brian of 20,000 of the users currency")
    public void updateBalance() {
        int updateBalance = 20000;
        String name = "Brian";

        User brian = api.getUser(name);
        api.updateBalance(brian.id, updateBalance);

        assertEquals(updateBalance, api.getUser(brian.id).balance, name + " should have updated balance to: " + updateBalance);
    }

    @Test
    @DisplayName("Register a new customer.")
    public void registerNewCustomer() {
        User james = new User();
        james.name = "James";
        james.balance = 20;
        james.currency = "GBP";
        james.likes = "Bingo";
        james.highRoller = false;

        User addedResponse = api.addUser(james);
        james.id = addedResponse.id; //Update model ID

        assertEquals(james, addedResponse);
        assertEquals(james, api.getUser(addedResponse.id));
    }

    @Test
    @DisplayName("Produce a list of the most popular games this week in order from most popular to least popular.")
    public void logMostPopularGames() {
        List<Game> list = api.getGames().stream()
                .sorted((o1, o2) -> Integer.compare(o2.stakesThisWeek, o1.stakesThisWeek))
                .collect(Collectors.toList());
        System.out.println("MOST POPULAR GAMES: ");
        list.forEach(g -> System.out.println(g.name + ": POPULARITY: " + g.stakesThisWeek));
    }

    @Test
    @DisplayName("The marketing department are looking to run a campaign on Starburst, produce a list of target customers based on their likes.")
    public void customersByLikes() {
        api.getUsers().stream().map(u -> u.likes).distinct().collect(Collectors.toList());

        Map<String, List<User>> usersByLikes = api.getUsers().stream()
                .collect(Collectors.groupingBy(u -> u.likes));

        System.out.println("MOST POPULAR GAMES STATISTICS: ");

        usersByLikes.forEach((likes, users) -> {
            System.out.println("\nUSERS likes: " + likes);
            users.forEach(u -> System.out.println(u.toString()));
        });
    }

    @Test
    @DisplayName("Produce a list for each user showing how many spins they can make on each game, at each level of stake available.")
    public void usersStakesStatistics() {
        api.getUsers().forEach(user -> {
            System.out.println("\n\n-------------------------------------\n\nUSER: " + user.name);
            api.getGames().forEach(g -> {
                System.out.println("\nStakes in GAME: " + g.name);
                g.stake.forEach(s -> {
                    System.out.println(s + " ---> " + user.balance / s);
                });
            });
        });
    }
}
