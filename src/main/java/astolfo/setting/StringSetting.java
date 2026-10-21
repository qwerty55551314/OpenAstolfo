package astolfo.setting;

import astolfo.setting.attribute.SettingAttribute;
import astolfo.util.TypeUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class StringSetting extends Setting<String> implements Comparable<StringSetting> {
   private static final long serialVersionUID = -1076827446660376L;

   public StringSetting() {
      super("", "", "", SettingType.UNDEFINED);
   }

   public StringSetting(String string, String string2, String string3) {
      super(string, string2, string3, SettingType.STRING);
      if (!TypeUtil.isString(string3)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public StringSetting(String string, String string2, String string3, List<SettingAttribute> list) {
      super(string, string2, string3, SettingType.STRING, list);
      if (!TypeUtil.isString(string3)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public StringSetting(String string, String string2, String string3, SettingAttribute... settingAttributes) {
      super(string, string2, string3, SettingType.STRING, Arrays.asList(settingAttributes));
      if (!TypeUtil.isString(string3)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public static int compare(String string, String string2) {
      return Integer.compare(string.length(), string2.length());
   }

   @Override
   public boolean equals(Object object) {
      boolean flag = object instanceof StringSetting;
      if (flag) {
         boolean flag1 = Objects.equals(super.value, ((StringSetting)object).getValue());
         if (flag1) {
            boolean flag2 = super.equals(object);
            if (flag2) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public int hashCode() {
      return super.hashCode() + (super.value == null ? 0 : ((String)super.value).hashCode());
   }

   public int compareTo(StringSetting stringSetting) {
      return compare((String)super.value, (String)stringSetting.value);
   }
}
