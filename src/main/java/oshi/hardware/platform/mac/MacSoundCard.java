package oshi.hardware.platform.mac;

import java.util.ArrayList;
import java.util.List;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.common.AbstractSoundCard;
import oshi.util.FileUtil;
import oshi.util.ParseUtil;

@Immutable
public final class MacSoundCard extends AbstractSoundCard {
   private static final String g = "Apple Inc.";

   public MacSoundCard(String kernelVersion, String name, String codec) {
      super(kernelVersion, name, codec);
   }

   public static List<MacSoundCard> getSoundCards() {
      ArrayList arraylist = new ArrayList();
      String s = "AppleHDAController";
      boolean flag = false;

      for (String s1 : FileUtil.readFile("/System/Library/Extensions/AppleHDA.kext/Contents/Info.plist")) {
         if (s1.contains("<key>com.apple.driver.AppleHDAController</key>")) {
            flag = true;
         } else if (flag) {
            s = "AppleHDAController " + ParseUtil.getTextBetweenStrings(s1, "<string>", "</string>");
            flag = false;
         }
      }

      arraylist.add(new MacSoundCard(s, "Apple Inc.", "AppleHDACodec"));
      return arraylist;
   }
}
