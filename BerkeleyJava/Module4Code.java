/*
Write a cash register class. It should have the following methods:

AddTransaction(amount) – add an amount to a running total and increments the number of transactions
TransactionCount( ) – returns the number of transactions received
Total( ) – returns the sum of the amounts of the transactions
ResetTransactions( ) – sets the total amount and transaction count to 0
Register Count( ) – returns the number of cash registers created

Next, write an application that instantiates at least two cash register objects
to prove that your class works.
*/

public class Register {

  private int numberTransactions;
  private double runningTotal;
  public static int numberRegisters = 0;

  public Register() {
    numberTransactions = 0;
    runningTotal = 0;
    numberRegisters++;
  }

  private void AddTransaction(double amount) {
    numberTransactions++;
    runningTotal += amount;
  }

  private int TransactionCount() {
    return numberTransactions;
  }

  private double Total() {
    return runningTotal;
  }

  private void ResetTransactions() {
    numberTransactions = 0;
    runningTotal = 0;
  }

  public static int Count() {
    return numberRegisters;
  }
  
  public static void main(String[] argv) {
    Register fruits = new Register();
    Register vegetables = new Register();
    System.out.println(Register.Count());

    fruits.AddTransaction(0.99);
    System.out.println(fruits.TransactionCount());
    System.out.println(fruits.Total());

    vegetables.AddTransaction(1.99);
    vegetables.AddTransaction(1.99);
    System.out.println(vegetables.TransactionCount());
    System.out.println(vegetables.Total());

    fruits.ResetTransactions();
    System.out.println(fruits.TransactionCount());
    System.out.println(fruits.Total());
  }
}
