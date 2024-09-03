package cpm.umcs.music;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListenerAccountTest {
    @BeforeAll
    public static void openDB() throws SQLException {
        cpm.umcs.database.DatabaseConnection.connect("songs.db", "connection");
        ListenerAccount.Persistence.init();
    }

    @Test
    public void registerTest() throws SQLException {
        int id = ListenerAccount.Persistence.register("test", "test");
        assertEquals(1, id);
    }
    @Test
    public void validLoginTest() throws SQLException, AuthenticationException {
        assertEquals(1, ListenerAccount.Persistence.authenticate("test", "test").getId());
    }
    @Test
    public void getCreditTest() throws SQLException {
        assertEquals(0, ListenerAccount.Persistence.getCredits(1));
    }
    @Test
    public void getAndAddCreditTest() throws SQLException {
        ListenerAccount.Persistence.addCredits(1, 10);
        assertEquals(10, ListenerAccount.Persistence.getCredits(1));
    }
    @Test
    public void checkEmptyPlaylist() throws SQLException, NotEnoughCreditsException, AuthenticationException {
        ListenerAccount account = ListenerAccount.Persistence.authenticate("test", "test");
        Playlist playlist = account.createPlaylist(List.of(3, 5, 6));
        assertEquals(3, playlist.size());
    }

    @AfterAll
    public static void closeDB() throws SQLException {
        cpm.umcs.database.DatabaseConnection.disconnect("connection");
    }

}
