package astolfo.auth.packet;

public class LoginResponse {
   public boolean success;
   public String message;
   private static int[] f;

   public LoginResponse(boolean bl, String string) {
      this.success = bl;
      this.message = string;
   }

   public static void M(int[] iArray) {
      f = iArray;
   }

   public static int[] m() {
      return f;
   }
}
