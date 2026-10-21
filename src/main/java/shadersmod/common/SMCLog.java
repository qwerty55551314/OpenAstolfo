package shadersmod.common;

import net.minecraft.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String PREFIX = "[Shaders] ";
   private static Entity[] i;

   public static void severe(String message) {
      LOGGER.error("[Shaders] " + message);
   }

   public static void warning(String message) {
      LOGGER.warn("[Shaders] " + message);
   }

   public static void info(String message) {
      LOGGER.info("[Shaders] " + message);
   }

   public static void fine(String message) {
      LOGGER.debug("[Shaders] " + message);
   }

   public static void severeFormatted(String string, Object... objects) {
      String s = String.format(string, objects);
      LOGGER.error("[Shaders] " + s);
   }

   public static void warning(String formatIn, Object... args) {
      String s = String.format(formatIn, args);
      LOGGER.warn("[Shaders] " + s);
   }

   public static void infoFormatted(String string, Object... objects) {
      String s = String.format(string, objects);
      LOGGER.info("[Shaders] " + s);
   }

   public static void fineFormatted(String string, Object... objects) {
      String s = String.format(string, objects);
      LOGGER.debug("[Shaders] " + s);
   }

   static {
      T(new Entity[1]);
   }

   public static void T(Entity[] entityArray) {
      i = entityArray;
   }

   public static Entity[] w() {
      return i;
   }
}
