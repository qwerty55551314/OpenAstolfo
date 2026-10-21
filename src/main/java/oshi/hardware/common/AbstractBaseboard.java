package oshi.hardware.common;

import oshi.annotation.concurrent.Immutable;
import oshi.hardware.Baseboard;

@Immutable
public abstract class AbstractBaseboard implements Baseboard {
   @Override
   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("manufacturer=").append(this.getManufacturer()).append(", ");
      stringbuilder.append("model=").append(this.getModel()).append(", ");
      stringbuilder.append("version=").append(this.getVersion()).append(", ");
      stringbuilder.append("serial number=").append(this.getSerialNumber());
      return stringbuilder.toString();
   }
}
