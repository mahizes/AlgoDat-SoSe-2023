/**
 * Created by Jannes on 15.04.20.
 */
public abstract class Elektrogeraete {

    int Kaufpreis;
    int TageBisPruefdatum = 365;

    void pruefen() {
        this.TageBisPruefdatum = 365;
    }
}
