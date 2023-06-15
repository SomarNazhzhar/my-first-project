public class Artikel implements Cloneable,CRUDoperations {
    private String artikelNumber;
    private String beschreibung;

    private double artikelPrice;


    public Artikel(String artikelNumber, String beschreibung) {
        this.artikelNumber = artikelNumber;
        this.beschreibung = beschreibung;
    }

    public Artikel() {

    }

    public String getArtikelNumber() {
        return artikelNumber;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
    public double getArtikelPrice() {
        return artikelPrice;
    }


    @Override
    public String toString() {
        return "artikel Number: " + getArtikelNumber() + " >>> Description: " + getBeschreibung()+ "]";
    }
    @Override
    public Artikel clone() throws CloneNotSupportedException {
        return (Artikel) super.clone();
    }

    @Override
    public void create(Object item) {

    }

    @Override
    public Object read(String id) {
        return null;
    }

    @Override
    public void update(Object item) {

    }

    @Override
    public void delete(String id) {

    }

}
