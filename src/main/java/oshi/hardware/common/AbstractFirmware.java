package oshi.hardware.common;

import oshi.annotation.concurrent.Immutable;
import oshi.hardware.Firmware;

@Immutable
public abstract class AbstractFirmware implements Firmware {
   @Override
   public String getName() {
      return "unknown";
   }

   @Override
   public String getDescription() {
      return "unknown";
   }

   @Override
   public String getReleaseDate() {
      return "unknown";
   }

   @Override
   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("manufacturer=").append(this.getManufacturer()).append(", ");
      stringbuilder.append("name=").append(this.getName()).append(", ");
      stringbuilder.append("description=").append(this.getDescription()).append(", ");
      stringbuilder.append("version=").append(this.getVersion()).append(", ");
      stringbuilder.append("release date=").append(this.getReleaseDate() == null ? "unknown" : this.getReleaseDate());
      return stringbuilder.toString();
   }
}
