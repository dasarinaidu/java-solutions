/*
Given the following set of classes, tell me whether the assignment will
compile, and if so, if it will succeed or fail at runtime. For any that fail to
compile, pretend that the right-hand side of that statement is null, and answer
the remaining questions assuming this change has been made.

Add the following declaration to interface Vehicle:

int getWeightInPounds( );

Then add the minumum amount of additional code to make all the classes that
implement Vehicle compile again. Assume that a small car weighs 1,000 pounds,
and a large pickup truck weighs 2,500 pounds. Try to write as little redundant
code as possible. You may introduce new classes if this would help meet this
goal.

Write a class ParkingGarage which simulates a garage for parking Vehicles. The
garage should have have enough capacity to park 20 Vehicles or 25,000 pounds. It
should refuse to park any Vehicle that would push the garage over either of
these limits. Note that ParkingGarage should be a well-behaved class; in
particular, it should be possible to create multiple independent ParkingGarages
in one program.
*/


public class Module5Code {

  // SaturnSL1 sl = new SaturnSL1();          // Compile OK, Run OK
  // HondaCivic hc = new HondaCivic();        // Compile OK, Run OK
  // Japanese jp = null;                      // Compile Not OK (error: Japanese is abstract), Run OK
  // German gr = new MercedesC230();          // Compile OK, Run OK
  // ChevyS10 cs = null;                      // Compile Not OK (error: Japanese is abstract), Run OK
  // SubaruOutback sb = new SubaruOutback();  // Compile OK, Run OK
  // Domestic dm = sl;                        // Compile OK, Run OK
  // // dm = hc;                              // Compile and Run not OK (error: identifier expected)
  // // dm = (Domestic) hc;                   // Compile and Run not OK (error: identifier expected)
  // // dm = cs;                              // Compile and Run not OK (error: identifier expected)
  // // dm = (Domestic) cs;                   // Compile and Run not OK (error: identifier expected)
  // Import im = sb;                          // Compile OK, Run OK
  // // gr = im;                              // Compile and Run not OK (error: identifier expected)
  // // gr = (German) im;                     // Compile and Run not OK (error: identifier expected)
  // // jp = im;                              // Compile and Run not OK (error: identifier expected)
  // // jp = (German) im;                     // Compile and Run not OK (error: identifier expected)
  // // jp = (Japanese) im;                   // Compile and Run not OK (error: identifier expected)
  // Automobile a = null;                     // Compile Not OK (error: cannot convert Chevy to Automobile), Run OK
  // // a = hc;                               // Compile and Run not OK (error: identifier expected)

  interface Domestic {}
  interface Import {}
  interface Japanese extends Import {}
  interface German extends Import {}
  interface Detroit extends Domestic {}
  interface SpringHill extends Domestic {}
     
  interface Vehicle {
    int getWeightInPounds();
  }

  interface Automobile extends Vehicle {   
    default int getWeightInPounds() {
      return 1000;
    }
  }
  interface LargeAutomobile extends Vehicle {
    default int getWeightInPounds() {
      return 2500;
    }
  }

  interface Sedan extends Automobile {}
  interface Van extends LargeAutomobile {}
  interface Truck extends LargeAutomobile {}
  interface Compact extends Automobile {}
  interface SportsUtilityVehicle extends Truck, Van {}

  static class SaturnSL1 implements SpringHill, LargeAutomobile {}
  static class HondaCivic implements Japanese, Compact {}
  static class MercedesC230 implements German, Sedan {}
  static class ChevyS10 implements Detroit, Truck {}
  static class SubaruOutback implements Japanese, SportsUtilityVehicle {}

  public static class Garage {
    int numberVehicles = 0;
    int totalWeight = 0;

    void showNumberVehicle() {
      System.out.println(numberVehicles);
    }

    void showTotalWeight() {
      System.out.println(totalWeight);
    }

    void addVehicle(Vehicle vehicle) {
      int numberVehiclesUpdate = numberVehicles;
      int totalWeightUpdate = totalWeight;

      numberVehiclesUpdate += 1;
      totalWeightUpdate += vehicle.getWeightInPounds();

      if (numberVehiclesUpdate > 20 || totalWeight > 25000) {
        System.out.println("Garage full!");
      } else {
        numberVehicles = numberVehiclesUpdate;
        totalWeight = totalWeightUpdate;
      }
    }

    void emptyGarage() {
      numberVehicles = 0;
      totalWeight = 0;
    }
  }

  public static void main(String[] args) {
    HondaCivic hc = new HondaCivic();
    SaturnSL1 sl = new SaturnSL1();

    Garage garage = new Garage();

    for (int i = 0; i < 25; i++) {
      System.out.println(i);
      garage.addVehicle(hc);
    }

    garage.emptyGarage();

    for (int i = 0; i < 25; i++) {
      System.out.println(i);
      garage.addVehicle(sl);
    }    
  }
}