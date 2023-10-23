import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Jannes on 09.04.20.
 */

public class Druckauftrag {
    private String Auftraggeber;
    private int Seitenzahl;

    public Druckauftrag (String Auftraggeber, int Seitenzahl){
        this.Auftraggeber = Auftraggeber;
        this.Seitenzahl = Seitenzahl;
    }

    public int getSeitenzahl() {
        return Seitenzahl;
    }

    public void report () {
        System.out.println("Auftraggeber: " + Auftraggeber);
        System.out.println("Seitenzahl: " + Seitenzahl);
    }


    public static void main(String [] args) {
        Stack<Druckauftrag> DruckauftragsStack = new Stack<Druckauftrag>();
        Queue<Druckauftrag> DruckauftragsQueue = new LinkedList<Druckauftrag>();
    }

}
