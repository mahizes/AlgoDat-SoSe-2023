/**
 * Created by Jannes on 09.04.20.
 */

import java.util.LinkedList;
import java.util.Queue;

public class Drucker extends Elektrogeraete{
    String Netzwerkname;
    Queue<Druckauftrag> Druckauftraege;
    int Tinte;

    public Drucker (String Netzwerkname) {
        // todo


    }

    public void DruckauftragEinreihen(Druckauftrag d) {
        this.Druckauftraege.add(d);
    }

    public void fill() {
        // todo

    }

    private void warten(int Sekunden) {
        try {
            for (int i = 0; i < 20; i++){
                Thread.sleep(Sekunden * 50);
                System.out.print(".");
            }
            System.out.println();

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void getInfo(){
        System.out.println("Aktuelle infromationen zum Drucker: " + this.Netzwerkname);
        System.out.println("Füllstand:                       " + this.Tinte + "%");
        System.out.println("Anstehende Aufträge:             " + this.Druckauftraege.size() + "\n");
        System.out.println("Tage bis zum nächsten Prüfdatum: " + this.TageBisPruefdatum+ "\n");

    }

    public void drucken() {
        // todo
    }
}
