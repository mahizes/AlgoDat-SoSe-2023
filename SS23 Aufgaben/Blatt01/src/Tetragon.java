// TODO
import java.util.Arrays;

public class Tetragon extends ConvexPolygon {

    public Tetragon(Vector2D a, Vector2D b, Vector2D c, Vector2D d) {
        super(new Vector2D[]{a, b, c, d});
    }

    public Tetragon(Tetragon tetragon) {
        super(tetragon.vertices.clone());
    }

    @Override
    public String toString() {
        return "Tetragon{" + Arrays.toString(vertices) + "}";
    }

    public static void main(String[] args) {
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c = new Vector2D(10, 10);
        Vector2D d = new Vector2D(0, 10);
        Tetragon tetragon = new Tetragon(a, b, c, d);
        double area = tetragon.area();
        System.out.printf("The area of the tetragon {%s, %s, %s, %s} is %.1f square units.\n", a, b, c, d, area);
    
        Tetragon tetragon2 = new Tetragon(tetragon);
        System.out.println("tetragon2 is a copy of 'tetragon' created by the copy constructor: " + tetragon2);
        a.setX(-5);
        System.out.println("The point 'a' used to define 'tetragon' has been modified.");
        System.out.println("The current value of 'tetragon2' is: " + tetragon2);
    }    
}
