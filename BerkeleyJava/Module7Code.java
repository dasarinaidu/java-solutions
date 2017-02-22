/*
Part 1. Modify the CharStack class from the Module 4 commentary, adding explicit
error reporting for stack underflow via a checked exception (i.e., one that must
be declared and caught). Provide a main program that tests this new capability.
*/

// StackUnderFlowException.java
public class StackUnderFlowException extends Exception
{
  public StackUnderFlowException(String message)
  { 
    super(message);
  }
}

// CharStack.java
import java.io.*;

public class CharStack {
  private char[] m_data;
  private int m_ptr;

  public CharStack(int size) {
    m_ptr = 0;
    m_data = new char[(size > 1 ? size : 10)];
  }

  public void push(char c) {
    if (m_ptr >= m_data.length) {
       char[] tmp = new char[m_data.length * 2];
       System.arraycopy(m_data, 0, tmp, 0, m_data.length);
       m_data = tmp;
    }
    m_data[m_ptr++] = c;
  }

  public char pop() throws StackUnderFlowException {
    if (m_ptr == 0) {
      throw new StackUnderFlowException("Stack is empty!");
    }

    return m_data[--m_ptr];
  }

  public boolean hasMoreElements() {
    return (m_ptr != 0);
  }

  public static void main(String[] argv) throws StackUnderFlowException {
    CharStack s = new CharStack(10);
    int i = 0;

    while (i < 10) {
      s.push((char) i);
      i++;
    }

    while (true) {
      try {
        System.out.write(s.pop());
        i--;
      } catch (StackUnderFlowException spe) {
        System.out.println("Stack is empty!");
        break;
      }
    }
  }
}


/*
Part 2. Define a class representing an inflatable balloon. Balloon should have a
method inflate( ) (which can be called many times) and a maximum inflation
pressure. It should act appropriately if the Balloon is overinflated. Since
Balloon is a proper Java class, it shouldn't be possible to change the pressure
without calling inflate( )!
*/

// MaximumPressureException.java
public class MaximumPressureException extends Exception
{
  public MaximumPressureException(String message)
  { 
    super(message);
  }
}

// Balloon.java
public class Balloon {

  private int pressure;
  private final int maximumPressure;

  public Balloon(int maximumPressure) {
    this.pressure = 0;
    this.maximumPressure = maximumPressure;
  }

  public void inflate() throws MaximumPressureException {
    if (this.pressure == this.maximumPressure) {
      throw new MaximumPressureException("Balloon maximum pressure reached!");
    }
    
    this.pressure++;
  }

  public int showPressure() {
    return this.pressure;
  }

  public static void main(String[] args) throws MaximumPressureException {
    Balloon balloon = new Balloon(10);

    for (int i=0; i<20; i++) {
      try {
        balloon.inflate();
      } catch (MaximumPressureException mpe) {
        System.out.println("Balloon maximum pressure reached at " 
            + balloon.showPressure() + " units.");
        break;
      }
    }
  }
}


/*
Part 3. Write a short program that creates two threads, one of which
successively sets a variable to the integers from 1 to 10, and another that
reads the values, printing each one as it goes. Use synchronized methods, wait()
and notify(). Use a separate condition variable to signify that the integer
variable is empty. Since the whole point of the exercise is to make sure that
every written value is read, without any values being skipped or overwritten,
pay special attention to access control. Look at the sample code in Section 7 of
this module's commentary for an idea of how to proceed.
*/

// Cultivator.java
class Cultivator extends java.lang.Thread {
  private String identifier = "Cultivator";
  private int maxIteration = 10;
  private int iteration = 0;
  private String value = "";
 
  public Cultivator(int maxIteration) {
    this.maxIteration = maxIteration;
  }

  public void run() {
    try {
      while (this.iteration < this.maxIteration) {
        this.iteration++;        
        assignValue(this.identifier, this.iteration);
      }
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
  }
 
  private synchronized void assignValue(String identifier, int iteration) 
      throws InterruptedException {
    // System.out.println(identifier + " assignment start for iteration "
    //     + String.valueOf(iteration) + "...");
    while (!this.value.equals("")) {
      wait();
    }
    this.value = String.valueOf(this.iteration);
    notify();
    // System.out.println(identifier + " assignment end for iteration " 
    //     + String.valueOf(iteration) + "...");
  }
 
  public synchronized String resetValue(String identifier, int iteration)
      throws InterruptedException {
    // System.out.println(identifier + " reset start for iteration "
    //     + String.valueOf(iteration) + "...");    
    notify();
    while (this.value.equals("")) {
      wait();
    }
    String result = this.value;
    this.value = "";
    // System.out.println(identifier + " reset ends for iteration "
    //     + String.valueOf(iteration) + "...");
    return result;
  }
}

// Harvester.java
class Harvester extends java.lang.Thread {
  private String identifier = "Harvester";  
  private int maxIteration = 10;
  private int iteration = 0;

  Cultivator cultivator;

  Harvester(Cultivator cultivator, int maxIteration) {
    this.cultivator = cultivator;
    this.maxIteration = maxIteration;
  }
 
  public void run() {
    try {
      while (this.iteration < this.maxIteration) {
        this.iteration++;
        String value = cultivator.resetValue(this.identifier, this.iteration);
        System.out.println("Current value: " + value);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
 
  public static void main(String args[]) {
    int maxIteration = 10;

    Cultivator cultivator = new Cultivator(maxIteration);
    cultivator.start();
    new Harvester(cultivator, maxIteration).start();
  }
}
