package oshi.util;

import java.util.Properties;
import oshi.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public final class GlobalConfig {
   private static final String W = "";
   private static final Properties CONFIG = FileUtil.readPropertiesFromFilename("");

   private GlobalConfig() {
   }

   public static String get(String key, String def) {
      return CONFIG.getProperty(key, def);
   }

   public static int get(String key, int def) {
      String s = CONFIG.getProperty(key);
      return s == null ? def : ParseUtil.parseIntOrDefault(s, def);
   }

   public static double get(String key, double def) {
      String s = CONFIG.getProperty(key);
      return s == null ? def : ParseUtil.parseDoubleOrDefault(s, def);
   }

   public static boolean get(String key, boolean def) {
      String s = CONFIG.getProperty(key);
      return s == null ? def : Boolean.parseBoolean(s);
   }

   public static void set(String key, Object val) {
      if (val == null) {
         CONFIG.remove(key);
      } else {
         CONFIG.setProperty(key, val.toString());
      }
   }

   public static void remove(String key) {
      CONFIG.remove(key);
   }

   public static void clear() {
      CONFIG.clear();
   }

   public static void load(Properties properties) {
      CONFIG.putAll(properties);
   }

   public static class PropertyException extends RuntimeException {
      private static final long serialVersionUID = -7482581936621748005L;

      public PropertyException(String property) {
         super("Invalid property: \"" + property + "\" = " + GlobalConfig.get(property, null));
      }

      public PropertyException(String property, String message) {
         super("Invalid property \"" + property + "\": " + message);
      }
   }
}
