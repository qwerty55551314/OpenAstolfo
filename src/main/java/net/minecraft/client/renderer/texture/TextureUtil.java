package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import net.minecraft.src.Mipmaps;
import net.minecraft.src.Reflector;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TextureUtil {
   private static final Logger logger = LogManager.getLogger();
   private static final IntBuffer dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
   public static final DynamicTexture missingTexture = new DynamicTexture(16, 16);
   public static final int[] missingTextureData = missingTexture.getTextureData();

   public static int glGenTextures() {
      return GlStateManager.generateTexture();
   }

   public static void deleteTexture(int textureId) {
      GlStateManager.deleteTexture(textureId);
   }

   public static int uploadTextureImage(int i, BufferedImage bufferedImage) {
      return uploadTextureImageAllocate(i, bufferedImage, false, false);
   }

   public static void uploadTexture(int textureId, int[] ints, int i, int j) {
      bindTexture(textureId);
      uploadTextureSub(0, ints, i, j, 0, 0, false, false, false);
   }

   public static int[][] generateMipmapData(int i, int j, int[][] ints) {
      int[][] aint = new int[i + 1][];
      aint[0] = ints[0];
      if (i > 0) {
         boolean flag = false;

         for (int i1 = 0; i1 < ints.length; i1++) {
            if (ints[0][i1] >> 24 == 0) {
               flag = true;
               break;
            }
         }

         for (int l1 = 1; l1 <= i; l1++) {
            if (ints[l1] != null) {
               aint[l1] = ints[l1];
            } else {
               int[] aint1 = aint[l1 - 1];
               int[] aint2 = new int[aint1.length >> 2];
               int j1 = j >> l1;
               int k = aint2.length / j1;
               int l = j1 << 1;

               for (int i1x = 0; i1x < j1; i1x++) {
                  for (int j1x = 0; j1x < k; j1x++) {
                     int k1 = 2 * (i1x + j1x * l);
                     aint2[i1x + j1x * j1] = blendColors(aint1[k1 + 0], aint1[k1 + 1], aint1[k1 + 0 + l], aint1[k1 + 1 + l], flag);
                  }
               }

               aint[l1] = aint2;
            }
         }
      }

      return aint;
   }

   private static int blendColors(int i, int j, int k, int l, boolean bl) {
      return Mipmaps.alphaBlend(i, j, k, l);
   }

   private static int W(int i, int i2, int i3, int i4, int i5) {
      float f = (float)Math.pow((double)((float)(i >> i5 & 0xFF) / 255.0F), 2.2);
      float f1 = (float)Math.pow((double)((float)(i2 >> i5 & 0xFF) / 255.0F), 2.2);
      float f2 = (float)Math.pow((double)((float)(i3 >> i5 & 0xFF) / 255.0F), 2.2);
      float f3 = (float)Math.pow((double)((float)(i4 >> i5 & 0xFF) / 255.0F), 2.2);
      float f4 = (float)Math.pow((double)(f + f1 + f2 + f3) * 0.25, 0.45454545454545453);
      return (int)((double)f4 * 255.0);
   }

   public static void uploadTextureMipmap(int[][] ints, int i, int j, int k, int l, boolean bl, boolean bl2) {
      for (int i1 = 0; i1 < ints.length; i1++) {
         int[] aint = ints[i1];
         uploadTextureSub(i1, aint, i >> i1, j >> i1, k >> i1, l >> i1, bl, bl2, ints.length > 1);
      }
   }

   private static void uploadTextureSub(int i, int[] ints, int j, int k, int l, int m, boolean bl, boolean bl2, boolean bl3) {
      int i1 = 4194304 / j;
      setTextureBlurMipmap(bl, bl3);
      setTextureClamped(bl2);
      int k1 = 0;

      while (k1 < j * k) {
         int l1 = k1 / j;
         int j1 = Math.min(i1, k - l1);
         int i1x = j * j1;
         copyToBufferPos(ints, k1, i1x);
         GL11.glTexSubImage2D(3553, i, l, m + l1, j, j1, 32993, 33639, dataBuffer);
         k1 += j * j1;
      }
   }

   public static int uploadTextureImageAllocate(int i, BufferedImage bufferedImage, boolean bl, boolean bl2) {
      allocateTexture(i, bufferedImage.getWidth(), bufferedImage.getHeight());
      return uploadTextureImageSub(i, bufferedImage, 0, 0, bl, bl2);
   }

   public static void allocateTexture(int i, int j, int k) {
      allocateTextureImpl(i, 0, j, k);
   }

   public static void allocateTextureImpl(int i, int j, int k, int l) {
      Class<TextureUtil> oclass = TextureUtil.class;
      if (Reflector.SplashScreen.exists()) {
         oclass = Reflector.SplashScreen.getTargetClass();
      }

      synchronized (oclass) {
         deleteTexture(i);
         bindTexture(i);
      }

      if (j >= 0) {
         GL11.glTexParameteri(3553, 33085, j);
         GL11.glTexParameterf(3553, 33082, 0.0F);
         GL11.glTexParameterf(3553, 33083, (float)j);
         GL11.glTexParameterf(3553, 34049, 0.0F);
      }

      for (int i1 = 0; i1 <= j; i1++) {
         GL11.glTexImage2D(3553, i1, 6408, k >> i1, l >> i1, 0, 32993, 33639, (IntBuffer)null);
      }
   }

   public static int uploadTextureImageSub(int textureId, BufferedImage bufferedImage, int i, int j, boolean bl, boolean bl2) {
      bindTexture(textureId);
      uploadTextureImageSubImpl(bufferedImage, i, j, bl, bl2);
      return textureId;
   }

   private static void uploadTextureImageSubImpl(BufferedImage bufferedImage, int i, int j, boolean bl, boolean bl2) {
      int i1 = bufferedImage.getWidth();
      int j1 = bufferedImage.getHeight();
      int k = 4194304 / i1;
      int[] aint = new int[k * i1];
      setTextureBlurred(bl);
      setTextureClamped(bl2);

      for (int l = 0; l < i1 * j1; l += i1 * k) {
         int i1x = l / i1;
         int j1x = Math.min(k, j1 - i1x);
         int k1 = i1 * j1x;
         bufferedImage.getRGB(0, i1x, i1, j1x, aint, 0, i1);
         copyToBuffer(aint, k1);
         GL11.glTexSubImage2D(3553, 0, i, j + i1x, i1, j1x, 32993, 33639, dataBuffer);
      }
   }

   public static void setTextureClamped(boolean bl) {
      if (bl) {
         GL11.glTexParameteri(3553, 10242, 33071);
         GL11.glTexParameteri(3553, 10243, 33071);
      } else {
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10243, 10497);
      }
   }

   private static void setTextureBlurred(boolean bl) {
      setTextureBlurMipmap(bl, false);
   }

   public static void setTextureBlurMipmap(boolean bl, boolean bl2) {
      if (bl) {
         int short3;
         int short4;
         int short5;
         if (bl2) {
            short3 = 3553;
            short4 = 10241;
            short5 = 9987;
         } else {
            short3 = 3553;
            short4 = 10241;
            short5 = 9729;
         }

         GL11.glTexParameteri(short3, short4, short5);
         GL11.glTexParameteri(3553, 10240, 9729);
      } else {
         int i = Config.getMipmapType();
         int short1;
         int short2;
         int j;
         if (bl2) {
            short1 = 3553;
            short2 = 10241;
            j = i;
         } else {
            short1 = 3553;
            short2 = 10241;
            j = 9728;
         }

         GL11.glTexParameteri(short1, short2, j);
         GL11.glTexParameteri(3553, 10240, 9728);
      }
   }

   private static void copyToBuffer(int[] ints, int i) {
      copyToBufferPos(ints, 0, i);
   }

   private static void copyToBufferPos(int[] ints, int i, int j) {
      int[] aint = ints;
      if (Minecraft.getMinecraft().gameSettings.anaglyph) {
         aint = updateAnaglyph(ints);
      }

      ((Buffer)dataBuffer).clear();
      dataBuffer.put(aint, i, j);
      ((Buffer)dataBuffer).position(0).limit(j);
   }

   public static void bindTexture(int i) {
      GlStateManager.bindTexture(i);
   }

   public static int[] readImageData(IResourceManager resourceManager, ResourceLocation imageLocation) throws IOException {
      BufferedImage bufferedimage = readBufferedImage(resourceManager.getResource(imageLocation).getInputStream());
      if (bufferedimage == null) {
         return null;
      } else {
         int i = bufferedimage.getWidth();
         int j = bufferedimage.getHeight();
         int[] aint = new int[i * j];
         bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
         return aint;
      }
   }

   public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
      if (imageStream == null) {
         return null;
      } else {
         BufferedImage bufferedimage;
         try {
            bufferedimage = ImageIO.read(imageStream);
         } finally {
            IOUtils.closeQuietly(imageStream);
         }

         return bufferedimage;
      }
   }

   public static int[] updateAnaglyph(int[] ints) {
      int[] aint = new int[ints.length];

      for (int i = 0; i < ints.length; i++) {
         aint[i] = anaglyphColor(ints[i]);
      }

      return aint;
   }

   public static int anaglyphColor(int i) {
      int i1 = i >> 24 & 0xFF;
      int j = i >> 16 & 0xFF;
      int k = i >> 8 & 0xFF;
      int l = i & 0xFF;
      int i1x = (j * 30 + k * 59 + l * 11) / 100;
      int j1 = (j * 30 + k * 70) / 100;
      int k1 = (j * 30 + l * 70) / 100;
      return i1 << 24 | i1x << 16 | j1 << 8 | k1;
   }

   public static void saveGlTexture(String string, int i, int j, int k, int l) {
      bindTexture(i);
      GL11.glPixelStorei(3333, 1);
      GL11.glPixelStorei(3317, 1);

      for (int i1 = 0; i1 <= j; i1++) {
         File file1 = new File(string + "_" + i1 + ".png");
         int j1 = k >> i1;
         int k1 = l >> i1;
         int l1 = j1 * k1;
         IntBuffer intbuffer = BufferUtils.createIntBuffer(l1);
         int[] aint = new int[l1];
         GL11.glGetTexImage(3553, i1, 32993, 33639, intbuffer);
         intbuffer.get(aint);
         BufferedImage bufferedimage = new BufferedImage(j1, k1, 2);
         bufferedimage.setRGB(0, 0, j1, k1, aint, 0, j1);

         try {
            ImageIO.write(bufferedimage, "png", file1);
            logger.debug("Exported png to: {}", file1.getAbsolutePath());
         } catch (Exception exception) {
            logger.debug("Unable to write: ", (Throwable)exception);
         }
      }
   }

   public static void processPixelValues(int[] ints, int i, int j) {
      int[] aint = new int[i];
      int i1 = j / 2;

      for (int j1 = 0; j1 < i1; j1++) {
         System.arraycopy(ints, j1 * i, aint, 0, i);
         System.arraycopy(ints, (j - 1 - j1) * i, ints, j1 * i, i);
         System.arraycopy(aint, 0, ints, (j - 1 - j1) * i, i);
      }
   }

   static {
      int[] aint = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
      int[] aint1 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
      int i = aint.length;

      for (int j = 0; j < 16; j++) {
         System.arraycopy(j < i ? aint : aint1, 0, missingTextureData, 16 * j, i);
         System.arraycopy(j < i ? aint1 : aint, 0, missingTextureData, 16 * j + i, i);
      }

      missingTexture.updateDynamicTexture();
   }
}
