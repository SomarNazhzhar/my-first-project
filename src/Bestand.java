import java.util.ArrayList;
import java.util.Scanner;

public class Bestand {
    private String bestandID;
    private Lager lagerName;
    private int amount;
    private ArrayList<Artikel> artikels;
    private double artikelPrice;

    public Bestand(String bestandID, Lager lagerName, int amount ,double artikelPrice) {
        this.bestandID = bestandID;
        this.lagerName = lagerName;
        this.amount = amount;
        this.artikels = new ArrayList<>();
        this.artikelPrice = artikelPrice;
    }

    public String getBestandID() {
        return bestandID;
    }

    public void setBestandID(String bestandID) {
        this.bestandID = bestandID;
    }

    public Lager getLagerName() {
        return lagerName;
    }

    public void setLagerName(Lager lagerName) {
        this.lagerName = lagerName;
    }

    public int getAmount() {
        return amount;
    }

    public ArrayList<Artikel> getArtikels() {
        return artikels;
    }

    public void setArtikels(ArrayList<Artikel> artikels) {
        this.artikels = artikels;
    }


    public void setArtikelPrice(double artikelPrice) {
        this.artikelPrice = artikelPrice;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public  void mengeChange() {

    }


    public static void sellArtikel(Lager mainLager) {
        Kunde.orderBestand(mainLager);
    }

    public static void reciveBestand(Lager mainLager) {
       Lieferant.addArtikelsFromLieferant(mainLager);
    }



}
