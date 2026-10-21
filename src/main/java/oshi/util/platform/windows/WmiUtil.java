package oshi.util.platform.windows;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import java.time.OffsetDateTime;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.util.Constants;
import oshi.util.ParseUtil;

@ThreadSafe
public final class WmiUtil {
   public static final String OHM_NAMESPACE = "ROOT\\OpenHardwareMonitor";
   private static final String g = "%s is not a %s type. CIM Type is %d and VT type is %d";

   private WmiUtil() {
   }

   public static <T extends Enum<T>> String queryToString(WmiQuery<T> query) {
      Enum[] aenum = (Enum[])query.getPropertyEnum().getEnumConstants();
      StringBuilder stringbuilder = new StringBuilder("SELECT ");
      stringbuilder.append(aenum[0].name());

      for (int i = 1; i < aenum.length; i++) {
         stringbuilder.append(',').append(aenum[i].name());
      }

      stringbuilder.append(" FROM ").append(query.getWmiClassName());
      return stringbuilder.toString();
   }

   public static <T extends Enum<T>> String getString(WmiResult<T> result, T property, int index) {
      if (result.getCIMType((T)property) == 8) {
         return getStr(result, (T)property, index);
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "String",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> String getDateString(WmiResult<T> result, T property, int index) {
      OffsetDateTime offsetdatetime = getDateTime(result, (T)property, index);
      return offsetdatetime.equals(Constants.UNIX_EPOCH) ? "" : offsetdatetime.toLocalDate().toString();
   }

   public static <T extends Enum<T>> OffsetDateTime getDateTime(WmiResult<T> result, T property, int index) {
      if (result.getCIMType((T)property) == 101) {
         return ParseUtil.parseCimDateTimeToOffset(getStr(result, (T)property, index));
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "DateTime",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> String getRefString(WmiResult<T> result, T property, int index) {
      if (result.getCIMType((T)property) == 102) {
         return getStr(result, (T)property, index);
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "Reference",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   private static <T extends Enum<T>> String getStr(WmiResult<T> result, T property, int index) {
      Object object = result.getValue((T)property, index);
      if (object == null) {
         return "";
      } else if (result.getVtType((T)property) == 8) {
         return (String)object;
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "String-mapped",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> long getUint64(WmiResult<T> result, T property, int index) {
      Object object = result.getValue((T)property, index);
      if (object == null) {
         return 0L;
      } else if (result.getCIMType((T)property) == 21 && result.getVtType((T)property) == 8) {
         return ParseUtil.parseLongOrDefault((String)object, 0L);
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "UINT64",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> int getUint32(WmiResult<T> result, T property, int index) {
      if (result.getCIMType((T)property) == 19) {
         return getInt(result, (T)property, index);
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "UINT32",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> long getUint32asLong(WmiResult<T> result, T property, int index) {
      if (result.getCIMType((T)property) == 19) {
         return (long)getInt(result, (T)property, index) & 4294967295L;
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "UINT32",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> int getSint32(WmiResult<T> result, T property, int index) {
      if (result.getCIMType((T)property) == 3) {
         return getInt(result, (T)property, index);
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "SINT32",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> int getUint16(WmiResult<T> result, T property, int index) {
      if (result.getCIMType((T)property) == 18) {
         return getInt(result, (T)property, index);
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "UINT16",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   private static <T extends Enum<T>> int getInt(WmiResult<T> result, T property, int index) {
      Object object = result.getValue((T)property, index);
      if (object == null) {
         return 0;
      } else if (result.getVtType((T)property) == 3) {
         return (Integer)object;
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d",
               property.name(),
               "32-bit integer",
               result.getCIMType((T)property),
               result.getVtType((T)property)
            )
         );
      }
   }

   public static <T extends Enum<T>> float getFloat(WmiResult<T> result, T property, int index) {
      Object object = result.getValue((T)property, index);
      if (object == null) {
         return 0.0F;
      } else if (result.getCIMType((T)property) == 4 && result.getVtType((T)property) == 4) {
         return (Float)object;
      } else {
         throw new ClassCastException(
            String.format(
               "%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "Float", result.getCIMType((T)property), result.getVtType((T)property)
            )
         );
      }
   }
}
