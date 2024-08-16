package ru.inno.course.player.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.inno.course.player.model.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

public class PlayerServiceImplTest {

    private PlayerServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new PlayerServiceImpl(); // Создаем новый экземпляр сервиса перед каждым тестом
        service.clearPlayers(); // Очищаем данные перед каждым тестом
    }

    //ПОЗИТИВНЫЕ:)

    @Test
    public void testAddPlayerAndVerifyInList() {
        int playerId = service.createPlayer("Player1");
        Collection<Player> players = service.getPlayers();
        assertTrue(players.stream().anyMatch(p -> p.getId() == playerId && "Player1".equals(p.getNick())), "Player should be in the list");
    }

    @Test
    public void testAddAndDeletePlayer() {
        int playerId = service.createPlayer("Player1");
        service.deletePlayer(playerId);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            service.getPlayerById(playerId);
        });
        assertEquals("No such user: " + playerId, exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testAddPlayerWithJsonFile() {
        // Assuming JSON file exists
        int playerId = service.createPlayer("Player1");
        Player player = service.getPlayerById(playerId);
        assertNotNull(player, "Player should be created");
        assertEquals("Player1", player.getNick(), "Player nickname should match");
    }

    @Test
    public void testAddPointsToExistingPlayer() {
        int playerId = service.createPlayer("Player1");
        int newPoints = service.addPoints(playerId, 10);
        assertEquals(10, newPoints, "Player should have 10 points");
    }

    @Test
    public void testAddPointsOnTopOfExisting() {
        int playerId = service.createPlayer("Player1");
        service.addPoints(playerId, 10);
        int newPoints = service.addPoints(playerId, 5);
        assertEquals(15, newPoints, "Player should have 15 points");
    }

    @Test
    public void testUpdatePlayerNicknameToExisting() {
        service.createPlayer("Player1");
        service.createPlayer("Player2");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.updatePlayerNickname(2, "Player1");
        });
        assertEquals("Nickname is already in use: Player1", exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testGetPlayerByIdAfterAdding() {
        int playerId = service.createPlayer("Player1");
        Player player = service.getPlayerById(playerId);
        assertEquals("Player1", player.getNick(), "Player nickname should match");
    }

    @Test
    public void testDeletePlayer() {
        int playerId = service.createPlayer("Player1");
        Player deletedPlayer = service.deletePlayer(playerId);
        assertNotNull(deletedPlayer, "Deleted player should not be null");
        assertEquals("Player1", deletedPlayer.getNick(), "Deleted player nickname should match");
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            service.getPlayerById(playerId);
        });
        assertEquals("No such user: " + playerId, exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testSaveToFile() {
        int playerId = service.createPlayer("Player1");
        // Assuming a method to get raw file content or mock the file save
        // Verify file contains player information
    }

    @Test
    public void testDeleteNonExistingPlayer() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            service.deletePlayer(999);
        });
        assertEquals("No such user: 999", exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testLoadFromFile() {
        // Assume the file contains certain players
        service = new PlayerServiceImpl(); // Reinitialize to simulate load
        Collection<Player> players = service.getPlayers();
        assertTrue(players.stream().anyMatch(p -> "Player1".equals(p.getNick())), "Player1 should be loaded from file");
    }

    @Test
    public void testUniqueIdGeneration() {
        int id1 = service.createPlayer("Player1");
        int id2 = service.createPlayer("Player2");
        int id3 = service.createPlayer("Player3");
        service.deletePlayer(id2);
        int newId = service.createPlayer("Player4");
        assertEquals(4, newId, "The new ID should be 4");
    }

    @Test
    public void testListPlayersWithNoJsonFile() {
        service.clearPlayers(); // Очистить игроков, если JSON-файл отсутствует
        Collection<Player> players = service.getPlayers();
        assertTrue(players.isEmpty(), "Players list should be empty");
    }

    @Test
    public void testCreatePlayerWith15CharactersNickname() {
        String longNickname = "123456789012345"; // 15 characters
        int playerId = service.createPlayer(longNickname);
        Player player = service.getPlayerById(playerId);
        assertEquals(longNickname, player.getNick(), "Player nickname should match");
    }

    @Test
    public void testListAllPlayers() {
        service.createPlayer("Player1");
        service.createPlayer("Player2");
        Collection<Player> players = service.getPlayers();
        assertEquals(2, players.size(), "List size should match number of created players");
    }

    @Test
    public void testClearPlayers() {
        service.createPlayer("Player1");
        service.createPlayer("Player2");
        service.clearPlayers();
        Collection<Player> players = service.getPlayers();
        assertTrue(players.isEmpty(), "Player list should be empty after clearing");
    }

    //НЕГАТИВНЫЕ:(
    @Test
    public void negTestDeleteNonExistingPlayer() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            service.deletePlayer(10); // Assuming last ID is 8
        });
        assertEquals("No such user: 10", exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testCreateDuplicatePlayer() {
        service.createPlayer("Player1");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createPlayer("Player1");
        });
        assertEquals("Nickname is already in use: Player1", exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testGetPlayerByInvalidId() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            service.getPlayerById(999); // Non-existing ID
        });
        assertEquals("No such user: 999", exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testCreatePlayerWithEmptyNickname() {
        PlayerServiceImpl service = new PlayerServiceImpl(); // Создайте экземпляр сервиса

        // Проверьте, что исключение выбрасывается при пустом никнейме
        assertThrows(IllegalArgumentException.class, () -> {
            service.createPlayer(""); // Пустой никнейм
        }, "Nickname cannot be empty or null.");

        assertThrows(IllegalArgumentException.class, () -> {
            service.createPlayer(null); // Null никнейм
        }, "Nickname cannot be empty or null.");
    }

    @Test
    public void testAddNegativePoints() {
        PlayerServiceImpl service = new PlayerServiceImpl();
        int playerId = service.createPlayer("ValidNickname");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.addPoints(playerId, -10); // Пытаемся добавить отрицательные очки
        });
        assertEquals("Points cannot be negative.", thrown.getMessage());
    }

    @Test
    public void testAddPointsWithoutId() {
        PlayerServiceImpl service = new PlayerServiceImpl();
        int invalidPlayerId = 0; // Или другой некорректный ID

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.addPoints(invalidPlayerId, 10); // Пытаемся добавить очки несуществующему игроку
        });
        assertEquals("Invalid player ID: " + invalidPlayerId, thrown.getMessage());
    }

    @Test
    public void testLoadFromDifferentJsonFile() {
        // Use a different JSON file or mock the loading process
        service = new PlayerServiceImpl(); // Load with new data
        Collection<Player> players = service.getPlayers();
        assertFalse(players.isEmpty(), "Players list should not be empty");
    }

    @Test
    public void testAddPointsWhenNoPlayersExist() {
        // Убедитесь, что нет игроков
        service.clearPlayers(); // Этот метод должен быть реализован для очистки списка игроков

        int playerId = 1; // Не существует такого игрока

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.addPoints(playerId, 10);
        });

        assertEquals("Invalid player ID: " + playerId, exception.getMessage(), "Exception message should match");
    }
}



