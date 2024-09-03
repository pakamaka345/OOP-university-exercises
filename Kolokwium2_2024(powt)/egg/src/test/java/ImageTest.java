import com.umcs.server.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageTest {
    private static Server server;
    @BeforeAll
    public static void setUp() {
        new Thread(() -> {
            server = new Server();
            server.start(8080);
        }).start();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "test.csv")
    public void test(String username, String path, int electrodeNumber, String expectedImage) {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            for (int i = 0; i < electrodeNumber; i++) {
                line = reader.readLine();
            }
            line = reader.readLine();
            String actualImage = server.createImage(username, line, electrodeNumber);
            System.out.println(actualImage);
            System.out.println(expectedImage);
            assertEquals(actualImage, expectedImage);
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
