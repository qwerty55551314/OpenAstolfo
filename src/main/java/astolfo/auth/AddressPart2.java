package astolfo.auth;

public class AddressPart2 extends AddressPart {
   private static String B;
   private static final String c = "XFbO";

   public AddressPart2() {
      super(AddressCipher2.decrypt(c));
   }
}
