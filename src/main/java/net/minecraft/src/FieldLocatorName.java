package net.minecraft.src;

import java.lang.reflect.Field;

public class FieldLocatorName implements IFieldLocator {
   private ReflectorClass reflectorClass = null;
   private String targetFieldName = null;
   private static final String b = "(Reflector) Field not present: ";

   public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
      this.reflectorClass = reflectorClass;
      this.targetFieldName = targetFieldName;
   }

   @Override
   public Field getField() {
      Class oclass = this.reflectorClass.getTargetClass();
      if (oclass == null) {
         return null;
      } else {
         try {
            Field field = oclass.getDeclaredField(this.targetFieldName);
            field.setAccessible(true);
            return field;
         } catch (NoSuchFieldException nosuchfieldexception) {
            Config.log(b + oclass.getName() + "." + this.targetFieldName);
            return null;
         } catch (SecurityException securityexception) {
            securityexception.printStackTrace();
            return null;
         } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
         }
      }
   }
}
