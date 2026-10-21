package astolfo.auth.packet;

public class LoginPacket {
   public String version;
   public String username;
   public String password;
   public String hwid;
   public String token;

   public LoginPacket(String string, String string2, String string3, String string4, String string5) {
      this.username = string2;
      if (null != null) {
         this.password = string3;
         this.version = string;
         this.hwid = string4;
         this.token = string5;
         int i = 0;
         i++;
      } else {
         this.password = string3;
         this.version = string;
         this.hwid = string4;
         this.token = string5;
      }
   }
}
