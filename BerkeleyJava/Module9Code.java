/*
Hollywood likes to make remakes of movies. Unfortunately, this means our DVD
collection may have duplicate titles of movies that were released in different
years. We will use the dvd.xml and dvd.dtd presented in the commentary.

To distinguish these remakes from the original, we will need to add a new field
called release_year and modify the dvd.dtd accordingly.

Afterwards, write an XML parser (either in DOM or StAX) that will count the
number of movies released by decade.

Add a few more DVD entries to our DVD collection XML file collection to verify
your code works.
*/

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;


public class DVD {
  private String id;
  private String title;
  private String performers;
  private String discs;
  private String price;
  private String released;

  public DVD(String id, String title, String performers, String discs,
      String price, String released) {
    this.id = id;
    this.title = title;
    this.performers = performers;
    this.discs = discs;
    this.price = price;
    this.released = released;
  }

  public static List<DVD> collection = new ArrayList<DVD>();
  public static List<String> performersList = new ArrayList<String>();

  public static void buildCollection(String fileName) {
    try {
      File file = new File(fileName);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setValidating(true);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document document = db.parse(file);
      document.getDocumentElement().normalize();

      NodeList nodeList =  document.getDocumentElement().getChildNodes();
      exposeNode(nodeList);
      System.out.println();

    } catch (SAXException saxe) {
      saxe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    }
  }

  public static void exposeNode(NodeList nodeList) {

    Map<String, String> DVDContent = new HashMap<String, String>();

    for (int i=0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);

      if (node.getNodeType() == Node.ELEMENT_NODE) {
        DVDContent.put(node.getNodeName(), node.getTextContent());
        if (node.getNodeName() == "performer") {
          performersList.add(node.getTextContent().replaceAll(" +", " ") + ",");
        }
        if (DVDContent.size() == 4) {
          String performersComplete = "";
          for (String performer : performersList) {
            performersComplete += performer;
          }

          System.out.println("Adding " + DVDContent.get("title") + "...");
          collection.add(new DVD("1", DVDContent.get("title").replaceAll(" +", " "), 
              performersComplete, DVDContent.get("discs"),
              DVDContent.get("price"), "2000"));
          DVDContent.clear();
          performersList.clear();
        }

        if (node.hasChildNodes()) {
          exposeNode(node.getChildNodes());
        }
      }
    }
  }

  public static void reviewCollection() {
    System.out.println();    
    for (DVD dvd : collection) {
      System.out.println("<DVD>");
      System.out.println("<details>");
      System.out.println("  <id>" + dvd.id + "</id>");
      System.out.println("  <title>" + dvd.title + "</id>");
      System.out.println("  <performers>" + dvd.performers + "</id>");
      System.out.println("  <discs>" + dvd.discs + "</id>");
      System.out.println("  <price>" + dvd.price + "</id>");
      System.out.println("  <released>" + dvd.released + "</id>");
      System.out.println("</DVD>");
      System.out.println("</details>");
    }
    System.out.println();
  }

  public static void resetReleasedDates() {
    List<DVD> newCollection = new ArrayList<DVD>();
    Scanner scanner = new Scanner(System.in);
    String input;

    for (int i=0; i<collection.size(); i++) {
      DVD dvd = collection.get(i);
      System.out.println("Enter release year for " + dvd.title + ":");
      input = scanner.nextLine();
      newCollection.add(new DVD(String.valueOf(i+1), dvd.title, dvd.performers,
          dvd.discs, dvd.price, input));
    }

    collection = newCollection;
  }

  public static void addDVD(DVD dvd) {
    System.out.println("Adding " + dvd.title + "...");
    collection.add(new DVD(String.valueOf(collection.size()+1), dvd.title, 
        dvd.performers, dvd.discs, dvd.price, dvd.released));
  }

  public static void generateStatistics() {
    Map<String, Integer> statistics = new TreeMap<String, Integer>();

    for (DVD dvd : collection) {
      Integer decade = Integer.valueOf(dvd.released);
      decade = decade - decade % 10;
      String decadeKey = String.valueOf(decade);

      if (statistics.containsKey(decadeKey)) {
        statistics.put(decadeKey, statistics.get(decadeKey)+1);
      } else {
        statistics.put(decadeKey, 1);
      }
    }

    System.out.println("\n<DVD>");
    System.out.println("<summary>");
    for (String decadeKey : statistics.keySet()) {
      System.out.println("  <count decade=\"" + decadeKey + "\">" 
          + statistics.get(decadeKey) + "</count>");
    }
    System.out.println("</DVD>");
    System.out.println("</summary>\n");
  }

  public static void main(String[] args) {
    buildCollection("dvd.xml");
    resetReleasedDates();
    reviewCollection();

    addDVD(new DVD("", "Lord of War", "Nicolas Cage, Ethan Hawke, Jared Leto,", "1", "9.99", "2005"));
    addDVD(new DVD("", "Interstellar", "Matthew McConaughey, Anne Hathaway, Jessica Chastain", "2", "8.99", "2014"));
    addDVD(new DVD("", "Whiplash", "Miles Teller, J.K. Simmons,", "1", "7.99", "2014"));
    generateStatistics();
  }
}
