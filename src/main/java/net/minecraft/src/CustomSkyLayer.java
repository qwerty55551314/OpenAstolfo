package net.minecraft.src;

import java.util.Properties;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;

public class CustomSkyLayer {
   public String source = null;
   private int startFadeIn = -1;
   private int endFadeIn = -1;
   private int startFadeOut = -1;
   private int endFadeOut = -1;
   private int blend = 1;
   private boolean rotate = false;
   private float speed = 1.0F;
   private float[] axis;
   private RangeListInt days;
   private int x;
   public int textureId;
   public static final float[] DEFAULT_AXIS = new float[]{1.0F, 0.0F, 0.0F};

   public CustomSkyLayer(Properties props, String defSource) {
      this.axis = DEFAULT_AXIS;
      this.days = null;
      this.x = 8;
      this.textureId = -1;
      ConnectedParser connectedparser = new ConnectedParser("CustomSky");
      this.source = props.getProperty("source", defSource);
      this.startFadeIn = this.parseTime(props.getProperty("startFadeIn"));
      this.endFadeIn = this.parseTime(props.getProperty("endFadeIn"));
      this.startFadeOut = this.parseTime(props.getProperty("startFadeOut"));
      this.endFadeOut = this.parseTime(props.getProperty("endFadeOut"));
      this.blend = Blender.parseBlend(props.getProperty("blend"));
      this.rotate = this.parseBoolean(props.getProperty("rotate"), true);
      this.speed = this.parseFloat(props.getProperty("speed"), 1.0F);
      this.axis = this.parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
      this.days = connectedparser.parseRangeListInt(props.getProperty("days"));
      this.x = connectedparser.parseIntOrDefault(props.getProperty("daysLoop"), 8);
   }

   private int parseTime(String str) {
      if (str == null) {
         return -1;
      } else {
         String[] astring = Config.tokenize(str, ":");
         if (astring.length != 2) {
            Config.warn("Invalid time: " + str);
            return -1;
         } else {
            String s = astring[0];
            String s1 = astring[1];
            int i = Config.parseInt(s, -1);
            int j = Config.parseInt(s1, -1);
            if (i >= 0 && i <= 23 && j >= 0 && j <= 59) {
               i -= 6;
               if (i < 0) {
                  i += 24;
               }

               return i * 1000 + (int)((double)j / 60.0 * 1000.0);
            } else {
               Config.warn("Invalid time: " + str);
               return -1;
            }
         }
      }
   }

   private boolean parseBoolean(String str, boolean defVal) {
      if (str == null) {
         return defVal;
      } else {
         boolean flag = str.toLowerCase().equals("true");
         if (flag) {
            return true;
         } else {
            boolean flag1 = str.toLowerCase().equals("false");
            if (flag1) {
               return false;
            } else {
               Config.warn("Unknown boolean: " + str);
               return defVal;
            }
         }
      }
   }

   private float parseFloat(String str, float defVal) {
      if (str == null) {
         return defVal;
      } else {
         float f = Config.parseFloat(str, Float.MIN_VALUE);
         if (f == Float.MIN_VALUE) {
            Config.warn("Invalid value: " + str);
            return defVal;
         } else {
            return f;
         }
      }
   }

   private float[] parseAxis(String str, float[] defVal) {
      if (str == null) {
         return defVal;
      } else {
         String[] astring = Config.tokenize(str, " ");
         int j = astring.length;
         if (j != 3) {
            Config.warn("Invalid axis: " + str);
            return defVal;
         } else {
            float[] afloat = new float[3];

            for (int i = 0; i < astring.length; i++) {
               afloat[i] = Config.parseFloat(astring[i], Float.MIN_VALUE);
               float f2 = afloat[i];
               if (f2 == Float.MIN_VALUE) {
                  Config.warn("Invalid axis: " + str);
                  return defVal;
               }

               if (afloat[i] < -1.0F || afloat[i] > 1.0F) {
                  Config.warn("Invalid axis values: " + str);
                  return defVal;
               }
            }

            float f3 = afloat[0];
            float f = afloat[1];
            float f1 = afloat[2];
            float f4;
            int k = (f4 = f3 * f3 + f * f + f1 * f1 - 1.0E-5F) == 0.0F ? 0 : (f4 < 0.0F ? -1 : 1);
            if (k < 0) {
               Config.warn("Invalid axis values: " + str);
               return defVal;
            } else {
               return new float[]{f1, f, -f3};
            }
         }
      }
   }

   public boolean isValid(String path) {
      if (this.source == null) {
         Config.warn("No source texture: " + path);
         return false;
      } else {
         this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
         int j1 = this.startFadeIn;
         if (j1 >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
            int i = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            if (this.startFadeOut < 0) {
               this.startFadeOut = this.normalizeTime(this.endFadeOut - i);
               if (this.timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
                  this.startFadeOut = this.endFadeIn;
               }
            }

            int j = this.normalizeTime(this.startFadeOut - this.endFadeIn);
            int k = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int l = this.normalizeTime(this.startFadeIn - this.endFadeOut);
            int i1 = i + j + k + l;
            if (i1 != 24000) {
               Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + i1);
               return false;
            } else if (this.speed < 0.0F) {
               Config.warn("Invalid speed: " + this.speed);
               return false;
            } else if (this.x <= 0) {
               Config.warn("Invalid daysLoop: " + this.x);
               return false;
            } else {
               return true;
            }
         } else {
            Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
         }
      }
   }

   private int normalizeTime(int timeMc) {
      while (timeMc >= 24000) {
         timeMc -= 24000;
      }

      while (timeMc < 0) {
         timeMc += 24000;
      }

      return timeMc;
   }

   public void render(int i, float f, float g) {
      float f1 = g * this.getFadeBrightness(i);
      f1 = Config.limit(f1, 0.0F, 1.0F);
      if (f1 >= 1.0E-4F) {
         GlStateManager.bindTexture(this.textureId);
         Blender.setupBlend(this.blend, f1);
         GlStateManager.pushMatrix();
         if (this.rotate) {
            GlStateManager.rotate(f * 360.0F * this.speed, this.axis[0], this.axis[1], this.axis[2]);
         }

         Tessellator tessellator = Tessellator.getInstance();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tessellator, 4);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(tessellator, 1);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(tessellator, 0);
         GlStateManager.popMatrix();
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tessellator, 5);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tessellator, 2);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tessellator, 3);
         GlStateManager.popMatrix();
      }
   }

   private float getFadeBrightness(int timeOfDay) {
      boolean flag = this.timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn);
      if (flag) {
         int k = this.normalizeTime(this.endFadeIn - this.startFadeIn);
         int l = this.normalizeTime(timeOfDay - this.startFadeIn);
         return (float)l / (float)k;
      } else {
         boolean flag1 = this.timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut);
         if (flag1) {
            return 1.0F;
         } else {
            boolean flag2 = this.timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut);
            if (flag2) {
               int i = this.normalizeTime(this.endFadeOut - this.startFadeOut);
               int j = this.normalizeTime(timeOfDay - this.startFadeOut);
               return 1.0F - (float)j / (float)i;
            } else {
               return 0.0F;
            }
         }
      }
   }

   private void renderSide(Tessellator tess, int side) {
      WorldRenderer worldrenderer = tess.getWorldRenderer();
      double d0 = (double)(side % 3) / 3.0;
      double d1 = (double)(side / 3) / 2.0;
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(-100.0, -100.0, -100.0).tex(d0, d1).endVertex();
      worldrenderer.pos(-100.0, -100.0, 100.0).tex(d0, d1 + 0.5).endVertex();
      worldrenderer.pos(100.0, -100.0, 100.0).tex(d0 + 0.3333333333333333, d1 + 0.5).endVertex();
      worldrenderer.pos(100.0, -100.0, -100.0).tex(d0 + 0.3333333333333333, d1).endVertex();
      tess.draw();
   }

   public boolean isActive(World world, int timeOfDay) {
      if (this.timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn)) {
         return false;
      } else {
         if (this.days != null) {
            long i = world.getWorldTime();
            long j = i - (long)this.startFadeIn;

            while (j < 0L) {
               j += (long)(24000 * this.x);
            }

            int k = (int)(j / 24000L);
            int l = k % this.x;
            boolean flag = this.days.isInRange(l);
            if (!flag) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
      return timeStart <= timeEnd ? timeOfDay >= timeStart && timeOfDay <= timeEnd : timeOfDay >= timeStart || timeOfDay <= timeEnd;
   }
}
