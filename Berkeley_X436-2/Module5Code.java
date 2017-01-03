public class Module5Code {

  interface Domestic {}
  interface Import {}
  interface Japanese extends Import {}
  interface German extends Import {}
  interface Detroit extends Domestic {}
  interface SpringHill extends Domestic {}
     
  interface Vehicle {}
  interface Automobile extends Vehicle {}
  interface LargeAutomobile extends Vehicle {}
  interface Sedan extends Automobile {}
  interface Van extends LargeAutomobile {}
  interface Truck extends LargeAutomobile {}
  interface Compact extends Automobile {}
  interface SportsUtilityVehicle extends Truck, Van {}
     
  class SaturnSL1 implements SpringHill, Sedan {}
  class HondaCivic implements Japanese, Compact {}
  class MercedesC230 implements German, Sedan {}
  class ChevyS10 implements Detroit, Truck {}
  class SubaruOutback implements Japanese, SportsUtilityVehicle {}

  SaturnSL1 sl = new SaturnSL1();          // #1 OK, #2 OK
  HondaCivic hc = new HondaCivic();        // #1 OK, #2 OK
  Japanese jp = null;                      // #1 Not OK (Japanese is abstract), #2 OK
  German gr = new MercedesC230();          // #1 OK, #2 OK
  ChevyS10 cs = null;                      // #1 Not OK (Japanese is abstract), #2 OK
  SubaruOutback sb = new SubaruOutback();  // #1 OK, #2 OK
  // Domestic dm = sl;
  // dm = null;
  // dm = (Domestic) hc;
  // dm = cs;
  // dm = (Domestic) cs;
  // Import im = sb;
  // gr = im;
  // gr = (German) im;
  // jp = im;
  // jp = (German) im;
  // jp = (Japanese) im;
  // Automobile a = cs;
  // a = hc;

  public static void main(String[] args) {}
}