package net.minecraft.src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.minecraft.util.ResourceLocation;

public class FontUtils {
   public static Properties readFontProperties(ResourceLocation locationFontTexture) {
      String s = locationFontTexture.getResourcePath();
      boolean flag = false;
      Properties properties = new Properties();
      if (!s.endsWith(".png")) {
         return properties;
      } else {
         String s1 = s.substring(0, s.length() - ".png".length()) + ".properties";

         try {
            ResourceLocation resourcelocation = new ResourceLocation(locationFontTexture.getResourceDomain(), s1);
            InputStream inputstream = Config.getResourceStream(Config.getResourceManager(), resourcelocation);
            if (inputstream == null) {
               return properties;
            }

            Config.log("Loading " + s1);
            properties.load(inputstream);
         } catch (FileNotFoundException filenotfoundexception) {
         } catch (IOException ioexception) {
            ioexception.printStackTrace();
         }

         return properties;
      }
   }

   public static void readCustomCharWidths(Properties props, float[] charWidth) {
      for (Object object : props.keySet()) {
         String s = (String)object;
         if (s.startsWith("width.")) {
            String s1 = s.substring("width.".length());
            int i = Config.parseInt(s1, -1);
            if (i >= 0 && i < charWidth.length) {
               String s2 = props.getProperty(s);
               float f = Config.parseFloat(s2, -1.0F);
               if (f >= 0.0F) {
                  charWidth[i] = f;
               }
            }
         }
      }
   }

   public static float readFloat(Properties props, String key, float defOffset) {
      String s = props.getProperty(key);
      if (s == null) {
         return defOffset;
      } else {
         float f = Config.parseFloat(s, Float.MIN_VALUE);
         if (f == Float.MIN_VALUE) {
            Config.warn("Invalid value for " + key + ": " + s);
            return defOffset;
         } else {
            return f;
         }
      }
   }

   public static ResourceLocation getHdFontLocation(ResourceLocation fontLoc) {
      if (!Config.isCustomFonts()) {
         return fontLoc;
      } else if (fontLoc == null) {
         return fontLoc;
      } else {
         String s = fontLoc.getResourcePath();
         if (!s.startsWith("textures/")) {
            return fontLoc;
         } else {
            s = s.substring("textures/".length());
            s = "mcpatcher/" + s;
            ResourceLocation resourcelocation = new ResourceLocation(fontLoc.getResourceDomain(), s);
            return Config.hasResource(Config.getResourceManager(), resourcelocation) ? resourcelocation : fontLoc;
         }
      }
   }
}
