package oshi.hardware.common;

import com.sun.jna.Platform;
import java.time.LocalDate;
import oshi.SystemInfo;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.PowerSource;
import oshi.hardware.platform.linux.LinuxPowerSource;
import oshi.hardware.platform.mac.MacPowerSource;
import oshi.hardware.platform.unix.freebsd.FreeBsdPowerSource;
import oshi.hardware.platform.unix.solaris.SolarisPowerSource;
import oshi.hardware.platform.windows.WindowsPowerSource;

@ThreadSafe
public abstract class AbstractPowerSource implements PowerSource {
   private String name;
   private String deviceName;
   private double remainingCapacityPercent;
   private double timeRemainingEstimated;
   private double timeRemainingInstant;
   private double powerUsageRate;
   private double voltage;
   private double amperage;
   private boolean powerOnLine;
   private boolean charging;
   private boolean discharging;
   private PowerSource.CapacityUnits capacityUnits;
   private int currentCapacity;
   private int maxCapacity;
   private int designCapacity;
   private int cycleCount;
   private String chemistry;
   private LocalDate manufactureDate;
   private String manufacturer;
   private String serialNumber;
   private double temperature;

   public AbstractPowerSource(
      String name,
      String deviceName,
      double remainingCapacityPercent,
      double timeRemainingEstimated,
      double timeRemainingInstant,
      double powerUsageRate,
      double voltage,
      double amperage,
      boolean powerOnLine,
      boolean charging,
      boolean discharging,
      PowerSource.CapacityUnits capacityUnits,
      int currentCapacity,
      int maxCapacity,
      int designCapacity,
      int cycleCount,
      String chemistry,
      LocalDate manufactureDate,
      String manufacturer,
      String serialNumber,
      double temperature
   ) {
      this.name = name;
      this.deviceName = deviceName;
      this.remainingCapacityPercent = remainingCapacityPercent;
      this.timeRemainingEstimated = timeRemainingEstimated;
      this.timeRemainingInstant = timeRemainingInstant;
      this.powerUsageRate = powerUsageRate;
      this.voltage = voltage;
      this.amperage = amperage;
      this.powerOnLine = powerOnLine;
      this.charging = charging;
      this.discharging = discharging;
      this.capacityUnits = capacityUnits;
      this.currentCapacity = currentCapacity;
      this.maxCapacity = maxCapacity;
      this.designCapacity = designCapacity;
      this.cycleCount = cycleCount;
      this.chemistry = chemistry;
      this.manufactureDate = manufactureDate;
      this.manufacturer = manufacturer;
      this.serialNumber = serialNumber;
      this.temperature = temperature;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getDeviceName() {
      return this.deviceName;
   }

   @Override
   public double getRemainingCapacity() {
      return this.getRemainingCapacityPercent();
   }

   @Override
   public double getRemainingCapacityPercent() {
      return this.remainingCapacityPercent;
   }

   @Override
   public double getTimeRemaining() {
      return this.getTimeRemainingEstimated();
   }

   @Override
   public double getTimeRemainingEstimated() {
      return this.timeRemainingEstimated;
   }

   @Override
   public double getTimeRemainingInstant() {
      return this.timeRemainingInstant;
   }

   @Override
   public double getPowerUsageRate() {
      return this.powerUsageRate;
   }

   @Override
   public double getVoltage() {
      return this.voltage;
   }

   @Override
   public double getAmperage() {
      return this.amperage;
   }

   @Override
   public boolean isPowerOnLine() {
      return this.powerOnLine;
   }

   @Override
   public boolean isCharging() {
      return this.charging;
   }

   @Override
   public boolean isDischarging() {
      return this.discharging;
   }

   @Override
   public PowerSource.CapacityUnits getCapacityUnits() {
      return this.capacityUnits;
   }

   @Override
   public int getCurrentCapacity() {
      return this.currentCapacity;
   }

   @Override
   public int getMaxCapacity() {
      return this.maxCapacity;
   }

   @Override
   public int getDesignCapacity() {
      return this.designCapacity;
   }

   @Override
   public int getCycleCount() {
      return this.cycleCount;
   }

   @Override
   public String getChemistry() {
      return this.chemistry;
   }

   @Override
   public LocalDate getManufactureDate() {
      return this.manufactureDate;
   }

   @Override
   public String getManufacturer() {
      return this.manufacturer;
   }

   @Override
   public String getSerialNumber() {
      return this.serialNumber;
   }

   @Override
   public double getTemperature() {
      return this.temperature;
   }

   @Override
   public boolean updateAttributes() {
      PowerSource[] apowersource = getPowerSources();

      for (PowerSource powersource : apowersource) {
         if (powersource.getName().equals(this.name)) {
            this.name = powersource.getName();
            this.deviceName = powersource.getDeviceName();
            this.remainingCapacityPercent = powersource.getRemainingCapacityPercent();
            this.timeRemainingEstimated = powersource.getTimeRemainingEstimated();
            this.timeRemainingInstant = powersource.getTimeRemainingInstant();
            this.powerUsageRate = powersource.getPowerUsageRate();
            this.voltage = powersource.getVoltage();
            this.amperage = powersource.getAmperage();
            this.powerOnLine = powersource.isPowerOnLine();
            this.charging = powersource.isCharging();
            this.discharging = powersource.isDischarging();
            this.capacityUnits = powersource.getCapacityUnits();
            this.currentCapacity = powersource.getCurrentCapacity();
            this.maxCapacity = powersource.getMaxCapacity();
            this.designCapacity = powersource.getDesignCapacity();
            this.cycleCount = powersource.getCycleCount();
            this.chemistry = powersource.getChemistry();
            this.manufactureDate = powersource.getManufactureDate();
            this.manufacturer = powersource.getManufacturer();
            this.serialNumber = powersource.getSerialNumber();
            this.temperature = powersource.getTemperature();
            return true;
         }
      }

      return false;
   }

   private static PowerSource[] getPowerSources() {
      switch (SystemInfo.getCurrentPlatformEnum()) {
         case WINDOWS:
            return WindowsPowerSource.getPowerSources();
         case MACOSX:
            return MacPowerSource.Y();
         case LINUX:
            return LinuxPowerSource.x();
         case SOLARIS:
            return SolarisPowerSource.getPowerSources();
         case FREEBSD:
            return FreeBsdPowerSource.getPowerSources();
         default:
            throw new UnsupportedOperationException("Operating system not supported: " + Platform.getOSType());
      }
   }

   @Override
   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("Name: ").append(this.getName()).append(", ");
      stringbuilder.append("Device Name: ").append(this.getDeviceName()).append(",\n ");
      stringbuilder.append("RemainingCapacityPercent: ").append(this.getRemainingCapacityPercent() * 100.0).append("%, ");
      stringbuilder.append("Time Remaining: ").append(formatTimeRemaining(this.getTimeRemainingEstimated())).append(", ");
      stringbuilder.append("Time Remaining Instant: ").append(formatTimeRemaining(this.getTimeRemainingInstant())).append(",\n ");
      stringbuilder.append("Power Usage Rate: ").append(this.getPowerUsageRate()).append("mW, ");
      stringbuilder.append("Voltage: ").append(this.getVoltage()).append("V, ");
      stringbuilder.append("Amperage: ").append(this.getAmperage()).append("mA,\n ");
      stringbuilder.append("Power OnLine: ").append(this.isPowerOnLine()).append(", ");
      stringbuilder.append("Charging: ").append(this.isCharging()).append(", ");
      stringbuilder.append("Discharging: ").append(this.isDischarging()).append(",\n ");
      stringbuilder.append("Capacity Units: ").append(this.getCapacityUnits()).append(", ");
      stringbuilder.append("Current Capacity: ").append(this.getCurrentCapacity()).append(", ");
      stringbuilder.append("Max Capacity: ").append(this.getMaxCapacity()).append(", ");
      stringbuilder.append("Design Capacity: ").append(this.getDesignCapacity()).append(",\n ");
      stringbuilder.append("Cycle Count: ").append(this.getCycleCount()).append(", ");
      stringbuilder.append("Chemistry: ").append(this.getChemistry()).append(", ");
      StringBuilder stringbuilder3 = stringbuilder.append("Manufacture Date: ");
      LocalDate localdate = this.getManufactureDate();
      StringBuilder stringbuilder1 = stringbuilder3;
      stringbuilder1.append(localdate != null ? this.getManufactureDate() : "unknown").append(", ");
      stringbuilder.append("Manufacturer: ").append(this.getManufacturer()).append(",\n ");
      stringbuilder.append("SerialNumber: ").append(this.getSerialNumber()).append(", ");
      StringBuilder stringbuilder2 = stringbuilder.append("Temperature: ");
      if (this.getTemperature() > 0.0) {
         stringbuilder.append(this.getTemperature()).append("°C");
      } else {
         stringbuilder.append("unknown");
      }

      return stringbuilder.toString();
   }

   private static String formatTimeRemaining(double timeInSeconds) {
      double d0;
      int k = (d0 = timeInSeconds - -1.5) == 0.0 ? 0 : (d0 < 0.0 ? -1 : 1);
      String s;
      if (k < 0) {
         s = "Charging";
      } else {
         double d1;
         int l = (d1 = timeInSeconds - 0.0) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (l < 0) {
            s = "Unknown";
         } else {
            int i = (int)(timeInSeconds / 3600.0);
            int j = (int)(timeInSeconds % 3600.0 / 60.0);
            s = String.format("%d:%02d", i, j);
         }
      }

      return s;
   }
}
