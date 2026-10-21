package astolfo.setting;

import astolfo.setting.attribute.SettingAttribute;
import astolfo.util.TypeUtil;
import java.util.Arrays;
import java.util.List;

public final class EnumSetting<E extends Enum> extends Setting<E> implements Comparable<EnumSetting<Enum<?>>> {
   private static final long serialVersionUID = -151221052342341475L;

   public EnumSetting() {
      super("", "", null, SettingType.UNDEFINED);
   }

   public EnumSetting(String string, String string2, E enum2) {
      super(string, string2, (E)enum2, SettingType.ENUM);
      if (!TypeUtil.isEnum(enum2)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public EnumSetting(String string, String string2, E enum2, List<SettingAttribute> list) {
      super(string, string2, (E)enum2, SettingType.ENUM, list);
      if (!TypeUtil.isEnum(enum2)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public EnumSetting(String string, String string2, E enum2, SettingAttribute... settingAttributes) {
      super(string, string2, (E)enum2, SettingType.ENUM, Arrays.asList(settingAttributes));
      if (!TypeUtil.isEnum(enum2)) {
         try {
            throw new InvalidValueException("Invalid value.");
         } catch (InvalidValueException invalidvalueexception) {
            invalidvalueexception.printStackTrace();
         }
      }
   }

   public E getValue() {
      return (E)super.getValue();
   }

   public void setValue(E enum2) {
      super.setValue(enum2);
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof EnumSetting) ? false : super.value == ((EnumSetting)object).getValue() && super.equals(object);
   }

   @Override
   public int hashCode() {
      return super.hashCode() + (super.value != null ? 0 : ((Enum)super.value).hashCode());
   }

   public int compareTo(EnumSetting enumSetting) {
      return ((Enum)super.value).compareTo((E)enumSetting.getValue());
   }
}
