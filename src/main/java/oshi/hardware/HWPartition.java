package oshi.hardware;

import oshi.annotation.concurrent.NotThreadSafe;
import oshi.util.FormatUtil;

@NotThreadSafe
public class HWPartition implements Comparable<HWPartition> {
   private String identification;
   private String name;
   private String type;
   private String uuid;
   private long size;
   private int major;
   private int minor;
   private String mountPoint;

   public HWPartition(String identification, String name, String typeIn, String uuid, long size, int major, int minor, String mountPoint) {
      this.setIdentification(identification);
      this.setName(name);
      this.setType(typeIn);
      this.setUuid(uuid);
      this.setSize(size);
      this.setMajor(major);
      this.setMinor(minor);
      this.setMountPoint(mountPoint);
   }

   public HWPartition() {
      this("", "", "", "", 0L, 0, 0, "");
   }

   public String getIdentification() {
      return this.identification;
   }

   public String getName() {
      return this.name;
   }

   public String getType() {
      return this.type;
   }

   public String getUuid() {
      return this.uuid;
   }

   public long getSize() {
      return this.size;
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public String getMountPoint() {
      return this.mountPoint;
   }

   public void setIdentification(String identification) {
      this.identification = identification == null ? "" : identification;
   }

   public void setName(String name) {
      this.name = name == null ? "" : name;
   }

   public void setType(String typeIn) {
      this.type = typeIn == null ? "" : typeIn;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid == null ? "" : uuid;
   }

   public void setSize(long size) {
      this.size = size;
   }

   public void setMajor(int major) {
      this.major = major;
   }

   public void setMinor(int minor) {
      this.minor = minor;
   }

   public void setMountPoint(String mountPoint) {
      this.mountPoint = mountPoint == null ? "" : mountPoint;
   }

   public int compareTo(HWPartition part) {
      return this.getIdentification().compareTo(part.getIdentification());
   }

   @Override
   public int hashCode() {
      int i = 1;
      int k1 = 31 * i;
      String s = this.identification;
      int j = k1;
      i = j + (s == null ? 0 : this.identification.hashCode());
      i = 31 * i + this.major;
      i = 31 * i + this.minor;
      k1 = 31 * i;
      String s1 = this.mountPoint;
      int k = k1;
      i = k + (s1 == null ? 0 : this.mountPoint.hashCode());
      k1 = 31 * i;
      String s2 = this.name;
      int l = k1;
      i = l + (s2 == null ? 0 : this.name.hashCode());
      i = 31 * i + (int)(this.size ^ this.size >>> 32);
      k1 = 31 * i;
      String s3 = this.type;
      int i1 = k1;
      i = i1 + (s3 == null ? 0 : this.type.hashCode());
      k1 = 31 * i;
      String s4 = this.uuid;
      int j1 = k1;
      return j1 + (s4 == null ? 0 : this.uuid.hashCode());
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         Class oclass = obj.getClass();
         if (!oclass.equals(this.getClass())) {
            return false;
         } else {
            HWPartition hwpartition = (HWPartition)obj;
            String s = this.identification;
            if (s == null) {
               if (hwpartition.identification != null) {
                  return false;
               }
            } else {
               boolean flag = this.identification.equals(hwpartition.identification);
               if (!flag) {
                  return false;
               }
            }

            int j = hwpartition.major;
            int i = this.major;
            if (i != j) {
               return false;
            } else if (this.minor != hwpartition.minor) {
               return false;
            } else {
               String s1 = this.mountPoint;
               if (s1 == null) {
                  if (hwpartition.mountPoint != null) {
                     return false;
                  }
               } else {
                  String s2 = this.mountPoint;
                  if (!s2.equals(hwpartition.mountPoint)) {
                     return false;
                  }
               }

               String s3 = this.name;
               if (s3 == null) {
                  if (hwpartition.name != null) {
                     return false;
                  }
               } else {
                  boolean flag1 = this.name.equals(hwpartition.name);
                  if (!flag1) {
                     return false;
                  }
               }

               if (this.size != hwpartition.size) {
                  return false;
               } else {
                  String s4 = this.type;
                  if (s4 == null) {
                     if (hwpartition.type != null) {
                        return false;
                     }
                  } else {
                     String s5 = this.type;
                     if (!s5.equals(hwpartition.type)) {
                        return false;
                     }
                  }

                  String s6 = this.uuid;
                  if (s6 == null) {
                     if (hwpartition.uuid != null) {
                        return false;
                     }
                  } else {
                     boolean flag2 = this.uuid.equals(hwpartition.uuid);
                     if (!flag2) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      }
   }

   @Override
   public String toString() {
      return this.getIdentification()
         + ": "
         + this.getName()
         + " ("
         + this.getType()
         + ") Maj:Min="
         + this.getMajor()
         + ":"
         + this.getMinor()
         + ", size: "
         + FormatUtil.formatBytesDecimal(this.getSize())
         + (this.getMountPoint().isEmpty() ? "" : " @ " + this.getMountPoint());
   }
}
