package astolfo.auth;

public class AddressPart3 extends AddressPart {
   private static final String b = "QptytQ==";

   public AddressPart3() {
      super(AddressCipher3.decrypt(b));
   }
}
