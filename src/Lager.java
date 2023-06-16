import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Lager implements CRUDoperations<Artikel> {
    private String lagerName;
    public List<Artikel> artikels;
    private List<Lager> subLagers;
    private List<Lieferant> lieferants;
    private Lieferant currentLieferant;
    private int amount;


    public Lager(String lagerName) {
        this.lagerName = lagerName;
        this.artikels = new ArrayList<>();
        this.subLagers = new ArrayList<>();
    }


    public void addLager(String lagerName) {
        Lager newLager = new Lager(lagerName);
        subLagers.add(newLager);
    }

    public void checkBestand() {                                                                    //////
        if (artikels.isEmpty()) {
            System.out.println("The Lager is Empty!!!");
        } else {
            System.out.println("we have:");
            int count = 1;
            for (Artikel artikel : artikels) {
                System.out.print("[ID: " + count + ", Artikelnummer: " + artikel.getArtikelNumber() +
                        ", Beschreibung: " + artikel.getBeschreibung() + "]");
                count++;
            }

            System.out.println("");
            int x = count - 1;
//          System.out.println(">>>"+"["+x +"]"+"stk" +", "+ "added by Liferant :"+lieferantName );
            System.out.println(x + "stk");
            System.out.print("the name of the lagers :");
            System.out.print("[1- mylager]");
            int c = 2;
            for (Lager lager : subLagers) {
                System.out.print("[" + c + ": " + lager.getLagerName() + "]");
                c++;
            }
            System.out.println("");
            System.out.println("-----------***----------");
        }
    }

    public static void transferArtikel(Lager mainLager) throws CloneNotSupportedException {
        Scanner input = new Scanner(System.in);


        System.out.println("insert artikel number:");
        String artikelNumber = input.next();
        input.nextLine();


        System.out.println("insert original lager name:");
        String originalLagerName = input.nextLine();


        System.out.println("insert target lager name:");
        String targetLagerName = input.nextLine();

        Lager originalLager = findLager(originalLagerName, mainLager);
        Lager targetLager = findLager(targetLagerName, mainLager);

        if (originalLager != null && targetLager != null) {
            Artikel artikelToTransfer = null;


            for (Artikel artikel : originalLager.artikels) {
                if (artikel.getArtikelNumber().equals(artikelNumber)) {
                    artikelToTransfer = artikel;
                    break;
                }
            }

            if (artikelToTransfer != null) {

                System.out.println("wie viel");
                int numberOfArtikels = input.nextInt();

                if (numberOfArtikels <= originalLager.artikels.size()) {
                    int counter = 0;

                    for (Artikel artikel : originalLager.artikels) {
                        if (counter == numberOfArtikels) {
                            break;
                        }

                        Artikel artikelClone = artikel.clone();
                        targetLager.artikels.add(artikelClone);
                        counter++;
                    }


                    originalLager.artikels.subList(0, counter).clear();

                    System.out.println("transfer successfully");


                    System.out.println("in lager " + originalLager.getLagerName() + ":");
                    originalLager.printBestand();

                    System.out.println("in lager" + targetLager.getLagerName() + "):");
                    targetLager.printBestand();
                } else {
                    System.out.println("the amount are big !!!");
                }

                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.newDocument();


                    Element lagerElement = doc.createElement("Lager");
                    doc.appendChild(lagerElement);


                    Element lagerNameElement = doc.createElement("Name");
                    lagerNameElement.appendChild(doc.createTextNode(targetLager.getLagerName()));
                    lagerElement.appendChild(lagerNameElement);


                    for (Artikel artikel : targetLager.artikels) {
                        Element artikelElement = doc.createElement("Artikel");
                        lagerElement.appendChild(artikelElement);

                        Element artikelNumberElement = doc.createElement("ArtikelNumber");
                        artikelNumberElement.appendChild(doc.createTextNode(artikel.getArtikelNumber()));
                        artikelElement.appendChild(artikelNumberElement);

                        Element artikelDescriptionElement = doc.createElement("ArtikelDescription");
                        artikelDescriptionElement.appendChild(doc.createTextNode(artikel.getBeschreibung()));
                        artikelElement.appendChild(artikelDescriptionElement);
                    }


                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File("newfile.xml"));
                    transformer.transform(source, result);

                    System.out.println("XML file created successfully.");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("artikel not found!!!");
            }
        } else {
            System.out.println("Lager not found!!!!");
        }
    }

    public static Lager findLager(String lagerName, Lager mainLager) {
        if (mainLager.getLagerName().equals(lagerName)) {
            return mainLager;
        } else {
            for (Lager lager : mainLager.getSubLagers()) {
                if (lager.getLagerName().equals(lagerName)) {
                    return lager;
                }
            }
        }
        return null;
    }


    public void printAllArtikels() {
        if (artikels.isEmpty() && subLagers.isEmpty()) {
            System.out.println("No artikels found in any lagers.");

            return;
        }

        if (!artikels.isEmpty()) {
            System.out.println("Artikels in " + lagerName + ":");
            printBestand();
            System.out.println("");
            System.out.println("-------------------------");
        }

        if (!subLagers.isEmpty()) {
            for (Lager subLager : subLagers) {
                System.out.println("Artikels in " + subLager.getLagerName() + ":");
                subLager.printAllArtikels();
                System.out.println("-------------------------");
            }
        }
    }

    public void makeXmlFile(Lager mainLager){
        Scanner input = new Scanner(System.in);
        System.out.println("insert lager name:");
        String originalLagerName = input.nextLine();

        System.out.println("insert the name of the File:");
        String nameOfTheFile = input.nextLine();

        Lager originalLager = findLager(originalLagerName, mainLager);


        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element lagerElement =doc.createElement("lager");
            doc.appendChild(lagerElement);

            Element lagerNameElement = doc.createElement("lager_name");
            lagerNameElement.appendChild(doc.createTextNode(getLagerName()));
            lagerElement.appendChild(lagerNameElement);

            for (Artikel artikel : originalLager.artikels) {
                Element artikelElement = doc.createElement("Artikel");
                lagerElement.appendChild(artikelElement);

                Element artikelNumberElement = doc.createElement("ArtikelNumber");
                artikelNumberElement.appendChild(doc.createTextNode(artikel.getArtikelNumber()));
                artikelElement.appendChild(artikelNumberElement);

                Element artikelDescriptionElement = doc.createElement("ArtikelDescription");
                artikelDescriptionElement.appendChild(doc.createTextNode(artikel.getBeschreibung()));
                artikelElement.appendChild(artikelDescriptionElement);
            }
            // حفظ الملف XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nameOfTheFile+ ".xml"));
            transformer.transform(source, result);

            System.out.println("XML file created successfully.");




        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    public void setCurrentLieferant(Lieferant lieferant) {
        this.currentLieferant = lieferant;
    }
    public String getLagerName() {
        return lagerName;
    }

    public static void addArtikel(Artikel artikel, Lager mainLager) {
        Scanner input = new Scanner(System.in);
        System.out.println("in welchem lager?");
        String lagerToInsert = input.nextLine();
        Lager targetLager = null;

        if (mainLager.getLagerName().equals(lagerToInsert)) {
            targetLager = mainLager;
        } else {

            for (Lager lager : mainLager.getSubLagers()) {
                if (lager.getLagerName().equals(lagerToInsert)) {
                    targetLager = lager;
                    break;
                }
            }
        }

        if (targetLager != null) {
            System.out.println("was ist der Artikel Nummer");
            String artikelNumber = input.nextLine();
            boolean exists = false;
            for (Artikel existingArtikel : targetLager.artikels) {
                if (existingArtikel.getArtikelNumber().equals(artikelNumber)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("du hast schon einfugt");
            } else {
                targetLager.artikels.add(artikel);
                System.out.println("added successfully");
                System.out.println("lager name: " + targetLager.getLagerName());
                System.out.println("das Artikel " + artikel.getArtikelNumber());
            }
        } else {
            System.out.println("es gibt kein lager!!!");
        }
    }

    public void printBestand() {
        if (artikels.isEmpty()) {
            System.out.println("The Lager is Empty!!!");
        } else {
            System.out.println("we have:");
            int count = 1;
            for (Artikel artikel : artikels) {
                System.out.print("[ID: " + count + ", Artikelnummer: " + artikel.getArtikelNumber() +
                        ", Beschreibung: " + artikel.getBeschreibung() + "]");
                count++;
            }
            System.out.println("");
            System.out.println("stock :" + (count - 1));
        }
    }

    public List<Lager> getSubLagers() {
        return subLagers;
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    public static void checkBestandAndAddArtikels(Lager lager, Scanner input) {

        System.out.println("insert artikel number:");
        String a_number = input.next();
        System.out.println("insert a description:");
        String a_description = input.next();
        System.out.println("insert an Amount:");
        int amount = input.nextInt();
        int x = lager.artikels.size() + amount;
        if (x > 100){
            System.out.println("lager ist full !!! du kannst jetzt nur :"+" "+ (100 -lager.artikels.size()) + " stock einfugen");
            System.out.println("möschten sie die mänge ändern ? true/ false");
            boolean choice = input.nextBoolean();
            if (choice == true){
                System.out.println("lager size ???" + "jetzt: "+ lager.artikels.size() + "/ du brauchst :"+
                        (lager.artikels.size()+ amount) );
                int lagerSize = input.nextInt();
                int lagerAfterEdit = lagerSize - lager.artikels.size();
                for (int i = 0; i <lagerAfterEdit; i++) {
                    Artikel artikel1 = new Artikel(a_number, a_description);
                    lager.create(artikel1);
                    System.out.println(artikel1.toString());
                }

            }else {
                System.out.println("try another time");
            }
        } else {
            for (int i = 0; i < amount; i++) {
                System.out.print("[" + (i + 1) + " :");
                Artikel artikel1 = new Artikel(a_number, a_description);
                lager.create(artikel1);
                System.out.println(artikel1.toString());
            }
            System.out.println("Artikel nummer : " + a_number + ">>>[" + amount + "]" + "stk");
            System.out.println("successfully");
            System.out.println(x);
            System.out.println("---------------------------------");
        }
    }







    @Override
    public void create(Artikel item) {
        artikels.add(item);

    }


    @Override
    public Artikel read(String id) {
        for (Artikel artikel : artikels) {
            if (artikel.getArtikelNumber().equals(id)) {
                return artikel;
            }
        }
        return null;
    }

    @Override
    public void update(Artikel item) {
        for (int i = 0; i < artikels.size(); i++) {
            Artikel existingItem = artikels.get(i);
            if (existingItem.getArtikelNumber().equals(item.getArtikelNumber())) {
                artikels.set(i, item);
                System.out.println("updated");
                break;
            }
        }
    }

    @Override
    public void delete(String id) {
        for (int i = 0; i < artikels.size(); i++) {
            Artikel artikel = artikels.get(i);
            if (artikel.getArtikelNumber().equals(id)) {
                artikels.remove(i);
                System.out.println("deleted!!");
                break;
            }
        }
    }



    public void changeSize(Lager mylager) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Current allowed quantity is: " + amount);
        System.out.println("Do you want to change the allowed quantity? (yes/no)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter the new allowed quantity: ");
            int newAmount = scanner.nextInt();

            // حساب عدد العناصر التي تمت إضافتها حديثًا
            int addedItems = artikels.size();

            while (newAmount < (amount + addedItems)) {
                System.out.println("Error: The new allowed quantity is less than the current number of articles plus the added items.");
                System.out.println("Enter a valid new allowed quantity: ");
                newAmount = scanner.nextInt();
            }

            amount = newAmount;
            System.out.println("The allowed quantity is changed to: " + newAmount);
        } else {
            System.out.println("The allowed quantity remains unchanged.");
        }
    }

    public void setSize(int newSize) {

        if (newSize < artikels.size()) {
            System.out.println("Error: The new size should be greater than or equal to the current number of elements.");
        } else {
            while (artikels.size() < newSize) {
                artikels.add(null);
            }
            System.out.println("Lager size changed to " + newSize);
        }
    }

}