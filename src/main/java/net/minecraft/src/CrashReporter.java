package net.minecraft.src;

import java.util.HashMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import shadersmod.client.Shaders;

public class CrashReporter {
   public static void onCrashReport(CrashReport crashReport, CrashReportCategory category) {
      try {
         GameSettings gamesettings = Config.getGameSettings();
         if (gamesettings == null) {
            return;
         }

         if (!gamesettings.snooperEnabled) {
            return;
         }

         Throwable throwable = crashReport.getCrashCause();
         if (throwable == null) {
            return;
         }

         Class oclass = throwable.getClass();
         if (oclass == Throwable.class) {
            return;
         }

         String s1 = throwable.getClass().getName();
         if (s1.contains(".fml.client.SplashProgress")) {
            return;
         }

         extendCrashReport(category);
         String s = makeReport(crashReport);
         byte[] abyte = s.getBytes("ASCII");
         IFileUploadListener ifileuploadlistener = new IFileUploadListener() {
            @Override
            public void fileUploadFinished(String url, byte[] content, Throwable exception) {
            }
         };
         HashMap hashmap = new HashMap();
         hashmap.put("OF-Version", Config.getVersion());
         hashmap.put("OF-Summary", makeSummary(crashReport));
         FileUploadThread fileuploadthread = new FileUploadThread("http://optifine.net/crashReport", hashmap, abyte, ifileuploadlistener);
         fileuploadthread.setPriority(10);
         fileuploadthread.start();
         Thread.sleep(1000L);
      } catch (Exception exception) {
         Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
      }
   }

   private static String makeReport(CrashReport crashReport) {
      StringBuffer stringbuffer = new StringBuffer();
      stringbuffer.append("OptiFineVersion: " + Config.getVersion() + "\n");
      stringbuffer.append("Summary: " + makeSummary(crashReport) + "\n");
      stringbuffer.append("\n");
      stringbuffer.append(crashReport.getCompleteReport());
      stringbuffer.append("\n");
      return stringbuffer.toString();
   }

   private static String makeSummary(CrashReport crashReport) {
      Throwable throwable = crashReport.getCrashCause();
      if (throwable == null) {
         return "Unknown";
      } else {
         StackTraceElement[] astacktraceelement = throwable.getStackTrace();
         String s = "unknown";
         if (astacktraceelement.length > 0) {
            s = astacktraceelement[0].toString().trim();
         }

         return throwable.getClass().getName() + ": " + throwable.getMessage() + " (" + crashReport.getDescription() + ") [" + s + "]";
      }
   }

   public static void extendCrashReport(CrashReportCategory cat) {
      cat.addCrashSection("OptiFine Version", Config.getVersion());
      if (Config.getGameSettings() != null) {
         cat.addCrashSection("Render Distance Chunks", "" + Config.getChunkViewDistance());
         cat.addCrashSection("Mipmaps", "" + Config.getMipmapLevels());
         cat.addCrashSection("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
         cat.addCrashSection("Antialiasing", "" + Config.getAntialiasingLevel());
         cat.addCrashSection("Multitexture", "" + Config.isMultiTexture());
      }

      cat.addCrashSection("Shaders", "" + Shaders.getShaderPackName());
      cat.addCrashSection("OpenGlVersion", "" + Config.openGlVersion);
      cat.addCrashSection("OpenGlRenderer", "" + Config.openGlRenderer);
      cat.addCrashSection("OpenGlVendor", "" + Config.openGlVendor);
      cat.addCrashSection("CpuCount", "" + Config.getAvailableProcessors());
   }
}
