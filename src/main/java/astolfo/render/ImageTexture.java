package astolfo.render;

import java.awt.image.BufferedImage;
import net.minecraft.util.ResourceLocation;

public class ImageTexture {
   public ResourceLocation location;
   public BufferedImage image;
   public int width;
   public int height;
   private static String[] l;

   public ImageTexture() {
   }

   public ImageTexture(BufferedImage bufferedImage) {
      this.image = bufferedImage;
   }

   public static void u(String[] stringArray) {
      l = stringArray;
   }

   public static String[] J() {
      return l;
   }
}
