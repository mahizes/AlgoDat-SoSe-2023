import sun.awt.image.ImageWatched;

import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Jannes on 09.04.20.
 */
public class Firma {
    private String Name;
    private LinkedList<Mitarbeiter> Angestellte;
    private Drucker einDrucker;
    int anzahlAngestellter;
    int letzteMitarbeiterNummer;

    public Firma(String Name) {
        this.Name = Name;
        this.Angestellte = new LinkedList<Mitarbeiter>();
        this.einDrucker = new Drucker("derDrucker");
        this.letzteMitarbeiterNummer = 0;
        this.anzahlAngestellter = 0;
    }

    public void einstellen (String Name) {
        this.Angestellte.add( new Mitarbeiter(Name, ++this.letzteMitarbeiterNummer));
        this.anzahlAngestellter++;
    }

    private int getNumberFromString (String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void checkSeitenzahl (Scanner s, Mitarbeiter m){
        System.out.println("Wie viele Seiten sollen für " + m.getName() + " gedruckt werden (1-100)?");

        while (true) {
            String Eingabe = s.next();
            int SeitenZahl = getNumberFromString(Eingabe);
            if ( (SeitenZahl > 0) && (SeitenZahl < 101) ) {
                System.out.println("Es werden " + SeitenZahl + " Seiten in Auftrag gegeben.");
                System.out.println();
                this.einDrucker.DruckauftragEinreihen(new Druckauftrag(m.getName(), SeitenZahl));
                return;
            } else {
                System.out.println("Ihre Eingabe: " + Eingabe + " konnte nicht verarbeitet werden.");
                System.out.println("Bitte geben Sie eine Seitenzahl zwischen 1 und 100 ein");
            }
        }
    }

    private Mitarbeiter getMitarbeiterFromEingabe (String Eingabe) {

        int nummer = getNumberFromString(Eingabe);
        if ((nummer < 1) || (nummer > this.anzahlAngestellter) ) {
            System.out.println("Eingegebene MitarbeiterNr: " + Eingabe + " ist ungültig!");
            System.out.println("Geben Sie eine MitarbeiterNr zwischen 1 und " + this.anzahlAngestellter + " ein!");
            System.out.println();
            return null;
        } else {
            return this.Angestellte.get(--nummer);
        }
    }

    public void Druckauftragssteuerung () {
        System.out.println();
        System.out.println("Wilkommen in der Druckauftragssteuerung der " + this.Name + " Korporation");
        System.out.println("Sie können Druckaufträge im Namen der Mitarbeiter in Auftrag geben.");
        System.out.println("Haben Sie alle Aufträge eingegeben können sie mit dem Drucken beginnen.");
        if (this.anzahlAngestellter < 1) {
            System.out.println("Die Firma " + this.Name + "hat noch keine angestellten!");
            System.out.println("Stellen Sie Mitarbeiter in ihrer Firma in der main() ein!");
            return;
        }
        System.out.println();
        System.out.println("######## Druckauftrag anlegen ########");
        System.out.println("Geben Sie die MitarbeiterNr ein (1.." + this.anzahlAngestellter + ") und bestätigen Sie mit Enter");
        System.out.println("Legen Sie anschließend eine Seitenzahl zwischen 1 und 100 fest.");
        System.out.println("Bestätigen Sie erneut mit Enter.");
        System.out.println("######################################");
        System.out.println();
        System.out.println("######### weitere Befehle #############");
        System.out.println("Sie verlassen die Druckauftragssteuerung mit q");
        System.out.println("Sie Drucken alle angelegten aufträge mit d");
        System.out.println("Sie erhalten Infromationen über den Drucker mit (h)");
        System.out.println("######################################");
        System.out.println();
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Mitarbeiter auswählen            (1.." + this.anzahlAngestellter + ")");
            System.out.println("Alle Aufträge Drucken            (d)");
            System.out.println("Druckauftragssteuerung verlassen (q)");
            System.out.println("Drucker Informationen            (h)");
            System.out.println();

            String Eingabe = scanner.next();
            switch (Eingabe){
                case "q":
                    System.out.println("Sie verlassen die Druckauftragssteuerung");
                    return;
                case "h":
                    this.einDrucker.getInfo();
                    break;
                case "d":
                    System.out.println("\nStarte Druckvorgang!");
                    this.einDrucker.drucken();
                    break;
                default:
                    Mitarbeiter m = getMitarbeiterFromEingabe(Eingabe);
                    if (m != null) {
                        checkSeitenzahl(scanner, m);
                    }
            }
        }
    }



    public static void main(String [] args) {
        // Ändern Sie gerne auch den Namen
        Firma tolleFirma = new Firma("tolleFirma");

        // Sie können gerne die Namen der Mitarbeiter ändern und weitere hinzufügen
        tolleFirma.einstellen("Tanja");
        tolleFirma.einstellen("Sven");
        tolleFirma.einstellen("Natalie");

        tolleFirma.Druckauftragssteuerung();

    }


}
