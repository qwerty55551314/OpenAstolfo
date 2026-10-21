package astolfo.render;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public final class GlHelper {
   public static List<Integer> textureIds = new ArrayList<>();
   public static List<Integer> bufferIds = new ArrayList<>();
   private static int savedMouseX;
   private static int savedMouseY;
   private static final Random random = new Random();

   private GlHelper() {
   }

   public static void scissorRect(int[] ints) {
      scissor((float)ints[0], (float)ints[1], (float)(ints[0] + ints[2]), (float)(ints[1] + ints[3]));
   }

   public static void scissor(float f, float g, float h, float f2) {
      int i = getScaleFactor();
      GL11.glScissor(
         (int)(f * (float)i), (int)((float)Minecraft.getMinecraft().displayHeight - f2 * (float)i), (int)((h - f) * (float)i), (int)((f2 - g) * (float)i)
      );
   }

   public static int genTexture() {
      int i = GL11.glGenTextures();
      textureIds.add(i);
      return i;
   }

   public static int uploadImageFile(int i, File file, int j, int k) throws IOException {
      uploadImage(i, ImageIO.read(file), j, k);
      return i;
   }

   public static int uploadImage(int i, BufferedImage bufferedImage, int j, int k) {
      int[] aint = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
      bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), aint, 0, bufferedImage.getWidth());
      ByteBuffer bytebuffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * 4);

      for (int i1 = 0; i1 < bufferedImage.getHeight(); i1++) {
         for (int j1 = 0; j1 < bufferedImage.getWidth(); j1++) {
            int k1 = aint[i1 * bufferedImage.getWidth() + j1];
            bytebuffer.put((byte)(k1 >> 16 & 0xFF));
            bytebuffer.put((byte)(k1 >> 8 & 0xFF));
            bytebuffer.put((byte)(k1 & 0xFF));
            bytebuffer.put((byte)(k1 >> 24 & 0xFF));
         }
      }

      ((Buffer)bytebuffer).flip();
      uploadTextureData(i, bufferedImage.getWidth(), bufferedImage.getHeight(), bytebuffer, j, k);
      return i;
   }

   public static int uploadTextureData(int i, int j, int k, ByteBuffer byteBuffer, int l, int m) {
      GL11.glBindTexture(3553, i);
      GL11.glTexParameteri(3553, 10241, l);
      GL11.glTexParameteri(3553, 10240, l);
      GL11.glTexParameteri(3553, 10242, m);
      GL11.glTexParameteri(3553, 10243, m);
      GL11.glPixelStorei(3317, 1);
      GL11.glTexImage2D(3553, 0, 32856, j, k, 0, 6408, 5121, byteBuffer);
      GL11.glBindTexture(3553, 0);
      return i;
   }

   public static int genBuffer() {
      int i = GL15.glGenBuffers();
      bufferIds.add(i);
      GL15.glBindBuffer(34962, i);
      return i;
   }

   public static void deleteAll() {
      GL15.glBindBuffer(34962, 0);
      GL11.glBindTexture(3553, 0);

      for (int i : textureIds) {
         GL11.glDeleteTextures(i);
      }

      for (int j : bufferIds) {
         GL15.glDeleteBuffers(j);
      }
   }

   public static int getMouseX() {
      return Mouse.getX() * getScaledWidth() / Minecraft.getMinecraft().displayWidth;
   }

   public static int getMouseDeltaX() {
      return getMouseX() - savedMouseX;
   }

   public static int getMouseY() {
      return getScaledHeight() - Mouse.getY() * getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;
   }

   public static int getMouseDeltaY() {
      return getMouseY() - savedMouseY;
   }

   public static int getScaledWidth() {
      return Minecraft.getMinecraft().displayWidth / getScaleFactor();
   }

   public static int getScaledHeight() {
      return Minecraft.getMinecraft().displayHeight / getScaleFactor();
   }

   public static int getScaleFactor() {
      int i = 1;
      boolean flag = Minecraft.getMinecraft().isUnicode();
      int j = Minecraft.getMinecraft().gameSettings.guiScale;
      if (j == 0) {
         j = 1000;
      }

      while (i < j) {
         int k = Minecraft.getMinecraft().displayWidth / (i + 1);
         if (k < 320 || Minecraft.getMinecraft().displayHeight / (i + 1) < 240) {
            break;
         }

         i++;
      }

      if (flag && i % 2 != 0 && i != 1) {
         i--;
      }

      return i;
   }

   public static void setColorFloats(float f, float g, float h, float f2) {
      GlStateManager.color(f, g, h, f2);
   }

   public static void setColor(Color color) {
      GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
   }

   public static void setColorWithAlpha(Color color, float f) {
      GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, f);
   }

   public static void setColorHex(int i) {
      GlStateManager.color((float)(i >> 16 & 0xFF) / 255.0F, (float)(i >> 8 & 0xFF) / 255.0F, (float)(i & 0xFF) / 255.0F, (float)(i >> 24 & 0xFF) / 255.0F);
   }

   public static void setColorHexAlpha(int i, float f) {
      GlStateManager.color((float)(i >> 16 & 0xFF) / 255.0F, (float)(i >> 8 & 0xFF) / 255.0F, (float)(i & 0xFF) / 255.0F, f);
   }

   public static Color randomColorHsb(int i, float f) {
      float f1 = random.nextFloat();
      float f1x = ((float)random.nextInt(i) + (float)i) / (float)i + (float)i;
      return Color.getHSBColor(f1, f1x, f);
   }

   public static Color hsbColor(float f, float g, float h) {
      return Color.getHSBColor(f, g, h);
   }

   public static Color randomColor() {
      return randomColorHsb(1000, 0.6F);
   }

   public static void saveMousePosition() {
      savedMouseX = getMouseX();
      savedMouseY = getMouseY();
   }
}
