package astolfo.hypixel;

import com.google.gson.annotations.SerializedName;
import net.hypixel.api.reply.AbstractReply;

public class KillsDeathsReply extends AbstractReply {
   @SerializedName("kills")
   private long kills;
   @SerializedName("deaths")
   private long deaths;
   private static int q;

   public static void j(int i) {
      q = i;
   }

   public static int x() {
      return q;
   }

   public static int v() {
      return 98;
   }
}
