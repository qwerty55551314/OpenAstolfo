package oshi.hardware.common;

import oshi.annotation.concurrent.Immutable;
import oshi.hardware.SoundCard;

@Immutable
public abstract class AbstractSoundCard implements SoundCard {
   private String kernelVersion;
   private String name;
   private String codec;

   public AbstractSoundCard(String kernelVersion, String name, String codec) {
      this.kernelVersion = kernelVersion;
      this.name = name;
      this.codec = codec;
   }

   @Override
   public String getDriverVersion() {
      return this.kernelVersion;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getCodec() {
      return this.codec;
   }

   @Override
   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("SoundCard@");
      stringbuilder.append(Integer.toHexString(this.hashCode()));
      stringbuilder.append(" [kernelVersion=");
      stringbuilder.append(this.kernelVersion);
      stringbuilder.append(", name=");
      stringbuilder.append(this.name);
      stringbuilder.append(", codec=");
      stringbuilder.append(this.codec);
      stringbuilder.append(']');
      return stringbuilder.toString();
   }
}
