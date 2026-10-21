package astolfo.notification;

import astolfo.render.ClientColor;
import java.awt.Color;
import net.minecraft.util.ResourceLocation;

public enum NotificationType {
   SUCCESS(ClientColor.GREEN.getColor(), -13710223, new ResourceLocation("contexts/SUCCESS.png")),
   INFO(ClientColor.BLUE.getColor(), -5592406, new ResourceLocation("contexts/INFO.png")),
   WARNING(ClientColor.ORANGE.getColor(), -932849, new ResourceLocation("contexts/WARNING.png")),
   DANGER(ClientColor.RED.getColor(), -4179669, new ResourceLocation("contexts/DANGER.png"));

   public final int color;
   public final ResourceLocation texture;
   public final Color consoleColor;
   private static final NotificationType[] VALUES = new NotificationType[]{SUCCESS, INFO, WARNING, DANGER};

   private NotificationType(Color color, int i2, ResourceLocation resourceLocation) {
      this.consoleColor = color;
      this.color = i2;
      this.texture = resourceLocation;
   }
}
