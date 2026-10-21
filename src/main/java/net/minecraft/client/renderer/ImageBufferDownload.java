package net.minecraft.client.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class ImageBufferDownload implements IImageBuffer {
   private int[] imageData;
   private int imageWidth;
   private int imageHeight;
   public boolean skipLegacyConversion;

   public ImageBufferDownload() {
   }

   public ImageBufferDownload(boolean bl) {
      this.skipLegacyConversion = bl;
   }

   @Override
   public BufferedImage parseUserSkin(BufferedImage image) {
      if (image == null) {
         return null;
      } else {
         this.imageWidth = 64;
         this.imageHeight = 64;
         int i = image.getWidth();
         int j = image.getHeight();
         int b0 = 1;

         while (true) {
            if (this.imageWidth >= i) {
               int k = this.imageHeight;
               if (k >= j) {
                  BufferedImage bufferedimage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
                  Graphics graphics = bufferedimage.getGraphics();
                  graphics.drawImage(image, 0, 0, (ImageObserver)null);
                  int l = image.getHeight();
                  if (l == 32 * b0 && !this.skipLegacyConversion) {
                     graphics.drawImage(bufferedimage, 24 * b0, 48 * b0, 20 * b0, 52 * b0, 4 * b0, 16 * b0, 8 * b0, 20 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 28 * b0, 48 * b0, 24 * b0, 52 * b0, 8 * b0, 16 * b0, 12 * b0, 20 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 20 * b0, 52 * b0, 16 * b0, 64 * b0, 8 * b0, 20 * b0, 12 * b0, 32 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 24 * b0, 52 * b0, 20 * b0, 64 * b0, 4 * b0, 20 * b0, 8 * b0, 32 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 28 * b0, 52 * b0, 24 * b0, 64 * b0, 0 * b0, 20 * b0, 4 * b0, 32 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 32 * b0, 52 * b0, 28 * b0, 64 * b0, 12 * b0, 20 * b0, 16 * b0, 32 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 40 * b0, 48 * b0, 36 * b0, 52 * b0, 44 * b0, 16 * b0, 48 * b0, 20 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 44 * b0, 48 * b0, 40 * b0, 52 * b0, 48 * b0, 16 * b0, 52 * b0, 20 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 36 * b0, 52 * b0, 32 * b0, 64 * b0, 48 * b0, 20 * b0, 52 * b0, 32 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 40 * b0, 52 * b0, 36 * b0, 64 * b0, 44 * b0, 20 * b0, 48 * b0, 32 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 44 * b0, 52 * b0, 40 * b0, 64 * b0, 40 * b0, 20 * b0, 44 * b0, 32 * b0, (ImageObserver)null);
                     graphics.drawImage(bufferedimage, 48 * b0, 52 * b0, 44 * b0, 64 * b0, 52 * b0, 20 * b0, 56 * b0, 32 * b0, (ImageObserver)null);
                  }

                  graphics.dispose();
                  this.imageData = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
                  if (!this.skipLegacyConversion) {
                     this.setAreaOpaque(0, 0, 32 * b0, 16 * b0);
                     this.setAreaTransparent(32 * b0, 0, 64 * b0, 32 * b0);
                     this.setAreaOpaque(0, 16 * b0, 64 * b0, 32 * b0);
                     this.setAreaTransparent(0, 32 * b0, 16 * b0, 48 * b0);
                     this.setAreaTransparent(16 * b0, 32 * b0, 40 * b0, 48 * b0);
                     this.setAreaTransparent(40 * b0, 32 * b0, 56 * b0, 48 * b0);
                     this.setAreaTransparent(0, 48 * b0, 16 * b0, 64 * b0);
                     this.setAreaOpaque(16 * b0, 48 * b0, 48 * b0, 64 * b0);
                     this.setAreaTransparent(48 * b0, 48 * b0, 64 * b0, 64 * b0);
                  }

                  return bufferedimage;
               }
            }

            this.imageWidth *= 2;
            this.imageHeight *= 2;
            b0 *= 2;
         }
      }
   }

   @Override
   public void skinAvailable() {
   }

   private void setAreaTransparent(int i, int j, int k, int l) {
      if (!this.hasTransparency(i, j, k, l)) {
         for (int i1 = i; i1 < k; i1++) {
            for (int j1 = j; j1 < l; j1++) {
               this.imageData[i1 + j1 * this.imageWidth] = this.imageData[i1 + j1 * this.imageWidth] & 16777215;
            }
         }
      }
   }

   private void setAreaOpaque(int i, int j, int k, int l) {
      for (int i1 = i; i1 < k; i1++) {
         for (int j1 = j; j1 < l; j1++) {
            this.imageData[i1 + j1 * this.imageWidth] = this.imageData[i1 + j1 * this.imageWidth] | 0xFF000000;
         }
      }
   }

   private boolean hasTransparency(int i, int j, int k, int l) {
      for (int i1 = i; i1 < k; i1++) {
         for (int j1 = j; j1 < l; j1++) {
            int k1 = this.imageData[i1 + j1 * this.imageWidth];
            if ((k1 >> 24 & 0xFF) < 128) {
               return true;
            }
         }
      }

      return false;
   }
}
