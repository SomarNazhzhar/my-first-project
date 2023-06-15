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
    public void mengeChange() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Current allowed quantity is: " + amount);
        System.out.println("Do you want to change the allowed quantity? (yes/no)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter the new allowed quantity: ");
            int newAmount = scanner.nextInt();

            if (newAmount >= artikels.size()) {
                amount = newAmount;
                System.out.println("The allowed quantity is changed to: " + newAmount);
            } else {
                System.out.println("Error: The new allowed quantity is less than the current number of articles.");
            }
        } else {
            System.out.println("The allowed quantity remains unchanged.");
        }
    }

    public static void sellArtikel(Lager mainLager) {
        Kunde.orderBestand(mainLager);
    }

    public static void reciveBestand(Lager mainLager) {
       Lieferant.addArtikelsFromLieferant(mainLager);
    }



}
