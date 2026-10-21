package astolfo.setting;

import astolfo.setting.attribute.SettingAttribute;
import astolfo.util.TypeUtil;
import java.util.Arrays;
import java.util.List;

public final class BooleanSetting extends Setting<Boolean> implements Comparable<BooleanSetting> {
   private static final long serialVersionUID = -4830768138240036487L;

   public BooleanSetting() {
      super("", "", false, SettingType.UNDEFINED);
   }

   public BooleanSetting(String string, String string2, boolean bl) {
      super(string, string2, bl, SettingType.BOOLEAN);
      if (!TypeUtil.isBoolean(bl)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public BooleanSetting(String string, String string2, Boolean boolean2) {
      super(string, string2, boolean2, SettingType.BOOLEAN);
      if (!TypeUtil.isBoolean(boolean2)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public BooleanSetting(String string, String string2, boolean bl, List<SettingAttribute> list) {
      super(string, string2, bl, SettingType.BOOLEAN, list);
      if (!TypeUtil.isBoolean(bl)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public BooleanSetting(String string, String string2, Boolean boolean2, List<SettingAttribute> list) {
      super(string, string2, boolean2, SettingType.BOOLEAN, list);
      if (!TypeUtil.isBoolean(boolean2)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public BooleanSetting(String string, String string2, boolean bl, SettingAttribute... settingAttributes) {
      super(string, string2, bl, SettingType.BOOLEAN, Arrays.asList(settingAttributes));
      if (!TypeUtil.isBoolean(bl)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public BooleanSetting(String string, String string2, Boolean boolean2, SettingAttribute... settingAttributes) {
      super(string, string2, boolean2, SettingType.BOOLEAN, Arrays.asList(settingAttributes));
      if (!TypeUtil.isBoolean(boolean2)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public static int compare(boolean bl, boolean bl2) {
      if (bl == bl2) {
         return 0;
      } else {
         return bl ? 1 : -1;
      }
   }

   public void disable() {
      super.setValue(false);
   }

   public void enable() {
      super.setValue(true);
   }

   public void set(boolean bl) {
      super.setValue(bl);
   }

   public void setValue(Boolean boolean2) {
      super.setValue(boolean2);
   }

   public boolean isEnabled() {
      return (Boolean)super.value;
   }

   public boolean isDisabled() {
      return !(Boolean)super.value;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof BooleanSetting) ? false : super.value == ((BooleanSetting)object).getValue() && super.equals(object);
   }

   @Override
   public int hashCode() {
      return super.hashCode() + ((Boolean)super.value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode());
   }

   public int compareTo(BooleanSetting booleanSetting) {
      return compare((Boolean)super.value, (Boolean)booleanSetting.value);
   }

   public static boolean r() {
      return true;
   }
}
