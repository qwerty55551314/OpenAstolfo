package astolfo.auth;

public class AddressPart4 extends AddressPart {
   private static final String b = "BAM=";

   public AddressPart4() {
      super(AddressCipher4.decrypt(b));
   }
}
