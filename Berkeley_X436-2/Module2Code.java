import java.io.*;


public class Module2Code {

  public static void main(String[] argv) throws IOException {

    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);

    // requests array length and inputs 
    System.out.println("How many strings?:");
    int n = Integer.parseInt(stdin.readLine());
    String [] strings = new String[n];

    for (int i=1; i<n+1; i++) {
      System.out.println("Word " + i + ":");
      strings[i-1] = stdin.readLine();
    }

    // creates array of scores based on comparisons between inputs
    int [] scores = new int[n];

    for (int i=0; i<n; i++) {
      int score = 0;

      for (int j=0; j<n; j++) {
        if (strings[i].compareTo(strings[j]) > 0) {
          score += 1;
        }
      }

      scores[i] = score;
    }

    // creates a reverse list
    int [] indices = new int[n];
    for (int i=0; i<n; i++) indices[scores[i]] = i;

    // prints out inputs in increasing order
    System.out.println("\nSorted inputs:");
    for (int i=0; i<n; i++) System.out.println(strings[indices[i]]);   
  }
}