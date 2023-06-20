import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Main {
    public static void main(String[] args) {
        Lager lager = new Lager("mylager");

        Scanner input = null;
        int choice = 0;
        do {
            try {
                System.out.println("1.  new Bestand");
                System.out.println("2.  add Artikle to Lager");
                System.out.println("3.  display artikels in orginal lager");
                System.out.println("4.  add Lager ");
                System.out.println("5.  transfer to another Lager");
                System.out.println("6.  order Bestand");
                System.out.println("7.  display all artikels");
                System.out.println("8.  add Artikels From Lieferant");
                System.out.println("9.  Read file and print on the Console");
                System.out.println("10. creat xml file");
                System.out.println("11. read File and insert data");

                input = new Scanner(System.in);
                choice = input.nextInt();

                switch (choice) {
                    case 1:
                       Lager.checkBestandAndAddArtikels(lager,input);
                       break;

                    case 2:
                        System.out.println("insert artikel number:");
                        String artikelNumber = input.next();

                        System.out.println("insert a description:");
                        input.nextLine(); // استهلاك السطر الجديد الذي تم إدخاله بعد رقم الصنف
                        String artikelDescription = input.nextLine();

                        Artikel newArtikel = new Artikel(artikelNumber, artikelDescription);
                        Lager.addArtikel(newArtikel, lager);
                        System.out.println("----------***-----------");
                        break;
                    case 3:
                        lager.checkBestand();
                        break;

                    case 4:
                        System.out.println("Enter the name of the new Lager:");
                        String lagerName = input.next();
                        lager.addLager(lagerName);
                        System.out.println("New Lager added successfully.");
                        break;

                    case 5:
                        Lager.transferArtikel(lager);
                        break;
                    case 6:
                        Bestand.sellArtikel(lager);
                        break;
                    case 7:
                        System.out.println("Artikels in All Lagers:");
                        lager.printAllArtikels();
                        break;
                    case 8:
                        Bestand.reciveBestand(lager);
                    break;
                    case 9://read
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();

                            Document document = builder.parse(new File("orginal_lager.xml"));

                            Element rootElement = document.getDocumentElement();

                            NodeList nodeList = rootElement.getChildNodes();
                            for (int i = 0; i < nodeList.getLength(); i++) {
                                Node node = nodeList.item(i);
                                if (node.getNodeType() == Node.ELEMENT_NODE) {
                                    Element element = (Element) node;
                                    String tagName = element.getTagName();
                                    String textContent = element.getTextContent();
                                    System.out.println(tagName + ": " + textContent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case 10 :
                        lager.makeXmlFile(lager);

                        break;

                    case 11:
                        try {
                            JFileChooser fileChooser = new JFileChooser();
                            int result = fileChooser.showOpenDialog(null);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                String filePath = selectedFile.getAbsolutePath();

                                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                DocumentBuilder builder = factory.newDocumentBuilder();

                                Document document = builder.parse(new File(filePath));

                                Element rootElement = document.getDocumentElement();

                                NodeList artikelList = rootElement.getElementsByTagName("Artikel");

                                Artikel[] artikels = new Artikel[artikelList.getLength()];

                                for (int i = 0; i < artikelList.getLength(); i++) {
                                    Element artikelElement = (Element) artikelList.item(i);
                                    String artikelNumberf = artikelElement.getElementsByTagName("ArtikelNumber").item(0).getTextContent();
                                    String artikelDescriptionf = artikelElement.getElementsByTagName("ArtikelDescription").item(0).getTextContent();
                                    Artikel artikel = new Artikel(artikelNumberf, artikelDescriptionf);
                                    artikels[i] = artikel;
                                }

                                for (Artikel artikel : artikels) {
                                    lager.artikels.add(artikel);
                                    System.out.println("Artikel Number: " + artikel.getArtikelNumber());
                                    System.out.println("Artikel Description: " + artikel.getBeschreibung());
                                    System.out.println("--------------------");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } while (choice != 20);
    }
}
