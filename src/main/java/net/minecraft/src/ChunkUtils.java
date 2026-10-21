package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.world.chunk.Chunk;

public class ChunkUtils {
   private static Field fieldHasEntities = null;
   private static boolean fieldHasEntitiesMissing = false;

   public static boolean hasEntities(Chunk chunk) {
      if (fieldHasEntities == null) {
         if (fieldHasEntitiesMissing) {
            return true;
         }

         fieldHasEntities = findFieldHasEntities(chunk);
         if (fieldHasEntities == null) {
            fieldHasEntitiesMissing = true;
            return true;
         }
      }

      try {
         return fieldHasEntities.getBoolean(chunk);
      } catch (Exception exception) {
         Config.warn("Error calling Chunk.hasEntities");
         Config.warn(exception.getClass().getName() + " " + exception.getMessage());
         fieldHasEntitiesMissing = true;
         return true;
      }
   }

   private static Field findFieldHasEntities(Chunk chunk) {
      try {
         ArrayList arraylist = new ArrayList();
         ArrayList arraylist1 = new ArrayList();
         Field[] afield = Chunk.class.getDeclaredFields();

         for (int i = 0; i < afield.length; i++) {
            Field field = afield[i];
            if (field.getType() == boolean.class) {
               field.setAccessible(true);
               arraylist.add(field);
               arraylist1.add(field.get(chunk));
            }
         }

         chunk.setHasEntities(false);
         ArrayList arraylist2 = new ArrayList();

         for (Object object : arraylist) {
            arraylist2.add(((Field)object).get(chunk));
         }

         chunk.setHasEntities(true);
         ArrayList arraylist3 = new ArrayList();

         for (Object object1 : arraylist) {
            arraylist3.add(((Field)object1).get(chunk));
         }

         ArrayList arraylist4 = new ArrayList();

         for (int j = 0; j < arraylist.size(); j++) {
            Field field1 = (Field)arraylist.get(j);
            Boolean obool = (Boolean)arraylist2.get(j);
            Boolean obool1 = (Boolean)arraylist3.get(j);
            if (!obool && obool1) {
               arraylist4.add(field1);
               Boolean obool2 = (Boolean)arraylist1.get(j);
               field1.set(chunk, obool2);
            }
         }

         if (arraylist4.size() == 1) {
            return (Field)arraylist4.get(0);
         }
      } catch (Exception exception) {
         Config.warn(exception.getClass().getName() + " " + exception.getMessage());
      }

      Config.warn("Error finding Chunk.hasEntities");
      return null;
   }
}
