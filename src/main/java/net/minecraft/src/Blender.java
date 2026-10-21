package net.minecraft.src;

import net.minecraft.client.renderer.GlStateManager;

public class Blender {
   public static final int BLEND_ALPHA = 0;
   public static final int BLEND_ADD = 1;
   public static final int BLEND_SUBSTRACT = 2;
   public static final int BLEND_MULTIPLY = 3;
   public static final int BLEND_DODGE = 4;
   public static final int BLEND_BURN = 5;
   public static final int BLEND_SCREEN = 6;
   public static final int BLEND_OVERLAY = 7;
   public static final int BLEND_REPLACE = 8;
   public static final int BLEND_DEFAULT = 1;

   public static int parseBlend(String str) {
      if (str == null) {
         return 1;
      } else {
         str = str.toLowerCase().trim();
         boolean flag = str.equals("alpha");
         if (flag) {
            return 0;
         } else {
            boolean flag1 = str.equals("add");
            if (flag1) {
               return 1;
            } else {
               boolean flag2 = str.equals("subtract");
               if (flag2) {
                  return 2;
               } else {
                  boolean flag3 = str.equals("multiply");
                  if (flag3) {
                     return 3;
                  } else {
                     boolean flag4 = str.equals("dodge");
                     if (flag4) {
                        return 4;
                     } else {
                        boolean flag5 = str.equals("burn");
                        if (flag5) {
                           return 5;
                        } else {
                           boolean flag6 = str.equals("screen");
                           if (flag6) {
                              return 6;
                           } else {
                              boolean flag7 = str.equals("overlay");
                              if (flag7) {
                                 return 7;
                              } else {
                                 boolean flag8 = str.equals("replace");
                                 if (flag8) {
                                    return 8;
                                 } else {
                                    Config.warn("Unknown blend: " + str);
                                    return 1;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void setupBlend(int blend, float brightness) {
      switch (blend) {
         case 0:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
            break;
         case 1:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 1);
            GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
            break;
         case 2:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(775, 0);
            GlStateManager.color(brightness, brightness, brightness, 1.0F);
            break;
         case 3:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(774, 771);
            GlStateManager.color(brightness, brightness, brightness, brightness);
            break;
         case 4:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(1, 1);
            GlStateManager.color(brightness, brightness, brightness, 1.0F);
            break;
         case 5:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(0, 769);
            GlStateManager.color(brightness, brightness, brightness, 1.0F);
            break;
         case 6:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(1, 769);
            GlStateManager.color(brightness, brightness, brightness, 1.0F);
            break;
         case 7:
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(774, 768);
            GlStateManager.color(brightness, brightness, brightness, 1.0F);
            break;
         case 8:
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
      }

      GlStateManager.enableTexture2D();
   }

   public static void clearBlend(float rainBrightness) {
      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 1);
      GlStateManager.color(1.0F, 1.0F, 1.0F, rainBrightness);
   }
}
