package astolfo.account;

import java.lang.reflect.Field;

public class FieldSetter {
   private String fieldName;
   private Class<?> targetClass;
   private static final String b = "modifiers";
   private static final long c = -6896822431961317393L;

   public FieldSetter(String string) {
      try {
         this.targetClass = Class.forName(string);
      } catch (ClassNotFoundException classnotfoundexception) {
         classnotfoundexception.printStackTrace();
      }
   }

   public void setStaticFinalField(String string, Object object) throws NoSuchFieldException, IllegalAccessException {
      Field field = this.targetClass.getDeclaredField(string);
      field.setAccessible(true);
      Field field1 = Field.class.getDeclaredField(b);
      field1.setAccessible(true);
      field1.setInt(field, field.getModifiers() & (int)c);
      field.set(null, object);
   }
}
