package astolfo.user;

public class AstolfoUser {
   public String clientName;
   public String nameHash;
   public AstolfoUserStatus status;
   private static String q;

   public AstolfoUser(String string, String string2, AstolfoUserStatus astolfoUserStatus) {
      this.clientName = string;
      this.nameHash = string2;
      this.status = astolfoUserStatus;
   }
}
