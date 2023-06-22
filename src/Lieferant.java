import java.util.ArrayList;
import java.util.Scanner;

public class Lieferant implements CRUDoperations<Lieferant> {
    public String lieferant_name;
    private ArrayList<Artikel> artikels;
    public ArrayList<Lieferant> lieferanten;
    private static ArrayList <Lieferant> lieferanten2 = new ArrayList<>();


    public Lieferant(String lieferant_name, ArrayList<Artikel> artikels, ArrayList<Lieferant>lieferanten) {
        this.lieferant_name = lieferant_name;
        this.artikels = artikels;
        this.lieferanten = new ArrayList<>();

    }
    public Lieferant(){

    }

    public String getLieferant_name() {
        return lieferant_name;
    }

    public void setLieferant_name(String lieferant_name) {
        this.lieferant_name = lieferant_name;
    }

    public ArrayList<Artikel> getArtikels() {
        return artikels;
    }

    public void setArtikels(ArrayList<Artikel> artikels) {
        this.artikels = artikels;
    }

    public static void addArtikelsFromLieferant(Lager mainLager) {
        Scanner input = new Scanner(System.in);


        System.out.println("Enter Lieferant name:");
        String lieferantName = input.nextLine();


        System.out.println("Insert Artikel Number:");
        String artikelNumber = input.next();
        input.nextLine();

        System.out.println("Insert a description:");
        String artikelDescription = input.nextLine();

        System.out.println("insert an Amount:");
        int amount = input.nextInt();
        for (int i = 0; i < amount; i++) {
            Artikel newArtikel = new Artikel(artikelNumber, artikelDescription);
            mainLager.create(newArtikel);
        }
        Lieferant newLieferant = new Lieferant(lieferantName, new ArrayList<>(),new ArrayList<>());
        newLieferant.create(newLieferant);
        mainLager.setCurrentLieferant(newLieferant);
        lieferanten2.add(newLieferant);


        System.out.println("Added by Lieferant: " + lieferantName);
        System.out.println("----------***-----------");
    }


    @Override
    public void create(Lieferant lieferant) {

        lieferanten.add(lieferant);
    }

    @Override
    public Lieferant read(String id) {
        return null;
    }

    @Override
    public void update(Lieferant item) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("insert the new Lieferant name : ");
        String l_name_new = scanner.nextLine();
        item.setLieferant_name(l_name_new);

        System.out.println("Lieferant updated successfully.");
        System.out.println("New customer name: " + item.getLieferant_name());

    }

    @Override
    public void delete(String id) {

    }

    public static void printLieferanten() {
        System.out.println("lieferanten : ");
        for (Lieferant lieferant : lieferanten2) {
            System.out.println(lieferant.getLieferant_name());
        }
    }


    public static Lieferant findLieferantByName(String name) {
        for (Lieferant lieferant : lieferanten2) {
            if (lieferant.getLieferant_name().equals(name)) {
                return lieferant;
            }
        }
        return null;
    }
}
