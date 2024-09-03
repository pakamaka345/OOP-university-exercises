import com.umcs.City;
import com.umcs.Resource;
import com.umcs.model.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityTest {
    private final City landCity = new City(new Point(5,5), "landCity", 2);
    private final City portCity = new City(new Point(9, 9), "portCity", 5);
    private final Resource coal = new Resource(new Point(5, 5), Resource.Type.Coal);
    private final Resource wood = new Resource(new Point(0, 0), Resource.Type.Wood);
    private final Resource fish = new Resource(new Point(11, 11), Resource.Type.Fish);

    private static Stream<Arguments> cityResourceProvider() {
        return Stream.of(
                Arguments.of(landCity, coal, true),
                Arguments.of(landCity, wood, false),
                Arguments.of(portCity, fish, true),
                Arguments.of(landCity, fish, false)
        );
    }

    @ParameterizedTest
    @MethodSource("cityResourceProvider")
    void testCity(City city, Resource resource, boolean expected) {
        city.addResourcesInRange(List.of(resource), 5);
        boolean result = city.resources.contains(resource.type);
        assertEquals(expected, result);
    }
}