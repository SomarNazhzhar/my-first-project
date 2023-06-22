import java.util.ArrayList;
import java.util.Scanner;

public class Kunde implements CRUDoperations<Kunde>{
    private String kunde_id;
    private String kunde_name;
    private static ArrayList <Kunde> customers = new ArrayList<>();

    public Kunde(String kunde_id, String kunde_name) {
        this.kunde_id = kunde_id;
        this.kunde_name = kunde_name;
    }
    public Kunde(){

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
        Kunde kunde = new Kunde(customerNumber,customerName);
        customers.add(kunde);
        System.out.println("choose Artikel Number");
        String artikelNumber = input.next();
        input.nextLine();

        System.out.println("choose Lager Name:(The name of the orginal lager is (mylager)");
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
                System.out.println("Buy? (true/ false )");
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

    public static void printCustomers() {
        System.out.println("Customers:");
        for (Kunde customer : customers) {
            System.out.println(customer.getKunde_name());
        }
        System.out.println("------------------------");
    }
    public static void updateKunde(Kunde customer) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter new customer name:");
        String newName = input.nextLine();

        System.out.println("Enter new customer number:");
        String newNumber = input.nextLine();

        customer.setKunde_name(newName);
        customer.setKunde_id(newNumber);
        System.out.println("Customer updated successfully.");
        System.out.println("New customer name: " + customer.getKunde_name());
        System.out.println("New customer number: " + customer.getKunde_id());

    }
    public static Kunde findCustomerByName(String name) {
        for (Kunde customer : customers) {
            if (customer.getKunde_name().equals(name)) {
                return customer;
            }
        }
        return null;
    }


    @Override
    public void update(Kunde item) {
    updateKunde(item);
    }

    @Override
    public void delete(String id) {

    }
}
