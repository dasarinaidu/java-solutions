import java.io.*;


public class Module3Code {
  
  public static String reverse(String text) {
    // calculates index of last character
    int index = text.length()-1;

    // stopping condition if text consists of single character
    if (index == 0) {
      return text;
    // otherwise move last character to the front and apply function recursively on remainder
    } else {
      return text.charAt(index) + reverse(text.substring(0, index));
    }
  }
  
  public static void main(String[] argv) throws IOException {

    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);

    // requests input string
    System.out.println("Please enter input string:");
    String text = stdin.readLine();

    // applies function and prints result
    System.out.println("Result:");
    String result = reverse(text);
    System.out.println(result);
  }
}