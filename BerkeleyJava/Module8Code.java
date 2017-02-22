/*
Write a program that will display lines of a text you specify. It should take
two arguments: a file or a URL and the number of lines to display. If the number
of lines given is positive, it should display the first n lines of the text. If
it is 0, it should display all the lines of the text. If it is a negative
number, it should display the last n lines of the text. Your program should
handle errors appropriately.
*/

import java.net.*;
import java.io.*;
import java.util.*;

public class Module8Code {

  public static void displayContent(String fileName, int contentLength) throws IOException {
    URL url = new URL(fileName);
    
    try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
      System.out.println("URL works!");
    } catch (IOException ioe) {
      System.out.println("URL does not work!");
      System.exit(0);
    }

    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    List<String> content = new ArrayList<String>();

    String inputLine;
    while ((inputLine = in.readLine()) != null)
      content.add(inputLine);
    in.close();

    int startIndex = 0;
    int endIndex = content.size();

    if (contentLength > 0) {
      endIndex = Math.min(contentLength, endIndex);
    } else if (contentLength < 0) {
      startIndex = endIndex + contentLength;
    }

    for (int i=startIndex; i<endIndex; i++) {
      System.out.println(content.get(i));
    }    
  }

  public static void main(String[] args) throws IOException {
    displayContent("http://www.economist.com/", 10);
  }
}
