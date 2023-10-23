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
        this.Netzwerkname = Netzwerkname;
        this.Tinte = 100;
        this.Druckauftraege = new LinkedList<Druckauftrag>();
        this.Kaufpreis = 300;


    }

    public void DruckauftragEinreihen(Druckauftrag d) {
        this.Druckauftraege.add(d);
    }

    public void fill() {
        this.Tinte = 100;

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

        if (this.Druckauftraege.peek() == null) {
            System.out.println("keine Druckaufträge vorhanden\n");
            return;
        }

        System.out.println("Drucke alle Druckaufträge\n");
        while (this.Druckauftraege.peek() != null ) {
            Druckauftrag aktuellerAuftrag = this.Druckauftraege.poll();

            aktuellerAuftrag.report();
            if (this.TageBisPruefdatum <= 0) {
                System.out.println("Drucken nicht möglich, dass gerät mus geprüft werden.");
                System.out.print("Prüfen");
                warten(5);
                pruefen();
            }

            if (aktuellerAuftrag.getSeitenzahl() > this.Tinte) {
                System.out.print("Auffüllen.");
                warten(2);
                fill();
            }
            System.out.print("Drucken.");
            warten(2);
            this.Tinte -= aktuellerAuftrag.getSeitenzahl();
            System.out.println("Auftrag abgeschlossen!\n");

        }
    }
}
