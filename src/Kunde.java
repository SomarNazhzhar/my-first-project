import java.util.Scanner;

public class Kunde implements CRUDoperations<Kunde>{
    private String kunde_id;
    private String kunde_name;

    public Kunde(String kunde_id, String kunde_name) {
        this.kunde_id = kunde_id;
        this.kunde_name = kunde_name;
    }

    public String getKunde_id() {
        return kunde_id;
    }

    public void setKunde_id(String kunde_id) {
        this.kunde_id = kunde_id;
    }

    public String getKunde_name() {
        return kunde_name;
    }

    public void setKunde_name(String kunde_name) {
        this.kunde_name = kunde_name;
    }

    public static void orderBestand(Lager mainLager) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter customer name:");
        String customerName = input.nextLine();

        System.out.println("Enter customer number:");
        String customerNumber = input.nextLine();

        System.out.println("Artikels List");
        mainLager.printBestand();

        System.out.println("choose Artikel Number");
        String artikelNumber = input.next();
        input.nextLine();

        System.out.println("choose Lager Name:");
        String lagerName = input.nextLine();

        Lager selectedLager = Lager.findLager(lagerName, mainLager);

        if (selectedLager != null) {
            Artikel selectedArtikel = null;

            for (Artikel artikel : selectedLager.artikels) {
                if (artikel.getArtikelNumber().equals(artikelNumber)) {
                    selectedArtikel = artikel;
                    break;
                }
            }

            if (selectedArtikel != null) {
                System.out.println("Artikel Number: " + selectedArtikel.getArtikelNumber());
                System.out.println("Lager name: " + selectedLager.getLagerName());
                System.out.println("Buy?");
                boolean kauf = input.nextBoolean();
                if (kauf) {
                    Kunde customer = new Kunde(customerNumber, customerName);
                    System.out.println("Customer: " + customer.getKunde_name() + ", " + customer.getKunde_id());
                    System.out.println("You selected Artikel " + selectedArtikel.getArtikelNumber());
                    System.out.println("From the Lager " + selectedLager.getLagerName());
                    selectedLager.artikels.remove(selectedArtikel);
                } else {
                    System.out.println("Purchase canceled.");
                }
            } else {
                System.out.println("Artikel not found!");
            }
        } else {
            System.out.println("Lager not found!");
        }
    }    /////////////////////////////////////////////////////////////////////









    @Override
    public void create(Kunde item) {

    }

    @Override
    public Kunde read(String id) {
        return null;
    }

    @Override
    public void update(Kunde item) {

    }

    @Override
    public void delete(String id) {

    }
}
