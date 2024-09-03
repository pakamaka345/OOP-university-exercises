import com.umcs.Polygon;
import com.umcs.model.Point;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PolygonTest {
    @Test
    public void testInsidePolygon(){
        Polygon polygon = new Polygon(List.of(new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)));
        Point point = new Point(5, 5);
        Assert.assertTrue(polygon.inside(point));
    }
    @Test
    public void testUnderPolygon(){
        Polygon polygon = new Polygon(List.of(new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)));
        Point point = new Point(5, -5);
        Assert.assertFalse(polygon.inside(point));
    }
    @Test
    public void testRightPolygon(){
        Polygon polygon = new Polygon(List.of(new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)));
        Point point = new Point(15, 5);
        Assert.assertFalse(polygon.inside(point));
    }
}
