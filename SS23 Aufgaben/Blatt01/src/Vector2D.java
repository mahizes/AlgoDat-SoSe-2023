public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D that) {
      this(that.x, that.y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D other = (Vector2D) o;

        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static void main(String[] args) {
        // Herons Formel zur Bestimmung des Flächeninhalts eines Dreiecks
        // Verwendung dieses Codes (mit notwendiger Anpassung) in Triangle -> area() erlaubt :)
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c =  new Vector2D(5, 5);
        Vector2D side1 = new Vector2D(b.getX() - a.getX(),b.getY() - a.getY());
        Vector2D side2 = new Vector2D(c.getX() - b.getX(),c.getY() - b.getY());
        Vector2D side3 = new Vector2D(a.getX() - c.getX(),a.getY() - c.getY());
        double s1 = side1.length();
        double s2 = side2.length();
        double s3 = side3.length();
        double s = (s1 + s2 + s3)/2;
        double area = Math.sqrt(s * (s-s1) * (s-s2) * (s-s3));
        System.out.printf("Die Fläche des Dreiecks {%s, %s, %s} beträgt %.1f LE^2.\n", a, b, c, area);
    }
}

