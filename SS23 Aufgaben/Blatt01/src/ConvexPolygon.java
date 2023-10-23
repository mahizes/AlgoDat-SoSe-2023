import java.util.Arrays;

public class ConvexPolygon extends Polygon {

    public ConvexPolygon(Vector2D[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public double perimeter() {
        double totalPerimeter = 0;
        int numVertices = vertices.length;

        for (int i = 0; i < numVertices; i++) {
            Vector2D currentPoint = vertices[i];
            Vector2D nextPoint = vertices[(i + 1) % numVertices];
            double deltaX = nextPoint.getX() - currentPoint.getX();
            double deltaY = nextPoint.getY() - currentPoint.getY();
            double segmentLength = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            totalPerimeter += segmentLength;
        }

        return totalPerimeter;
    }

    @Override
    public double area() {
        double totalArea = 0;
        int numVertices = vertices.length;

        for (int i = 0; i < numVertices; i++) {
            Vector2D currentPoint = vertices[i];
            Vector2D nextPoint = vertices[(i + 1) % numVertices];
            totalArea += (currentPoint.getX() * nextPoint.getY()) - (nextPoint.getX() * currentPoint.getY());
        }

        return Math.abs(totalArea) * 0.5;
    }

    public static Polygon[] somePolygons() {
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c = new Vector2D(5, 5);
        Polygon triangle = new ConvexPolygon(new Vector2D[]{a, b, c});

        Vector2D d = new Vector2D(10, -5);
        Vector2D e = new Vector2D(12, 2);
        Vector2D f = new Vector2D(3, 17);
        Polygon quadrilateral = new ConvexPolygon(new Vector2D[]{a, d, e, f});

        Polygon regularPentagon = new RegularPolygon(5, 1);
        Polygon regularHexagon = new RegularPolygon(6, 1);

        return new Polygon[]{triangle, quadrilateral, regularPentagon, regularHexagon};
    }
    

    public static double totalArea(Polygon[] polygons) {
        double totalArea = 0;
        for (Polygon polygon : polygons) {
            totalArea += polygon.area();
        }
        return totalArea;
    }

    @Override
    public String toString() {
        return "ConvexPolygon" + Arrays.toString(vertices);
    }

    public static void main(String[] args) {
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c = new Vector2D(5, 5);
        Polygon polygon = new ConvexPolygon(new Vector2D[]{a, b, c});
        System.out.println(polygon); // Expected output: ConvexPolygon[(0.0, 0.0), (10.0, 0.0), (5.0, 5.0)]

        Polygon[] somePolys = somePolygons();
        System.out.println("Some polygons: ");
        for (Polygon p : somePolys) {
        System.out.println(p);
        }

        // Expected output:
        // Triangle[(0.0, 0.0), (10.0, 0.0), (5.0, 5.0)]
        // Tetragon[(0.0, 0.0), (10.0, -5.0), (12.0, 2.0), (3.0, 17.0)]
        // RegularPolygon[(0.8090169943749475, 0.5877852522924731), ...] (5 vertices)
        // RegularPolygon[(1.0, 0.0), ...] (6 vertices)

        double totalArea = totalArea(somePolys);
        System.out.println("Total area of polygons: " + totalArea);
    }
}
