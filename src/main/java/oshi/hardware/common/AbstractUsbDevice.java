package oshi.hardware.common;

import java.util.Arrays;
import oshi.annotation.concurrent.Immutable;
import oshi.hardware.UsbDevice;

@Immutable
public abstract class AbstractUsbDevice implements UsbDevice {
   public String name;
   public String vendor;
   public String vendorId;
   public String productId;
   public String serialNumber;
   public String uniqueDeviceId;
   public UsbDevice[] connectedDevices;

   public AbstractUsbDevice(
      String name, String vendor, String vendorId, String productId, String serialNumber, String uniqueDeviceId, UsbDevice[] connectedDevices
   ) {
      this.name = name;
      this.vendor = vendor;
      this.vendorId = vendorId;
      this.productId = productId;
      this.serialNumber = serialNumber;
      this.uniqueDeviceId = uniqueDeviceId;
      this.connectedDevices = Arrays.copyOf(connectedDevices, connectedDevices.length);
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getVendor() {
      return this.vendor;
   }

   @Override
   public String getVendorId() {
      return this.vendorId;
   }

   @Override
   public String getProductId() {
      return this.productId;
   }

   @Override
   public String getSerialNumber() {
      return this.serialNumber;
   }

   @Override
   public String getUniqueDeviceId() {
      return this.uniqueDeviceId;
   }

   @Override
   public UsbDevice[] getConnectedDevices() {
      return Arrays.copyOf(this.connectedDevices, this.connectedDevices.length);
   }

   public int compareTo(UsbDevice usb) {
      return this.getName().compareTo(usb.getName());
   }

   @Override
   public String toString() {
      return indentUsb(this, 1);
   }

   private static String indentUsb(UsbDevice usbDevice, int indent) {
      String s = indent > 2 ? String.format("%%%ds|-- ", indent - 4) : String.format("%%%ds", indent);
      StringBuilder stringbuilder = new StringBuilder(String.format(s, ""));
      stringbuilder.append(usbDevice.getName());
      int i = usbDevice.getVendor().length();
      if (i > 0) {
         stringbuilder.append(" (").append(usbDevice.getVendor()).append(')');
      }

      if (usbDevice.getSerialNumber().length() > 0) {
         stringbuilder.append(" [s/n: ").append(usbDevice.getSerialNumber()).append(']');
      }

      for (UsbDevice usbdevice : usbDevice.getConnectedDevices()) {
         StringBuilder stringbuilder1 = stringbuilder.append('\n').append(indentUsb(usbdevice, indent + 4));
      }

      return stringbuilder.toString();
   }
}
