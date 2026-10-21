package astolfo.setting;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public enum SettingType {
   UNDEFINED("undefined", 0, null),
   BOOLEAN("boolean", 1, Boolean.class),
   NUMBER("number", 2, Number.class),
   STRING("string", 3, String.class),
   ENUM("enum", 4, Enum.class),
   MAP("map", 5, Map.class),
   LIST("list", 6, List.class),
   COLOR("color", 7, Color.class);

   private int id;
   private String typeName;
   private Class<?> valueClass;
   private static String G;

   private SettingType(String string2, int i2, Class<?> classValue) {
      this.id = i2;
      this.typeName = string2;
      this.valueClass = classValue;
   }

   public int getId() {
      return this.id;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public Class<?> getValueClass() {
      return this.valueClass;
   }

   public static SettingType fromName(String string) {
      for (SettingType settingtype : values()) {
         if (settingtype.getTypeName().equalsIgnoreCase(string)) {
            return settingtype;
         }
      }

      return null;
   }

   public static Class<?> getClassForName(String string) {
      for (SettingType settingtype : values()) {
         if (settingtype.getTypeName().equalsIgnoreCase(string)) {
            return settingtype.getValueClass();
         }
      }

      return null;
   }
}
