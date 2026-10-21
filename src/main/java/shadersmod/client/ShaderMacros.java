package shadersmod.client;

import net.minecraft.src.Config;
import net.minecraft.util.Util;

public class ShaderMacros {
   private static String PREFIX_MACRO = "MC_";
   public static final String MC_VERSION = "MC_VERSION";
   public static final String MC_GL_VERSION = "MC_GL_VERSION";
   public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
   public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
   public static final String MC_OS_MAC = "MC_OS_MAC";
   public static final String MC_OS_LINUX = "MC_OS_LINUX";
   public static final String MC_OS_OTHER = "MC_OS_OTHER";
   public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
   public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
   public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
   public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
   public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
   public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
   public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
   public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
   public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
   public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
   public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
   public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
   private static String[] extensionMacros;

   public static String getOs() {
      Util.EnumOS util$enumos = Util.getOSType();
      switch (util$enumos) {
         case WINDOWS:
            return "MC_OS_WINDOWS";
         case OSX:
            return "MC_OS_MAC";
         case LINUX:
            return "MC_OS_LINUX";
         default:
            return "MC_OS_OTHER";
      }
   }

   public static String getVendor() {
      String s = Config.openGlVendor;
      if (s == null) {
         return "MC_GL_VENDOR_OTHER";
      } else {
         s = s.toLowerCase();
         boolean flag = s.startsWith("ati");
         String s1;
         if (flag) {
            s1 = "MC_GL_VENDOR_ATI";
         } else {
            boolean flag1 = s.startsWith("intel");
            if (flag1) {
               s1 = "MC_GL_VENDOR_INTEL";
            } else {
               boolean flag2 = s.startsWith("nvidia");
               s1 = flag2 ? "MC_GL_VENDOR_NVIDIA" : (s.startsWith("x.org") ? "MC_GL_VENDOR_XORG" : "MC_GL_VENDOR_OTHER");
            }
         }

         return s1;
      }
   }

   public static String getRenderer() {
      String s = Config.openGlRenderer;
      if (s == null) {
         return "MC_GL_RENDERER_OTHER";
      } else {
         s = s.toLowerCase();
         boolean flag = s.startsWith("amd");
         String s1;
         if (flag) {
            s1 = "MC_GL_RENDERER_RADEON";
         } else {
            boolean flag1 = s.startsWith("ati");
            if (flag1) {
               s1 = "MC_GL_RENDERER_RADEON";
            } else {
               boolean flag2 = s.startsWith("radeon");
               if (flag2) {
                  s1 = "MC_GL_RENDERER_RADEON";
               } else {
                  boolean flag3 = s.startsWith("gallium");
                  if (flag3) {
                     s1 = "MC_GL_RENDERER_GALLIUM";
                  } else {
                     boolean flag4 = s.startsWith("intel");
                     if (flag4) {
                        s1 = "MC_GL_RENDERER_INTEL";
                     } else {
                        boolean flag5 = s.startsWith("geforce");
                        if (flag5) {
                           s1 = "MC_GL_RENDERER_GEFORCE";
                        } else {
                           boolean flag6 = s.startsWith("nvidia");
                           if (flag6) {
                              s1 = "MC_GL_RENDERER_GEFORCE";
                           } else {
                              boolean flag7 = s.startsWith("quadro");
                              if (flag7) {
                                 s1 = "MC_GL_RENDERER_QUADRO";
                              } else {
                                 boolean flag8 = s.startsWith("nvs");
                                 s1 = flag8 ? "MC_GL_RENDERER_QUADRO" : (s.startsWith("mesa") ? "MC_GL_RENDERER_MESA" : "MC_GL_RENDERER_OTHER");
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return s1;
      }
   }

   public static String getPrefixMacro() {
      return PREFIX_MACRO;
   }

   public static String[] getExtensions() {
      if (extensionMacros == null) {
         String[] astring = Config.getOpenGlExtensions();
         String[] astring1 = new String[astring.length];

         for (int i = 0; i < astring.length; i++) {
            astring1[i] = PREFIX_MACRO + astring[i];
         }

         extensionMacros = astring1;
      }

      return extensionMacros;
   }
}
