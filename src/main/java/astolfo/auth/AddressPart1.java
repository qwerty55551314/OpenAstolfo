package astolfo.auth;

public class AddressPart1 extends AddressPart {
   private static final String b = "ak4r";

   public AddressPart1() {
      super(AddressCipher1.decrypt(b));
   }
}
